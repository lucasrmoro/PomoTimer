package br.com.lucas.pomotimer.features.main.presentation.component

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.SimpleItemAnimator
import br.com.lucas.pomotimer.core.base.bottomSheet.BaseBottomSheetDialogFragment
import br.com.lucas.pomotimer.databinding.BottomSheetRingtonesBinding
import br.com.lucas.pomotimer.features.main.domain.AlarmTone
import br.com.lucas.pomotimer.uikit.adapter.generic.GenericAdapter
import br.com.lucas.pomotimer.uikit.adapter.viewholder.alarmTone.VhAlarmToneCallbacks
import kotlinx.coroutines.launch

class BottomSheetRingtones(private val alarmTonesCallbacks: VhAlarmToneCallbacks) :
    BaseBottomSheetDialogFragment<BottomSheetRingtonesBinding>(BottomSheetRingtonesBinding::inflate) {

    private var alarmTonesAdapter: GenericAdapter? = null

    override fun initView() {
        setupAlarmTonesRecyclerView()
    }

    fun submitList(list: List<AlarmTone>?) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                alarmTonesAdapter?.submitList(list)
            }
        }
    }

    fun setOnAddButtonClickListener(block: (View) -> Unit) {
        binding.btnAdd.setOnClickListener(block)
    }

    private fun setupAlarmTonesRecyclerView() = with(binding.rvAlarmTones) {
        alarmTonesAdapter = GenericAdapter(alarmTonesCallbacks)
        adapter = alarmTonesAdapter
        (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        alarmTonesAdapter = null
    }

}