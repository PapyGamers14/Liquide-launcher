package fr.liquide.launcher.platform

import android.content.ComponentName
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.LruCache
import fr.liquide.launcher.model.TvApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/** Rasterization is off the UI thread; bounded cache avoids retaining all installed artwork. */
class AppIconLoader(context: Context) {
    private val packageManager = context.applicationContext.packageManager
    private val cache = object : LruCache<String, Bitmap>(4 * 1024 * 1024) {
        override fun sizeOf(key: String, value: Bitmap): Int = value.allocationByteCount
    }

    suspend fun load(app: TvApp): Bitmap? = withContext(Dispatchers.IO) {
        cache.get(app.id) ?: try {
            val drawable = packageManager.getActivityIcon(ComponentName(app.packageName, app.activityName)).mutate()
            val bitmap = Bitmap.createBitmap(160, 160, Bitmap.Config.ARGB_8888)
            drawable.setBounds(0, 0, 160, 160)
            drawable.draw(Canvas(bitmap))
            cache.put(app.id, bitmap)
            bitmap
        } catch (_: android.content.pm.PackageManager.NameNotFoundException) { null }
    }
    fun clear() = cache.evictAll()
}
