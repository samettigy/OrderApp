package com.android.orderapp.ui.fragments.login

import androidx.lifecycle.viewModelScope
import com.android.orderapp.ui.base.BaseViewModel
import com.android.orderapp.ui.base.LoadingState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : BaseViewModel() {

    fun forgotPassword(
        email: String,
        loginSuccess: () -> Unit,
        loginError: (String) -> Unit
    ) =
        viewModelScope.launch {
            loadingState.emit(LoadingState.SHOW)
            firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener {
                viewModelScope.launch {
                    loadingState.emit(LoadingState.HIDE)
                }
                if (it.isSuccessful){
                    loginSuccess.invoke()
                } else {
                    loginError.invoke(it.exception?.message.orEmpty())
                }
            }
        }

}