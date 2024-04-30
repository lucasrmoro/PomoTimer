package br.com.lucas.pomotimer.uikit.adapter.generic

interface AdapterItem : DiffUtilEquality {

    fun itemViewType(): AdapterItemType

}