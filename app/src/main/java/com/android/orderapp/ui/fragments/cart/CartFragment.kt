package com.android.orderapp.ui.fragments.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.android.orderapp.R
import com.android.orderapp.databinding.FragmentCartBinding
import com.android.orderapp.ui.base.BaseFragment
import com.android.orderapp.ui.base.FragmentInflate


class CartFragment : BaseFragment<CartViewModel,FragmentCartBinding>() {

    override val viewModel: CartViewModel by viewModels()
    override val viewBindingInflater: FragmentInflate<FragmentCartBinding>
        get() = FragmentCartBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }

}