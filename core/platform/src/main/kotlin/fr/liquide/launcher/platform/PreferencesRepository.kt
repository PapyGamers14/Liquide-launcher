package fr.liquide.launcher.platform

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import fr.liquide.launcher.model.LauncherPreferences
import java.io.IOException
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

private val Context.launcherDataStore by preferencesDataStore(name = "launcher")

class PreferencesRepository(context: Context) {
    private val store = context.applicationContext.launcherDataStore
    private val favorites = stringSetPreferencesKey("favorite_components")
    private val motion = booleanPreferencesKey("reduced_motion")

    val preferences = store.data.catch { cause ->
        if (cause is IOException) emit(emptyPreferences()) else throw cause
    }.map { LauncherPreferences(it[favorites].orEmpty(), it[motion] ?: false) }

    suspend fun toggleFavorite(id: String) {
        store.edit { values ->
            val current = values[favorites].orEmpty()
            values[favorites] = if (id in current) current - id else current + id
        }
    }

    suspend fun setReducedMotion(enabled: Boolean) { store.edit { it[motion] = enabled } }
}
