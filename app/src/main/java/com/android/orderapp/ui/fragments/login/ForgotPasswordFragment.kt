package com.android.orderapp.ui.fragments.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.orderapp.R
import com.android.orderapp.databinding.FragmentForgotPasswordBinding
import com.android.orderapp.ui.base.BaseFragment
import com.android.orderapp.ui.base.FragmentInflate
import com.google.firebase.auth.FirebaseAuth


class ForgotPasswordFragment : BaseFragment<ForgotPasswordViewModel,FragmentForgotPasswordBinding>() {

    override val viewModel: ForgotPasswordViewModel by viewModels()
    override val viewBindingInflater: FragmentInflate<FragmentForgotPasswordBinding>?
        get() = FragmentForgotPasswordBinding::inflate
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnReset.setOnClickListener {
            val mail = binding.forgotUsername.text.toString()
            resetPassword(mail)
        }

        binding.btnForgotToLogin.setOnClickListener {
            findNavController().navigate(R.id.forgotToLogin)
        }



    }

    fun resetPassword(email:String) {
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(requireContext(),"Link sent to your e-mail", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(),"Hata verdimmm :)", Toast.LENGTH_SHORT).show()
            }
        }
    }

}