package br.com.lucas.pomotimer.core.base.bottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLayoutChangeListener
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.CallSuper
import androidx.core.view.updatePadding
import androidx.viewbinding.ViewBinding
import br.com.lucas.pomotimer.core.extensions.MINUS_ONE
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

    private val deviceScreenHeight by lazy {
        activity?.findViewById<View>(Window.ID_ANDROID_CONTENT)?.height.orZero()
    }
    private val peekHeight by lazy {
        deviceScreenHeight.times(Double.SEVENTY_PERCENT).toInt()
    }
    private val unusedPeekHeight by lazy {
        deviceScreenHeight.times(Double.THIRTY_PERCENT).toInt()
    }
    private var dragHandleViewHeight = Int.MINUS_ONE
    private var containerHeight = Int.MINUS_ONE
    private val containerMinHeight
        get() = peekHeight.minus(dragHandleViewHeight)
    private val isContainerMinHeight
        get() = containerHeight != Int.MINUS_ONE && containerHeight <= containerMinHeight
    private var onContainerLayoutChangeListener =
        OnLayoutChangeListener { v, _, _, _, _, _, _, _, _ ->
            containerHeight = v?.height ?: Int.MINUS_ONE
        }

    private var _rootBinding: ComponentBaseBottomSheetDialogFragmentBinding? = null
    private val rootBinding
        get() = _rootBinding!!

    private var _binding: VB? = null
    protected val binding
        get() = _binding!!

    private val parent: View
        get() = view?.parent as View

    private val bottomSheetBehavior: BottomSheetBehavior<View>
        get() = BottomSheetBehavior.from(parent)

    open fun initView() {}

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _rootBinding =
            ComponentBaseBottomSheetDialogFragmentBinding.inflate(inflater, container, false)
        _binding = inflater(inflater, rootBinding.flContainer, true)
        return rootBinding.root
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupContainerHeight()
        getDragHandleViewHeight()
        setupBehavior()
        setupPeekHeight()
        setContainerBottomPadding()
        setupOnDismissListener()
        initView()
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        rootBinding.flContainer.removeOnLayoutChangeListener(onContainerLayoutChangeListener)
        _binding = null
        _rootBinding = null
        dialog?.setOnDismissListener(null)
    }

    private fun setupBehavior() = with(bottomSheetBehavior) {
        BottomSheetBehavior.from(rootBinding.llContent).apply {
            isDraggable = false
            state = BottomSheetBehavior.STATE_EXPANDED
        }
        addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {

            override fun onStateChanged(bottomSheet: View, newState: Int) {}

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                setContainerBottomPadding(slideOffset)
            }
        })
    }

    private fun setupOnDismissListener() {
        dialog?.setOnDismissListener {
            parentFragmentManager.beginTransaction()
                .remove(this@BaseBottomSheetDialogFragment).commit()
        }
    }

    private fun setContainerBottomPadding(slideOffset: Float = Float.ZERO) =
        with(rootBinding.flContainer) {
            val isExpanded = slideOffset == Float.ONE
            updatePadding(
                bottom = when {
                    isExpanded || isContainerMinHeight -> Int.ZERO
                    else -> (unusedPeekHeight - (unusedPeekHeight * slideOffset)).toInt()
                }
            )
        }

    private fun getDragHandleViewHeight() = with(rootBinding.dragHandleView) {
        post {
            dragHandleViewHeight = height
        }
    }

    private fun setupContainerHeight() = with(rootBinding.flContainer) {
        minimumHeight = containerMinHeight
        addOnLayoutChangeListener(onContainerLayoutChangeListener)
    }

    private fun setupPeekHeight() {
        bottomSheetBehavior.peekHeight = peekHeight
    }

    companion object {
        const val TAG = "POMO_TIMER_BOTTOM_SHEET_TAG"
    }
}