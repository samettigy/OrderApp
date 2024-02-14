package com.android.orderapp.ui.fragments.login

import android.widget.Toast
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.android.orderapp.R
import com.android.orderapp.data.entity.UserInfo
import com.android.orderapp.ui.base.BaseViewModel
import com.android.orderapp.ui.base.LoadingState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : BaseViewModel() {

    fun singIn(
        email: String,
        password: String,
        loginSuccess: () -> Unit,
        loginError: (String) -> Unit
    ) =
        viewModelScope.launch {
            loadingState.emit(LoadingState.SHOW)
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                viewModelScope.launch {
                    loadingState.emit(LoadingState.HIDE)
                }
                if (it.isSuccessful) {
                    loginSuccess.invoke()
                    val user = it.result.user
                    val userId = user!!.uid
                    val userEmail = user.email

                    val db = Firebase.firestore

                    val userDocument = db.collection("test2").document(userId)

                    val userInfo = UserInfo (
                        email = userEmail
                    )

                    userDocument.set(userInfo)

                } else {
                    loginError.invoke(it.exception?.message.orEmpty())
                }
            }
        }
}