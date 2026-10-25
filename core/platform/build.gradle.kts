plugins {
    alias(libs.plugins.android.library)

}
android {
    namespace = "fr.liquide.launcher.platform"
    compileSdk = 37
    defaultConfig { minSdk = 26 }
    compileOptions { sourceCompatibility = JavaVersion.VERSION_17; targetCompatibility = JavaVersion.VERSION_17 }

}
kotlin { jvmToolchain(17) }
dependencies {
    implementation(project(":core:model"))
    implementation(libs.coroutines)
    implementation(libs.datastore)
}
