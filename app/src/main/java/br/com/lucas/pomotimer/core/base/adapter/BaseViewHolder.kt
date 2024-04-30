package br.com.lucas.pomotimer.core.base.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import br.com.lucas.pomotimer.uikit.adapter.generic.AdapterItem
import br.com.lucas.pomotimer.uikit.adapter.generic.GenericAdapterCallback

abstract class BaseViewHolder<VB : ViewBinding, CB : GenericAdapterCallback, T : AdapterItem>(
    private val binding: VB,
    private val callbacks: CB
) : RecyclerView.ViewHolder(binding.root) {

    abstract fun onBind(item: T)

    protected fun bind(block: VB.(CB) -> Unit) {
        block(binding, callbacks)
    }

}