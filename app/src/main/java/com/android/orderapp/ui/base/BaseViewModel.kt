package com.android.orderapp.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow

abstract class BaseViewModel : ViewModel() {
    var loadingState: MutableSharedFlow<LoadingState> = MutableSharedFlow<LoadingState>()

}

enum class LoadingState {
    SHOW,
    HIDE
}