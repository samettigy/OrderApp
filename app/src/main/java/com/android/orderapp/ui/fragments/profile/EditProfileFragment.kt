package com.android.orderapp.ui.fragments.profile

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.android.orderapp.R
import com.android.orderapp.databinding.FragmentEditProfileBinding
import com.android.orderapp.ui.base.BaseFragment
import com.android.orderapp.ui.base.FragmentInflate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class EditProfileFragment : BaseFragment<EditProfileViewModel, FragmentEditProfileBinding>() {

    private lateinit var firebaseAuth: FirebaseAuth
    override val viewModel: EditProfileViewModel by viewModels()
    override val viewBindingInflater: FragmentInflate<FragmentEditProfileBinding>
        get() = FragmentEditProfileBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()

        if (firebaseAuth.currentUser == null) {
            Toast.makeText(requireContext(),"Kullanıcı yok",Toast.LENGTH_LONG)
            return
        }

        val uid = firebaseAuth.currentUser?.uid.toString()
        val docRef = db.collection("users").document(uid)

        docRef.get().addOnSuccessListener { document ->
            if (document.exists()) {
                val mail = document.get("mail") as String
                binding.profileEmail.setText(mail)
            } else {
                // Kullanıcı bulunamadı hatası
            }
        }

    }


}