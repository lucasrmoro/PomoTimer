package br.com.lucas.pomotimer.core.delegate

import android.view.View
import android.widget.TextView
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class TextDelegate(private val textView: TextView) : ReadWriteProperty<View, String?> {

    override fun getValue(thisRef: View, property: KProperty<*>): String? =
        textView.text?.toString()

    override fun setValue(thisRef: View, property: KProperty<*>, value: String?) {
        textView.text = value
    }

}