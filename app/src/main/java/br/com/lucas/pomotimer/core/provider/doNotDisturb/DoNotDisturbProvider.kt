package br.com.lucas.pomotimer.core.provider.doNotDisturb

import kotlinx.coroutines.flow.StateFlow

interface DoNotDisturbProvider {

    val onDoNotDisturbChange: StateFlow<Boolean>

    fun onDoNotDisturbChange(enabled: Boolean)

}