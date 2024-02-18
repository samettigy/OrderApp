package com.android.orderapp.ui.fragments.login


import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.orderapp.R
import com.android.orderapp.databinding.FragmentLoginBinding
import com.android.orderapp.ui.base.BaseFragment
import com.android.orderapp.ui.base.FragmentInflate
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding>() {

    override val viewModel: LoginViewModel by viewModels()
    override val viewBindingInflater: FragmentInflate<FragmentLoginBinding>
        get() = FragmentLoginBinding::inflate


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            val email = binding.textLoginUsername.text.toString()
            val password = binding.textLoginPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.singIn(
                    email = email,
                    password = password,
                    loginSuccess = {
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    },
                    loginError = { message ->
                        showToastMessage(message)
                    })
            } else {
                showToastMessage("Fields cannot be empty")
            }
        }

        binding.textForgot.setOnClickListener {
            findNavController().navigate(R.id.loginToForgotPassword)
        }

        binding.textSignUp.setOnClickListener {
            findNavController().navigate(R.id.loginToSignup)
        }
    }


}