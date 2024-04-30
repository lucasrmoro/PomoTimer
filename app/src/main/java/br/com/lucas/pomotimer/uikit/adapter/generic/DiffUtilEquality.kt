package br.com.lucas.pomotimer.uikit.adapter.generic


interface DiffUtilEquality {

    fun areItemsTheSame(toCompare: Any): Boolean
    fun areContentsTheSame(toCompare: Any): Boolean

}