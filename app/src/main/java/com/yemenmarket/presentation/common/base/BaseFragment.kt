package com.yemen.market.presentation.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.yemen.market.presentation.common.extensions.showToast

/**
 * Base Fragment for all fragments in the app
 * @param T ViewBinding type
 */
abstract class BaseFragment<T : ViewBinding> : Fragment() {

    private var _binding: T? = null
    protected val binding get() = _binding!!
    protected abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Set RTL layout direction
        view.layoutDirection = android.view.View.LAYOUT_DIRECTION_RTL
        
        setupView()
        setupObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected abstract fun setupView()
    protected open fun setupObservers() {}

    protected fun showLoading() {
        // Implement loading in fragment
    }

    protected fun hideLoading() {
        // Hide loading in fragment
    }

    protected fun showError(message: String) {
        showToast(message)
    }

    protected fun showError(resId: Int) {
        showToast(getString(resId))
    }

    protected fun navigateBack() {
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }
}
