package br.com.lucas.pomotimer.uikit.adapter.generic

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.lucas.pomotimer.databinding.RvAlarmToneItemBinding
import br.com.lucas.pomotimer.features.main.domain.AlarmTone
import br.com.lucas.pomotimer.uikit.adapter.viewholder.alarmTone.VhAlarmTone
import br.com.lucas.pomotimer.uikit.adapter.viewholder.alarmTone.VhAlarmToneCallbacks

class GenericAdapter(
    private val adapterCallbacks: GenericAdapterCallback? = null
) : ListAdapter<AdapterItem, RecyclerView.ViewHolder>(GenericItemDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (AdapterItemType.entries[viewType]) {
            AdapterItemType.ALARM_TONE -> VhAlarmTone(
                RvAlarmToneItemBinding.inflate(layoutInflater, parent, false),
                adapterCallbacks as VhAlarmToneCallbacks
            )
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val item = currentList[position]
        when (holder) {
            is VhAlarmTone -> holder.onBind(item as AlarmTone)
        }
    }

    override fun getItemCount() = currentList.size

    override fun getItemViewType(position: Int): Int = currentList[position].itemViewType().ordinal
}