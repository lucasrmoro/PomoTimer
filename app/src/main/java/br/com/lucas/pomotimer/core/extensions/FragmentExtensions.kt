package br.com.lucas.pomotimer.core.extensions

import br.com.lucas.pomotimer.core.base.bottomSheet.BaseBottomSheetDialogFragment
import br.com.lucas.pomotimer.core.base.fragment.BaseFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

fun BaseFragment<*, *>.showBottomSheet(bottomSheet: BottomSheetDialogFragment) {
    if (bottomSheet.dialog?.isShowing != true && !bottomSheet.isRemoving && !bottomSheet.isAdded) {
        bottomSheet.show(childFragmentManager, BaseBottomSheetDialogFragment.TAG)
    }
}