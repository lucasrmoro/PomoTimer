package br.com.lucas.pomotimer.core.extensions

import android.app.NotificationManager
import android.app.NotificationManager.INTERRUPTION_FILTER_ALARMS
import android.app.NotificationManager.INTERRUPTION_FILTER_ALL
import android.app.NotificationManager.INTERRUPTION_FILTER_NONE
import android.app.NotificationManager.INTERRUPTION_FILTER_PRIORITY
import android.content.Context
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

fun Context.getDrawableCompat(@DrawableRes drawableRes: Int) =
    ContextCompat.getDrawable(this, drawableRes)

fun Context?.isDoNotDisturbModePermissionGranted() =
    this?.getNotificationManager()?.isNotificationPolicyAccessGranted ?: false

fun Context?.isDoNoDisturbModeEnabled() =
    this?.getNotificationManager()?.currentInterruptionFilter.run {
        this == INTERRUPTION_FILTER_NONE || this == INTERRUPTION_FILTER_PRIORITY || this == INTERRUPTION_FILTER_ALARMS
    }

fun Context.setDoNotDisturbMode(isEnabled: Boolean) {
    getNotificationManager().setInterruptionFilter(
        if (isEnabled) INTERRUPTION_FILTER_ALARMS else INTERRUPTION_FILTER_ALL
    )
}

fun Context.getNotificationManager() =
    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager