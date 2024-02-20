package com.android.orderapp.ui.fragments.profile

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import com.android.orderapp.R
import com.android.orderapp.databinding.FragmentEditProfileBinding
import com.android.orderapp.ui.base.BaseFragment
import com.android.orderapp.ui.base.FragmentInflate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class EditProfileFragment : BaseFragment<EditProfileViewModel, FragmentEditProfileBinding>() {

    private val mail: MutableLiveData<String> = MutableLiveData()
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    override val viewModel: EditProfileViewModel by viewModels()
    override val viewBindingInflater: FragmentInflate<FragmentEditProfileBinding>
        get() = FragmentEditProfileBinding::inflate


    init {
        if (firebaseAuth.currentUser == null) {
            showToast("Kullanıcı yok")
        }

        val uid = firebaseAuth.currentUser!!.uid
        val docRef = db.collection("users").document(uid)

        docRef.get().addOnSuccessListener { document ->
            if (document.exists()) {
                mail.value = document.getString("mail") ?: ""
            } else {
                // Handle user not found
                showToast("Kullanıcı bulunamadı")
            }
        }.addOnFailureListener { exception ->
            // Handle potential errors during document retrieval
            Log.e("EditProfileViewModel", "Error retrieving user data", exception)
            showToast("Bir hata oluştu")
        }
    }

    private fun showToast(message: String) {

    }


}