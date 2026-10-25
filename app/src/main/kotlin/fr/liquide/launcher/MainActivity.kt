package fr.liquide.launcher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import fr.liquide.launcher.home.HomeScreen
import fr.liquide.launcher.home.HomeViewModel
import fr.liquide.launcher.platform.deviceCapabilities

class MainActivity : ComponentActivity() {
    private val dependencies get() = application as LauncherApplication
    private val viewModel by lazy {
        ViewModelProvider(this, HomeViewModel.factory(dependencies.apps, dependencies.preferences))[HomeViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = android.view.View.SYSTEM_UI_FLAG_FULLSCREEN or
            android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
            android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
            android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
            android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
            android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        setContent { HomeScreen(viewModel, dependencies.icons, dependencies.launcher, deviceCapabilities(this)) }
    }
    override fun onResume() {
        super.onResume()
        dependencies.icons.clear()
        viewModel.refresh()
    }
}
