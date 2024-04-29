package br.com.lucas.pomotimer.core.base.bottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.CallSuper
import androidx.core.view.updatePadding
import androidx.viewbinding.ViewBinding
import br.com.lucas.pomotimer.core.extensions.ONE
import br.com.lucas.pomotimer.core.extensions.SEVENTY_PERCENT
import br.com.lucas.pomotimer.core.extensions.THIRTY_PERCENT
import br.com.lucas.pomotimer.core.extensions.ZERO
import br.com.lucas.pomotimer.core.extensions.orZero
import br.com.lucas.pomotimer.databinding.ComponentBaseBottomSheetDialogFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetDialogFragment<VB : ViewBinding>(
    private val inflater: (LayoutInflater, ViewGroup?, Boolean) -> VB,
) : BottomSheetDialogFragment() {

    private var shouldUpdateNestedScrollViewHeight = false
    private val deviceScreenHeight by lazy {
        activity?.findViewById<View>(Window.ID_ANDROID_CONTENT)?.height.orZero()
    }
    private val bottomSheetPeekHeight by lazy {
        (deviceScreenHeight * Double.SEVENTY_PERCENT).toInt()
    }
    private val bottomSheetUnusedPeekHeight by lazy {
        ((deviceScreenHeight * Double.THIRTY_PERCENT)).toInt()
    }

    private var _binding: ComponentBaseBottomSheetDialogFragmentBinding? = null
    private val binding
        get() = _binding!!

    private var _contentBinding: VB? = null
    protected val contentBinding
        get() = _contentBinding ?: throw IllegalArgumentException("ViewBiding not found")

    protected val parent: View?
        get() = view?.parent as? View

    protected val bottomSheetBehavior: BottomSheetBehavior<View>?
        get() = parent?.let { BottomSheetBehavior.from(it) }

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            ComponentBaseBottomSheetDialogFragmentBinding.inflate(inflater, container, false)
        _contentBinding = inflater(inflater, binding.flContainer, true)
        return binding.root
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBehavior()
    }

    @CallSuper
    override fun onDestroyView() {
        _contentBinding = null
        _binding = null
        super.onDestroyView()
    }

    private fun setupBehavior() = bottomSheetBehavior?.run {
        BottomSheetBehavior.from(binding.llContent).apply {
            isDraggable = false
            state = BottomSheetBehavior.STATE_EXPANDED
        }
        peekHeight = bottomSheetPeekHeight
        setNestedScrollViewBottomPadding()
        addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {

            override fun onStateChanged(bottomSheet: View, newState: Int) {}

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                setNestedScrollViewBottomPadding(slideOffset)
            }
        })
    }

    private fun setNestedScrollViewBottomPadding(slideOffset: Float = Float.ZERO) {
        binding.nestedScrollViewContent.updatePadding(
            bottom = when {
                slideOffset < Float.ZERO || slideOffset == Float.ONE -> Int.ZERO
                else -> (bottomSheetUnusedPeekHeight - (bottomSheetUnusedPeekHeight * slideOffset)).toInt()
            }
        )
    }

    companion object {
        const val TAG = "POMO_TIMER_BOTTOM_SHEET_TAG"
    }
}