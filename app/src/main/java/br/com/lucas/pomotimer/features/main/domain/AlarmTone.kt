package br.com.lucas.pomotimer.features.main.domain

import android.net.Uri
import br.com.lucas.pomotimer.uikit.adapter.generic.AdapterItem
import br.com.lucas.pomotimer.uikit.adapter.generic.AdapterItemType

data class AlarmTone(
    val name: String,
    val uri: Uri,
    val isSelected: Boolean = false
) : AdapterItem {

    override fun itemViewType(): AdapterItemType = AdapterItemType.ALARM_TONE

    override fun areItemsTheSame(toCompare: Any): Boolean =
        this.name == (toCompare as? AlarmTone)?.name

    override fun areContentsTheSame(toCompare: Any): Boolean = this == toCompare

}