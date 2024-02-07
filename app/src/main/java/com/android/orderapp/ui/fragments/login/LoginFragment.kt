package com.android.orderapp.ui.fragments.login


import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.android.orderapp.R
import com.android.orderapp.databinding.FragmentLoginBinding
import com.android.orderapp.ui.base.BaseFragment
import com.android.orderapp.ui.base.FragmentInflate
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth


class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding>() {

    override val viewModel: LoginViewModel by activityViewModels()
    override val viewBindingInflater: FragmentInflate<FragmentLoginBinding>
        get() = FragmentLoginBinding::inflate
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.btmNavigation)

        firebaseAuth = FirebaseAuth.getInstance()

        bottomNav.visibility = View.VISIBLE

        binding.btnLogin.setOnClickListener {


            val email = binding.textLoginUsername.text.toString()
            val password = binding.textLoginPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()){
                firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                    if (it.isSuccessful){
                        findNavController().navigate(R.id.loginToHomePage)
                    }else {
                        Toast.makeText(requireContext(),it.exception.toString(),Toast.LENGTH_SHORT).show()
                    }
                }
            }else {
                Toast.makeText(requireContext(),"Fields cannot be empty",Toast.LENGTH_SHORT).show()
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