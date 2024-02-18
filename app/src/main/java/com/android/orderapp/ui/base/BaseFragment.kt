package com.android.orderapp.ui.base

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

typealias  FragmentInflate<T> = (inflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean) -> T

abstract class BaseFragment<VM : BaseViewModel, VB : ViewBinding> : Fragment() {
    private var _binding: VB? = null

    val binding: VB get() = _binding!!

    open val viewBindingInflater: FragmentInflate<VB>? = null

    abstract val viewModel: VM

    private var progressBar: ProgressBar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (_binding == null) {
            _binding = viewBindingInflater?.invoke(inflater, container, false)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.loadingState.collectLatest {
                if (it == LoadingState.SHOW) {
                    showLoadingDialog()
                } else {
                    hideLoadingDialog()
                }
            }
        }
    }


    protected fun showLoadingDialog() {
        if (progressBar == null) {
            progressBar = ProgressBar(requireContext())
            progressBar?.isIndeterminate = true
        } else {
            progressBar?.visibility= View.GONE
        }
    }

    protected fun hideLoadingDialog() {
        progressBar?.visibility = View.GONE
    }

    protected fun showToastMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}