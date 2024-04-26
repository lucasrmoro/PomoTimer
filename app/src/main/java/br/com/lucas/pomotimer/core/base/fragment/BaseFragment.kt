package br.com.lucas.pomotimer.core.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType
import kotlinx.coroutines.launch

abstract class BaseFragment<VB : ViewBinding, VM : ViewModel>(
    private val inflater: (LayoutInflater, ViewGroup?, Boolean) -> VB,
) : Fragment() {

    private var _binding: VB? = null
    protected val binding
        get() = _binding ?: throw IllegalArgumentException("ViewBiding not found")

    private var _viewModel: VM? = null
    protected val viewModel: VM
        get() = _viewModel ?: throw IllegalArgumentException("ViewModel not found")

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflater(inflater, container, false)
        _viewModel = ViewModelProvider(this)[getViewModelClass()]
        return binding.root
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupListeners()
        setupCollectors()
    }

    @CallSuper
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    open fun setupViews() {}
    open fun setupListeners() {}
    open fun setupCollectors() {}

    protected fun showToast(@StringRes message: Int, duration: Int = Toast.LENGTH_SHORT) {
        context?.let {
            Toast.makeText(it, getString(message), duration).show()
        }
    }

    protected fun withViewModel(block: suspend VM.() -> Unit) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                block(viewModel)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun getViewModelClass() = (javaClass.genericSuperclass as ParameterizedType)
        .actualTypeArguments
        .firstOrNull { it is Class<*> && ViewModel::class.java.isAssignableFrom(it) }
        ?.let { it as Class<VM> }
        ?: throw IllegalStateException("ViewModel class not found")

}