package com.android.orderapp.ui.fragments.profile

import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
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
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class EditProfileFragment : BaseFragment<EditProfileViewModel, FragmentEditProfileBinding>() {


    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override val viewModel: EditProfileViewModel by viewModels()
    override val viewBindingInflater: FragmentInflate<FragmentEditProfileBinding>
        get() = FragmentEditProfileBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = Firebase.firestore
        val usersRef = db.collection("users")

        usersRef.get().addOnSuccessListener { result ->
            for (document in result) {
                val email = document.getString("email")
                if (email != null) {
                    binding.profileEmail.text = SpannableStringBuilder(email)
                }
            }
        }.addOnFailureListener {
            Log.w(TAG, "error getting documents", it)
        }


    }




    private fun showToast(message: String) {

    }


}