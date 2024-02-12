package com.android.orderapp.ui.fragments.login

import androidx.lifecycle.viewModelScope
import com.android.orderapp.ui.base.BaseViewModel
import com.android.orderapp.ui.base.LoadingState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : BaseViewModel() {


    fun signUp(
        email: String,
        password: String,
        confirmPassword: String,
        loginSuccess: () -> Unit,
        loginError: (String) -> Unit
    ) =
        viewModelScope.launch {
            loadingState.emit(LoadingState.SHOW)
            firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                viewModelScope.launch {
                    loadingState.emit(LoadingState.HIDE)
                }
                if (it.isSuccessful) {
                    loginSuccess.invoke()
                } else {
                    loginError.invoke(it.exception?.message.orEmpty())
                }
            }
        }

}