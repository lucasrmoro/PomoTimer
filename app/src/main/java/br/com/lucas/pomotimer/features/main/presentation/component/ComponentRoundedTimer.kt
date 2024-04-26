package br.com.lucas.pomotimer.features.main.presentation.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import br.com.lucas.pomotimer.core.delegate.TextDelegate
import br.com.lucas.pomotimer.core.delegate.ViewDelegate
import br.com.lucas.pomotimer.core.extensions.ZERO
import br.com.lucas.pomotimer.core.extensions.millisToMinutesAndSeconds
import br.com.lucas.pomotimer.databinding.ComponentRoundedTimerBinding

class ComponentRoundedTimer @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val binding = ComponentRoundedTimerBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    private val pbTimer by ViewDelegate(binding.pbTimer)
    private val tvTime by ViewDelegate(binding.tvTime)

    private var timeText by TextDelegate(tvTime)

    fun updateProgress(currentTimeInMillis: Long, totalTimeInMillis: Long) {
        setMinutesAndSecondsBy(currentTimeInMillis)
        pbTimer.max = totalTimeInMillis.toInt()
        pbTimer.setProgressCompat(currentTimeInMillis.toInt(), true)
    }

    fun reset() {
        setMinutesAndSecondsBy(Long.ZERO)
        pbTimer.setProgressCompat(Int.ZERO, true)
    }

    private fun setMinutesAndSecondsBy(timeInMillis: Long) {
        timeText = timeInMillis.millisToMinutesAndSeconds()
    }
}