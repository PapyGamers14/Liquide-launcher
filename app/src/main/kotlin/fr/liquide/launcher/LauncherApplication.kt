package fr.liquide.launcher

import android.app.Application
import fr.liquide.launcher.platform.AndroidAppRepository
import fr.liquide.launcher.platform.AppIconLoader
import fr.liquide.launcher.platform.AppLauncher
import fr.liquide.launcher.platform.PreferencesRepository

class LauncherApplication : Application() {
    val apps by lazy { AndroidAppRepository(this) }
    val preferences by lazy { PreferencesRepository(this) }
    val icons by lazy { AppIconLoader(this) }
    val launcher by lazy { AppLauncher(this) }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        if (level >= TRIM_MEMORY_RUNNING_LOW) icons.clear()
    }
}
