package fr.liquide.launcher.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import fr.liquide.launcher.model.LauncherPreferences
import fr.liquide.launcher.model.TvApp
import fr.liquide.launcher.model.orderedApps
import fr.liquide.launcher.platform.AppRepository
import fr.liquide.launcher.platform.PreferencesRepository
import java.io.IOException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed interface CatalogState {
    data object Loading : CatalogState
    data class Ready(val apps: List<TvApp>) : CatalogState
    data object Failed : CatalogState
}

data class HomeState(
    val catalog: CatalogState = CatalogState.Loading,
    val preferences: LauncherPreferences = LauncherPreferences(),
    val saveFailed: Boolean = false,
)

class HomeViewModel(private val apps: AppRepository, private val preferences: PreferencesRepository) : ViewModel() {
    private val catalog = MutableStateFlow<CatalogState>(CatalogState.Loading)
    private val saveFailed = MutableStateFlow(false)
    private var refreshJob: Job? = null
    val state = combine(catalog, preferences.preferences, saveFailed) { result, prefs, failed ->
        HomeState(if (result is CatalogState.Ready)
            CatalogState.Ready(orderedApps(result.apps, prefs.favoriteIds)) else result, prefs, failed)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), HomeState())

    init { refresh() }
    fun refresh() {
        refreshJob?.cancel()
        refreshJob = viewModelScope.launch {
            try { catalog.value = CatalogState.Ready(apps.installedTvApps()) }
            catch (cancelled: CancellationException) { throw cancelled }
            catch (_: Exception) { catalog.value = CatalogState.Failed }
        }
    }
    fun favorite(id: String) = save { preferences.toggleFavorite(id) }
    fun reducedMotion(enabled: Boolean) = save { preferences.setReducedMotion(enabled) }
    fun clearError() { saveFailed.value = false }
    private fun save(action: suspend () -> Unit) {
        viewModelScope.launch {
            try { action(); saveFailed.value = false }
            catch (_: IOException) { saveFailed.value = true }
        }
    }
    companion object {
        fun factory(apps: AppRepository, preferences: PreferencesRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    require(modelClass.isAssignableFrom(HomeViewModel::class.java))
                    return HomeViewModel(apps, preferences) as T
                }
            }
    }
}
