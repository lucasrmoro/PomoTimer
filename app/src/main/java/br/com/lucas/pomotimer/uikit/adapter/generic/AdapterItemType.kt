package br.com.lucas.pomotimer.uikit.adapter.generic

enum class AdapterItemType {
    ALARM_TONE;

    companion object {
        fun fromOrdinal(value: Int) = entries[value]
    }
}