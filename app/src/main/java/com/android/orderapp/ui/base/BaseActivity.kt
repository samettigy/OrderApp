package com.android.orderapp.ui.base

import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

typealias ActivityInflate<T> = (LayoutInflater) -> T

abstract class BaseActivity<VM : BaseViewModel, VB : ViewBinding> : AppCompatActivity() {

    abstract val viewModel : VM

    open val viewBindingInflater : ActivityInflate<VB>? = null

    lateinit var binding: VB

    open  var viewBinding : VB? = null

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        viewBindingInflater?.invoke(layoutInflater)?.let { nonNullViewBinding ->
            viewBinding = nonNullViewBinding
            binding = nonNullViewBinding
        }

        setContentView(binding.root)

    }



}