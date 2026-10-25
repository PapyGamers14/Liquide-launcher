package fr.liquide.launcher.platform

import android.animation.ValueAnimator
import android.app.ActivityManager
import android.content.Context
import fr.liquide.launcher.model.DeviceCapabilities

fun deviceCapabilities(context: Context): DeviceCapabilities = DeviceCapabilities(
    lowRam = context.getSystemService(ActivityManager::class.java)?.isLowRamDevice ?: true,
    systemAnimationsEnabled = ValueAnimator.areAnimatorsEnabled(),
)
