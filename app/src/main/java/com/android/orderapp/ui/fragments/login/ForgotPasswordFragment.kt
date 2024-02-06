package com.android.orderapp.ui.fragments.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.orderapp.R
import com.android.orderapp.databinding.FragmentForgotPasswordBinding
import com.android.orderapp.ui.base.BaseFragment
import com.android.orderapp.ui.base.FragmentInflate


class ForgotPasswordFragment : BaseFragment<ForgotPasswordViewModel,FragmentForgotPasswordBinding>() {

    override val viewModel: ForgotPasswordViewModel by viewModels()
    override val viewBindingInflater: FragmentInflate<FragmentForgotPasswordBinding>?
        get() = FragmentForgotPasswordBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnForgotToLogin.setOnClickListener {
            findNavController().navigate(R.id.forgotToLogin)
        }

    }

}