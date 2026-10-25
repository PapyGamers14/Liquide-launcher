package fr.liquide.launcher.platform

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import fr.liquide.launcher.model.TvApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface AppRepository { suspend fun installedTvApps(): List<TvApp> }

class AndroidAppRepository(context: Context) : AppRepository {
    private val context = context.applicationContext

    @Suppress("DEPRECATION")
    override suspend fun installedTvApps(): List<TvApp> = withContext(Dispatchers.IO) {
        val query = Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LEANBACK_LAUNCHER)
        context.packageManager.queryIntentActivities(query, PackageManager.MATCH_ALL)
            .mapNotNull { result ->
                val activity = result.activityInfo ?: return@mapNotNull null
                if (!activity.exported || !activity.enabled || !activity.applicationInfo.enabled ||
                    activity.packageName == context.packageName) return@mapNotNull null
                val component = ComponentName(activity.packageName, activity.name)
                TvApp(component.flattenToString(), result.loadLabel(context.packageManager).toString(),
                    activity.packageName, activity.name)
            }.distinctBy(TvApp::id)
    }
}

/** Explicit intent only: no undocumented OEM API, shell command or privileged permission. */
class AppLauncher(context: Context) {
    private val context = context.applicationContext
    fun launch(app: TvApp): Boolean = try {
        context.startActivity(Intent(Intent.ACTION_MAIN)
            .addCategory(Intent.CATEGORY_LEANBACK_LAUNCHER)
            .setComponent(ComponentName(app.packageName, app.activityName))
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED))
        true
    } catch (_: android.content.ActivityNotFoundException) { false }
      catch (_: SecurityException) { false }
}
