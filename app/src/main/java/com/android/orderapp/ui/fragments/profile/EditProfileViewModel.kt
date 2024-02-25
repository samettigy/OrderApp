package com.android.orderapp.ui.fragments.profile

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.orderapp.data.entity.UserInfo
import com.android.orderapp.ui.base.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseStorage: FirebaseStorage,
    private val fireStore: FirebaseFirestore
) : BaseViewModel() {

    private val _screenState =
        MutableLiveData<EditProfileScreenState>(EditProfileScreenState.Loading)
    val screenState: LiveData<EditProfileScreenState> = _screenState
    private val docRef = fireStore.collection("users").document("${firebaseAuth.uid}")
    var selectedImg: Uri? = null

    init {
        getUser()
    }

    private fun getUser() {
        _screenState.value = EditProfileScreenState.Loading
        docRef.get().addOnSuccessListener { document ->
            if (document != null) {
                _screenState.value = EditProfileScreenState.Content(
                    UserInfo(
                        profileImageUrl = document.getString("profileImageUrl").orEmpty(),
                        email = document.getString("email").orEmpty(),
                        name = document.getString("name").orEmpty(),
                        surname = document.getString("surname").orEmpty(),
                        gender = document.getString("gender").orEmpty()
                    )
                )
            } else {
                _screenState.value = EditProfileScreenState.Error("User bulunamadi")
            }
        }.addOnFailureListener() {
            _screenState.value = EditProfileScreenState.Error("${it.message}")
        }
    }

    fun updateProfile(
        edittextName: String, edittextSurname: String, edittextGender: String,
        onSuccess: () -> Unit, onError: (message: String) -> Unit
    ) {
        if (edittextName.isEmpty() || edittextSurname.isEmpty() || edittextGender.isEmpty()) {
            onError.invoke("fields cannot be empty")
        } else {
            /*       uploadImageToFirebaseStorage(
                       selectedImg,
                       docRef,
                       edittextName,
                       edittextSurname,
                       edittextGender,onSuccess, onError
                   )*/
        }
    }

    private fun uploadImageToFirebaseStorage(
        edittextName: String,
        edittextSurname: String,
        edittextGender: String, onSuccess: () -> Unit, onError: (message: String) -> Unit
    ) {
        if (selectedImg == null) {
            //saveUser()
            return
        }

        val ref = FirebaseStorage.getInstance().getReference()
            .child(UUID.randomUUID().toString()) // todo user id kullanilacak

        ref.putFile(selectedImg!!)
            .addOnSuccessListener {
                ref.downloadUrl.addOnSuccessListener { uri ->
                    // saveUser()
                }

            }
    }

    private fun saveUser(
        onSuccess: () -> Unit,
        onError: (message: String) -> Unit
    ) { // todo kullanici bilgileri ve fotograf download url paslanaca
        /*
                val newData = hashMapOf<String, Any>(
                    "name" to edittextName,
                    "surname" to edittextSurname,
                    "gender" to edittextGender,
                    "profileImageUrl" to uri.toString()
                )

                docRef.update(newData)
                    .addOnSuccessListener {
                        Log.d(
                            ContentValues.TAG,
                            "Belge başarıyla güncellendi!"
                        )
                    }
                    .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Hata oluştu: ", e) }
        */

    }
}

sealed class EditProfileScreenState() {
    data object Loading : EditProfileScreenState()
    class Error(val error: String) : EditProfileScreenState()
    class Content(val user: UserInfo) : EditProfileScreenState()
}

