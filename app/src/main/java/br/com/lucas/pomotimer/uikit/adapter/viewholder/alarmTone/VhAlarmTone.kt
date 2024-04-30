package br.com.lucas.pomotimer.uikit.adapter.viewholder.alarmTone

import br.com.lucas.pomotimer.core.base.adapter.BaseViewHolder
import br.com.lucas.pomotimer.databinding.RvAlarmToneItemBinding
import br.com.lucas.pomotimer.features.main.domain.AlarmTone

class VhAlarmTone(binding: RvAlarmToneItemBinding, callbacks: VhAlarmToneCallbacks) :
    BaseViewHolder<RvAlarmToneItemBinding, VhAlarmToneCallbacks, AlarmTone>(binding, callbacks) {

    override fun onBind(item: AlarmTone) = bind {
        tvAlarmTone.text = item.name
        radioButton.isChecked = item.isSelected
        root.setOnClickListener {_ ->
            it.onSelect(item)
        }
        radioButton.isClickable = false
    }
}