package br.com.lucas.pomotimer.features.main.presentation.fragment

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import br.com.lucas.pomotimer.R
import br.com.lucas.pomotimer.core.base.fragment.BaseFragment
import br.com.lucas.pomotimer.core.extensions.setAnimatedVectorDrawable
import br.com.lucas.pomotimer.core.provider.CountdownTimerProvider.Status
import br.com.lucas.pomotimer.databinding.FragmentMainBinding
import br.com.lucas.pomotimer.features.main.presentation.viewModel.MainViewModel

class MainFragment :
    BaseFragment<FragmentMainBinding, MainViewModel>(FragmentMainBinding::inflate) {

    override fun setupViews(): Unit = with(binding) {
        viewModel.workTimeDurationInMillis.also {
            componentRoundedTimer.updateProgress(it, it)
        }
    }

    override fun setupListeners() = with(binding) {
        btnStartStop.setOnClickListener {
            viewModel.startOrStopTimer()
        }
    }

    override fun setupCollectors() = withViewModel {
        onTimeUpdate.collect {
            when (it) {
                is Status.RUNNING -> {
                    setupStartStopButton(R.string.stop, R.drawable.avd_start_to_stop)
                    binding.componentRoundedTimer.updateProgress(
                        it.remainingTimeInMillis,
                        it.durationInMillis
                    )
                }

                is Status.STOPPED -> {
                    setupStartStopButton(R.string.start, R.drawable.avd_stop_to_start)
                    binding.componentRoundedTimer.reset()
                }

                else -> {}
            }
        }
    }

    private fun setupStartStopButton(
        @StringRes buttonTextRes: Int,
        @DrawableRes buttonIconRes: Int
    ) = with(binding) {
        btnStartStop.setText(buttonTextRes)
        btnStartStop.setAnimatedVectorDrawable(buttonIconRes)
    }
}