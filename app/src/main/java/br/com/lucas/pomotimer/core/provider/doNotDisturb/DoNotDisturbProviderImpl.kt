package br.com.lucas.pomotimer.core.provider.doNotDisturb

import android.content.Context
import br.com.lucas.pomotimer.core.extensions.isDoNoDisturbModeEnabled
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.annotation.Single

@Single
class DoNotDisturbProviderImpl(context: Context) : DoNotDisturbProvider {

    private val _onDoNotDisturbChange = MutableStateFlow(context.isDoNoDisturbModeEnabled())
    override val onDoNotDisturbChange: StateFlow<Boolean> = _onDoNotDisturbChange.asStateFlow()

    override fun onDoNotDisturbChange(enabled: Boolean) {
        _onDoNotDisturbChange.value = enabled
    }

}