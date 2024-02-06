package com.android.orderapp.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

typealias  FragmentInflate<T> = (inflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean) -> T

abstract class BaseFragment<VM : ViewModel, VB : ViewBinding> : Fragment() {
    private var _binding: VB? = null

    val binding: VB get() = _binding!!


    open val viewBindingInflater: FragmentInflate<VB>? = null

    abstract val viewModel: VM

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
}