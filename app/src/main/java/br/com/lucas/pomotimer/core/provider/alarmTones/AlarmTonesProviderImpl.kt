package br.com.lucas.pomotimer.core.provider.alarmTones

import android.content.Context
import br.com.lucas.pomotimer.core.extensions.getDeviceAlarmTones
import br.com.lucas.pomotimer.features.main.domain.AlarmTone
import org.koin.core.annotation.Single

@Single
class AlarmTonesProviderImpl(private val context: Context) : AlarmTonesProvider {

    override fun getAll(): List<AlarmTone> = context.getDeviceAlarmTones().take(5)

}