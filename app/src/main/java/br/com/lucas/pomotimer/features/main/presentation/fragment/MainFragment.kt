package br.com.lucas.pomotimer.features.main.presentation.fragment

import android.content.Intent
import android.provider.Settings
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.lifecycle.lifecycleScope
import br.com.lucas.pomotimer.R
import br.com.lucas.pomotimer.core.base.fragment.BaseFragment
import br.com.lucas.pomotimer.core.extensions.isDoNoDisturbModeEnabled
import br.com.lucas.pomotimer.core.extensions.isDoNotDisturbModePermissionGranted
import br.com.lucas.pomotimer.core.extensions.setAnimatedVectorDrawable
import br.com.lucas.pomotimer.core.extensions.setDoNotDisturbMode
import br.com.lucas.pomotimer.core.extensions.showBottomSheet
import br.com.lucas.pomotimer.core.provider.timer.CountdownTimerProvider.Status
import br.com.lucas.pomotimer.databinding.FragmentMainBinding
import br.com.lucas.pomotimer.features.main.domain.AlarmTone
import br.com.lucas.pomotimer.features.main.presentation.component.BottomSheetRingtones
import br.com.lucas.pomotimer.features.main.presentation.viewModel.MainViewModel
import br.com.lucas.pomotimer.uikit.adapter.viewholder.alarmTone.VhAlarmToneCallbacks

class MainFragment :
    BaseFragment<FragmentMainBinding, MainViewModel>(FragmentMainBinding::inflate) {

    private val alarmTonesBottomSheet = BottomSheetRingtones(getAlarmTonesAdapterCallbacks())
    private val doNotDisturbPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            toggleDoNotDisturbMode(false)
        }

    override fun setupViews(): Unit = with(binding) {
        viewModel.workTimeDurationInMillis.also {
            componentRoundedTimer.updateProgress(it, it)
        }
    }

    override fun setupListeners() = with(binding) {
        btnDoNotDisturb.setOnClickListener {
            toggleDoNotDisturbMode(true)
        }
        btnRingtones.setOnClickListener {
            viewModel.getAlarmTones()
            showBottomSheet(alarmTonesBottomSheet)
        }
        btnStartStop.setOnClickListener {
            viewModel.startOrStopTimer()
        }
    }

    override fun setupCollectors() = with(viewModel) {
        onTimeUpdate.collectLifecycleAware {
            when (this) {
                is Status.RUNNING -> {
                    setupStartStopButton(R.string.stop, R.drawable.avd_start_to_stop)
                    binding.componentRoundedTimer.updateProgress(
                        remainingTimeInMillis,
                        durationInMillis
                    )
                }

                is Status.STOPPED -> {
                    setupStartStopButton(R.string.start, R.drawable.avd_stop_to_start)
                    binding.componentRoundedTimer.reset()
                }

                else -> {}
            }
        }
        onDoNotDisturbChange.collectLifecycleAware {
            binding.btnDoNotDisturb.setImageResource(
                if (this) R.drawable.ic_do_not_disturb_on else R.drawable.ic_do_not_disturb_off
            )
        }
        onGetAlarmTones.collectLifecycleAware(alarmTonesBottomSheet.lifecycleScope) {
            alarmTonesBottomSheet.submitList(this)
        }
    }

    private fun setupStartStopButton(
        @StringRes buttonTextRes: Int,
        @DrawableRes buttonIconRes: Int
    ) = with(binding) {
        btnStartStop.setText(buttonTextRes)
        btnStartStop.setAnimatedVectorDrawable(buttonIconRes)
    }

    private fun toggleDoNotDisturbMode(shouldOpenSettingsScreen: Boolean) {
        if (!context.isDoNotDisturbModePermissionGranted()) {
            if (shouldOpenSettingsScreen) doNotDisturbPermissionLauncher.launch(
                Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
            )
            return
        }
        context?.setDoNotDisturbMode(!context.isDoNoDisturbModeEnabled())
        binding.btnDoNotDisturb.contentDescription = getString(
            R.string.disable_do_not_disturb_mode.takeIf {
                context.isDoNotDisturbModePermissionGranted()
            } ?: R.string.enable_do_not_disturb_mode
        )
    }

    private fun getAlarmTonesAdapterCallbacks(): VhAlarmToneCallbacks =
        object : VhAlarmToneCallbacks {
            override fun onSelect(item: AlarmTone) {
                viewModel.onSelectAlarmTone(item)
            }

            override fun onPlayStop(item: AlarmTone) {
                Log.d("TAG", "onPlayStop: $item")
            }
        }
}