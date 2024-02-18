package com.android.orderapp.ui.fragments.profile

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.android.orderapp.R
import com.android.orderapp.databinding.FragmentProfileBinding
import com.android.orderapp.databinding.FragmentSplashBinding
import com.android.orderapp.ui.base.BaseFragment
import com.android.orderapp.ui.base.FragmentInflate
import com.android.orderapp.ui.fragments.splash.SplashViewModel
import com.google.firebase.auth.FirebaseAuth


class ProfileFragment : BaseFragment<ProfileViewModel, FragmentProfileBinding>() {

    private lateinit var firebaseAuth: FirebaseAuth
    override val viewModel: ProfileViewModel by viewModels()
    override val viewBindingInflater: FragmentInflate<FragmentProfileBinding>
        get() = FragmentProfileBinding::inflate


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.editButton.setOnClickListener {
            findNavController().navigate(R.id.editProfileFragment)
        }

        binding.exitButton.setOnClickListener {
            val alertDialogBuilder = AlertDialog.Builder(requireContext())
            alertDialogBuilder.setMessage("Emin misiniz?")
            alertDialogBuilder.setPositiveButton("Evet") { dialog, which ->
                FirebaseAuth.getInstance().signOut()
                findNavController().navigate(R.id.profileFragmentToLoginFragment)
            }
            alertDialogBuilder.setNegativeButton("Hayır") { dialog, which ->

            }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }

    }


}