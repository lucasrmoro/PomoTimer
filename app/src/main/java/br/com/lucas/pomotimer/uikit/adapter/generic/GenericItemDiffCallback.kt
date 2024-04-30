package br.com.lucas.pomotimer.uikit.adapter.generic

import androidx.recyclerview.widget.DiffUtil
import br.com.lucas.pomotimer.uikit.adapter.generic.AdapterItem

class GenericItemDiffCallback : DiffUtil.ItemCallback<AdapterItem>() {
    override fun areItemsTheSame(oldItem: AdapterItem, newItem: AdapterItem): Boolean {
        return oldItem.areItemsTheSame(newItem)
    }

    override fun areContentsTheSame(oldItem: AdapterItem, newItem: AdapterItem): Boolean {
        return oldItem.areContentsTheSame(newItem)
    }
}