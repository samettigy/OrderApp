package com.android.orderapp.ui.fragments.login


import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.android.orderapp.R
import com.android.orderapp.databinding.FragmentLoginBinding
import com.android.orderapp.ui.base.BaseFragment
import com.android.orderapp.ui.base.FragmentInflate
import com.google.android.material.bottomnavigation.BottomNavigationView


class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding>() {

    override val viewModel: LoginViewModel by activityViewModels()
    override val viewBindingInflater: FragmentInflate<FragmentLoginBinding>
        get() = FragmentLoginBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.loginToHomePage)
        }

        binding.textForgot.setOnClickListener {
            findNavController().navigate(R.id.loginToForgotPassword)
        }

        binding.textSignUp.setOnClickListener {
            findNavController().navigate(R.id.loginToSignup)
        }

    }




}