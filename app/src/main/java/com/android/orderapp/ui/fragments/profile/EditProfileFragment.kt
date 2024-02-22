package com.android.orderapp.ui.fragments.profile

import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.android.orderapp.databinding.FragmentEditProfileBinding
import com.android.orderapp.ui.base.BaseFragment
import com.android.orderapp.ui.base.FragmentInflate
import com.google.firebase.auth.FirebaseAuth
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
        val emailText = binding.profileEmail
        val uuid = firebaseAuth.currentUser?.uid
        val docRef = db.collection("users").document("$uuid")

        docRef.get().addOnSuccessListener { document ->
            if (document != null) {
                val email = document.getString("email")
                emailText.text = SpannableStringBuilder(email)
            } else {
                showToastMessage("belge boş.")
            }
        }.addOnFailureListener {
            Log.d(TAG, "Belge alınamadı: ", it)
        }


        binding.updateButton.setOnClickListener {
            val edittextName = binding.profileName.text.toString()
            val edittextSurname = binding.profileSurname.text.toString()
            val edittextGender = binding.profileGender.text.toString()


            if (edittextName.isEmpty()) {
                showToastMessage("fields cannot be empty")
            } else {


                val newData = hashMapOf<String, Any>(
                    "name" to edittextName,
                    "surname" to edittextSurname,
                    "gender" to edittextGender
                )

                docRef.update(newData)
                    .addOnSuccessListener { Log.d(TAG, "Belge başarıyla güncellendi!") }
                    .addOnFailureListener { e -> Log.w(TAG, "Hata oluştu: ", e) }
            }

        }
    }


}





