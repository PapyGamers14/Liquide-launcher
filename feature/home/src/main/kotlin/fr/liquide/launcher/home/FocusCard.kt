package fr.liquide.launcher.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun FocusCard(
    reducedMotion: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    onLongClick: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit,
) {
    var focused by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (focused && !reducedMotion) 1.035f else 1f,
        animationSpec = tween(if (reducedMotion) 0 else LiquideTokens.FocusDurationMillis),
        label = "focusScale",
    )
    val shape = RoundedCornerShape(LiquideTokens.Radius)
    Box(modifier
        .graphicsLayer { scaleX = scale; scaleY = scale }
        .clip(shape)
        .background(LiquideTokens.Surface.copy(alpha = if (focused) 1f else .85f))
        .border(if (focused) 3.dp else 1.dp,
            if (focused) LiquideTokens.Focus else LiquideTokens.Secondary.copy(alpha = .24f), shape)
        .onFocusChanged { focused = it.isFocused }
        .combinedClickable(role = Role.Button, onClick = onClick, onLongClick = onLongClick),
        content = content)
}
