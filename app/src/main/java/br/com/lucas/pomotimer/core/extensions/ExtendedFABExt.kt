package br.com.lucas.pomotimer.core.extensions

import android.graphics.drawable.Animatable
import androidx.annotation.DrawableRes
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

fun ExtendedFloatingActionButton.setAnimatedVectorDrawable(@DrawableRes drawableRes: Int) {
    if (tag == drawableRes.toString()) return
    tag = drawableRes.toString()
    icon = context.getDrawableCompat(drawableRes)
    (icon as? Animatable)?.start()
}