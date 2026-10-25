package fr.liquide.launcher.model

data class TvApp(val id: String, val label: String, val packageName: String, val activityName: String)

data class LauncherPreferences(
    val favoriteIds: Set<String> = emptySet(),
    val reducedMotion: Boolean = false,
)

data class DeviceCapabilities(val lowRam: Boolean, val systemAnimationsEnabled: Boolean)

/** Stable identity uses the exact launch component, never an app label or guessed package. */
fun orderedApps(apps: List<TvApp>, favoriteIds: Set<String>): List<TvApp> =
    apps.distinctBy(TvApp::id).sortedWith(
        compareByDescending<TvApp> { it.id in favoriteIds }
            .thenBy(String.CASE_INSENSITIVE_ORDER) { it.label }
            .thenBy { it.id },
    )
