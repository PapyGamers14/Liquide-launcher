package fr.liquide.launcher.home

import android.graphics.Bitmap
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.liquide.launcher.model.DeviceCapabilities
import fr.liquide.launcher.model.TvApp
import fr.liquide.launcher.platform.AppIconLoader
import fr.liquide.launcher.platform.AppLauncher

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    icons: AppIconLoader,
    launcher: AppLauncher,
    capabilities: DeviceCapabilities,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val reduced = state.preferences.reducedMotion || !capabilities.systemAnimationsEnabled
    var settings by rememberSaveable { mutableStateOf(false) }
    var favoriteOnly by rememberSaveable { mutableStateOf(false) }
    var selected by remember { mutableStateOf<TvApp?>(null) }
    var launchFailed by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val firstFocus = remember { FocusRequester() }
    val catalog = state.catalog
    val apps = (catalog as? CatalogState.Ready)?.apps.orEmpty()
    val visible = if (favoriteOnly) apps.filter { it.id in state.preferences.favoriteIds } else apps

    // Focus starts on a permanent toolbar action; it never points to a removed package.
    LaunchedEffect(Unit) { firstFocus.requestFocus() }
    BackHandler(enabled = favoriteOnly && !settings && selected == null) { favoriteOnly = false }

    LiquideTheme {
        Box(Modifier.fillMaxSize().background(Brush.verticalGradient(
            listOf(LiquideTokens.Atmosphere, LiquideTokens.Background)))) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(150.dp),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(LiquideTokens.Inset),
                horizontalArrangement = Arrangement.spacedBy(LiquideTokens.Gap),
                verticalArrangement = Arrangement.spacedBy(LiquideTokens.Gap),
            ) {
                item(key = "navigation", span = { GridItemSpan(maxLineSpan) }) {
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text(stringResource(R.string.brand), fontWeight = FontWeight.SemiBold,
                            fontSize = 24.sp, color = LiquideTokens.Foreground, modifier = Modifier.weight(1f))
                        ToolbarAction(stringResource(R.string.all_apps), reduced, { favoriteOnly = false }, Modifier.focusRequester(firstFocus))
                        ToolbarAction(stringResource(R.string.favorites), reduced, { favoriteOnly = true })
                        ToolbarAction(stringResource(R.string.settings), reduced, { settings = true })
                    }
                }
                item(key = "welcome", span = { GridItemSpan(maxLineSpan) }) {
                    Column(Modifier.padding(vertical = 24.dp)) {
                        Text(stringResource(if (favoriteOnly) R.string.favorites_title else R.string.home_title),
                            fontSize = 38.sp, fontWeight = FontWeight.Medium, color = LiquideTokens.Foreground,
                            modifier = Modifier.semantics { heading() })
                        Spacer(Modifier.height(8.dp))
                        Text(stringResource(R.string.home_description), color = LiquideTokens.Secondary,
                            style = MaterialTheme.typography.bodyLarge)
                    }
                }
                when (catalog) {
                    CatalogState.Loading -> item(span = { GridItemSpan(maxLineSpan) }) {
                        Column(Modifier.height(120.dp), verticalArrangement = Arrangement.Center) {
                            CircularProgressIndicator()
                            Text(stringResource(R.string.loading), color = LiquideTokens.Foreground)
                        }
                    }
                    CatalogState.Failed -> item(span = { GridItemSpan(maxLineSpan) }) {
                        Column {
                            Text(stringResource(R.string.catalog_error), color = LiquideTokens.Foreground)
                            Button(onClick = viewModel::refresh) { Text(stringResource(R.string.retry)) }
                        }
                    }
                    is CatalogState.Ready -> {
                        if (visible.isEmpty()) item(span = { GridItemSpan(maxLineSpan) }) {
                            Text(stringResource(if (favoriteOnly) R.string.empty_favorites else R.string.empty_apps),
                                color = LiquideTokens.Secondary, modifier = Modifier.heightIn(min = 120.dp))
                        }
                        items(visible, key = TvApp::id, contentType = { "app" }) { app ->
                            val isFavorite = app.id in state.preferences.favoriteIds
                            AppTile(app, isFavorite, icons, reduced,
                                onClick = { launchFailed = !launcher.launch(app) },
                                onLongClick = { selected = app })
                        }
                    }
                }
                item(key = "help", span = { GridItemSpan(maxLineSpan) }) {
                    Text(stringResource(R.string.remote_help), color = LiquideTokens.Secondary,
                        style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(top = 12.dp))
                }
            }
        }
        if (settings) AlertDialog(
            onDismissRequest = { settings = false },
            title = { Text(stringResource(R.string.settings)) },
            text = { Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(stringResource(R.string.motion_description))
                Button(onClick = { viewModel.reducedMotion(!state.preferences.reducedMotion) }) {
                    Text(stringResource(if (state.preferences.reducedMotion) R.string.motion_enable else R.string.motion_reduce))
                }
                Button(onClick = {
                    try { context.startActivity(android.content.Intent(android.provider.Settings.ACTION_SETTINGS)) }
                    catch (_: android.content.ActivityNotFoundException) { launchFailed = true }
                    catch (_: SecurityException) { launchFailed = true }
                }) { Text(stringResource(R.string.tv_settings)) }
            } },
            confirmButton = { TextButton(onClick = { settings = false }) { Text(stringResource(R.string.close)) } },
        )
        selected?.let { app -> AlertDialog(
            onDismissRequest = { selected = null },
            title = { Text(app.label) },
            text = { Text(stringResource(R.string.favorite_description)) },
            confirmButton = { TextButton(onClick = { viewModel.favorite(app.id); selected = null }) {
                Text(stringResource(if (app.id in state.preferences.favoriteIds) R.string.remove_favorite else R.string.add_favorite))
            } },
            dismissButton = { TextButton(onClick = { selected = null }) { Text(stringResource(R.string.cancel)) } },
        ) }
        if (launchFailed || state.saveFailed) AlertDialog(
            onDismissRequest = { launchFailed = false; viewModel.clearError() },
            title = { Text(stringResource(R.string.action_error)) },
            text = { Text(stringResource(if (state.saveFailed) R.string.save_error else R.string.launch_error)) },
            confirmButton = { TextButton(onClick = {
                launchFailed = false; viewModel.clearError(); viewModel.refresh()
            }) { Text(stringResource(R.string.close)) } },
        )
    }
}

@Composable
private fun ToolbarAction(label: String, reduced: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    FocusCard(reduced, onClick, modifier) {
        Text(label, color = LiquideTokens.Foreground, modifier = Modifier.padding(horizontal = 18.dp, vertical = 12.dp))
    }
}

@Composable
private fun AppTile(app: TvApp, favorite: Boolean, icons: AppIconLoader, reduced: Boolean,
                    onClick: () -> Unit, onLongClick: () -> Unit) {
    val icon by produceState<Bitmap?>(null, app.id, icons) { value = icons.load(app) }
    FocusCard(reduced, onClick, Modifier.fillMaxWidth().height(156.dp), onLongClick) {
        Column(Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Box(Modifier.size(72.dp), contentAlignment = Alignment.Center) {
                icon?.let { Image(it.asImageBitmap(), contentDescription = null, modifier = Modifier.fillMaxSize()) }
                    ?: Text(app.label.take(1), color = LiquideTokens.Foreground, fontSize = 32.sp)
            }
            Spacer(Modifier.height(12.dp))
            Text(app.label, maxLines = 1, overflow = TextOverflow.Ellipsis, color = LiquideTokens.Foreground)
            if (favorite) Text(stringResource(R.string.favorite_badge), color = LiquideTokens.Focus, fontSize = 11.sp)
        }
    }
}
