package com.android.orderapp.ui.fragments.profile

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.orderapp.R
import com.android.orderapp.databinding.FragmentEditProfileBinding
import com.android.orderapp.ui.base.BaseFragment
import com.android.orderapp.ui.base.FragmentInflate
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID


class EditProfileFragment : BaseFragment<EditProfileViewModel, FragmentEditProfileBinding>() {


    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var storage: FirebaseStorage
    private lateinit var selectedImg: Uri
    private lateinit var dialog: AlertDialog.Builder
    override val viewModel: EditProfileViewModel by viewModels()
    override val viewBindingInflater: FragmentInflate<FragmentEditProfileBinding>
        get() = FragmentEditProfileBinding::inflate

    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            selectedImg = uri
        } else {
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog = AlertDialog.Builder(requireContext())
            .setMessage("Updating profile..")
            .setCancelable(false)

        storage = FirebaseStorage.getInstance()
        val db = Firebase.firestore
        val uuid = firebaseAuth.currentUser?.uid
        val docRef = db.collection("users").document("$uuid")

        docRef.get().addOnSuccessListener { document ->
            if (document != null) {
                document.getString("surname")?.let { surname ->
                    binding.profileSurname.text = SpannableStringBuilder(surname)
                }
                document.getString("name")?.let { name ->
                    binding.profileName.text = SpannableStringBuilder(name)
                }
                document.getString("gender")?.let { gender ->
                    binding.profileGender.text = SpannableStringBuilder(gender)
                }
                document.getString("email")?.let { email ->
                    binding.profileEmail.text = SpannableStringBuilder(email)
                }

                val profileImageUrl = document.getString("profileImageUrl")
                if (!profileImageUrl.isNullOrEmpty()) {

                    Glide.with(requireContext())
                        .load(profileImageUrl)
                        .into(binding.profileImg)

                }

            } else {
                showToastMessage("belge boş.")
            }
        }.addOnFailureListener {
            Log.d(TAG, "Belge alınamadı: ", it)
        }

        binding.profileImg.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }


        binding.updateButton.setOnClickListener {
            val edittextName = binding.profileName.text.toString()
            val edittextSurname = binding.profileSurname.text.toString()
            val edittextGender = binding.profileGender.text.toString()

            if (edittextName.isEmpty() || edittextSurname.isEmpty() || edittextGender.isEmpty()) {
                showToastMessage("fields cannot be empty")
            } else {
                uploadImageToFirebaseStorage(
                    selectedImg,
                    docRef,
                    edittextName,
                    edittextSurname,
                    edittextGender
                )
                showToastMessage("Update Completed")
                findNavController().navigate(R.id.editProfileToProfile)
            }
        }


    }

    private fun uploadImageToFirebaseStorage(
        uri: Uri?,
        docRef: DocumentReference,
        edittextName: String,
        edittextSurname: String,
        edittextGender: String
    ) {
        if (uri == null) return

        val ref = FirebaseStorage.getInstance().getReference().child(UUID.randomUUID().toString())

        ref.putFile(uri)
            .addOnSuccessListener {

                ref.downloadUrl.addOnSuccessListener { uri ->
                    val newData = hashMapOf<String, Any>(
                        "name" to edittextName,
                        "surname" to edittextSurname,
                        "gender" to edittextGender,
                        "profileImageUrl" to uri.toString()
                    )

                    docRef.update(newData)
                        .addOnSuccessListener { Log.d(TAG, "Belge başarıyla güncellendi!") }
                        .addOnFailureListener { e -> Log.w(TAG, "Hata oluştu: ", e) }

                }

            }

    }
}





