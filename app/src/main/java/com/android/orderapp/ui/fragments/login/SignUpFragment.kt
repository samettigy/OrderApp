package com.android.orderapp.ui.fragments.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.orderapp.MainActivity
import com.android.orderapp.R
import com.android.orderapp.databinding.FragmentSignUpBinding
import com.android.orderapp.ui.base.BaseFragment
import com.android.orderapp.ui.base.FragmentInflate
import com.google.firebase.auth.FirebaseAuth


class SignUpFragment : BaseFragment<SignUpViewModel,FragmentSignUpBinding>() {

    override val viewModel: SignUpViewModel by viewModels()
    override val viewBindingInflater: FragmentInflate<FragmentSignUpBinding>?
        get() = FragmentSignUpBinding::inflate
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnSignup.setOnClickListener {
            val email = binding.signupUsername.text.toString()
            val password = binding.signupPassword.text.toString()
            val confirmPassword = binding.signupConfirmPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if (password == confirmPassword){
                    firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val intent = Intent(requireContext(),MainActivity::class.java)
                            startActivity(intent)
                        }else {
                            Toast.makeText(requireContext(), it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(requireContext(),"Password does not match",Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(),"Fields cannot be empty",Toast.LENGTH_SHORT).show()
            }

        }


        binding.btnSignupToLogin.setOnClickListener {
            findNavController().navigate(R.id.signupToLogin)
        }

    }

}