package br.com.lucas.pomotimer.core.extensions

import br.com.lucas.pomotimer.core.base.bottomSheet.BaseBottomSheetDialogFragment
import br.com.lucas.pomotimer.core.base.fragment.BaseFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

fun BaseFragment<*, *>.showBottomSheet(bottomSheet: BottomSheetDialogFragment) {
    if (!bottomSheet.isAdded &&
        parentFragmentManager.findFragmentByTag(BaseBottomSheetDialogFragment.TAG) == null &&
        activity?.isDestroyed != true && activity?.isFinishing != true
    ) {
        bottomSheet.show(parentFragmentManager, BaseBottomSheetDialogFragment.TAG)
    }
}