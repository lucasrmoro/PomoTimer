package br.com.lucas.pomotimer.core.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import br.com.lucas.pomotimer.core.extensions.isDoNoDisturbModeEnabled
import br.com.lucas.pomotimer.core.provider.doNotDisturb.DoNotDisturbProvider
import org.koin.java.KoinJavaComponent.inject

class DoNotDisturbBroadcastReceiver : BroadcastReceiver() {

    private val doNotDisturbProvider by inject<DoNotDisturbProvider>(DoNotDisturbProvider::class.java)

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action != NotificationManager.ACTION_INTERRUPTION_FILTER_CHANGED) return
        doNotDisturbProvider.onDoNotDisturbChange(context.isDoNoDisturbModeEnabled())
    }

}