package com.android.orderapp.ui.fragments.cart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.orderapp.data.model.BasketInfo
import com.android.orderapp.data.model.FavInfo
import com.android.orderapp.data.model.MovieModel
import com.android.orderapp.ui.base.BaseViewModel
import com.android.orderapp.ui.fragments.favorite.FavoritesScreenState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val gson: Gson,
    private val firebaseFirestore: FirebaseFirestore
) : BaseViewModel() {

    private val _screenState = MutableLiveData<BasketsScreenState>(BasketsScreenState.Loading)
    val screenState: LiveData<BasketsScreenState> = _screenState
    val currentUser = firebaseAuth.currentUser?.uid
    val docRef = firebaseFirestore.collection("baskets").document("$currentUser")

    init {
        getBaskets()
    }


    fun getBaskets() {
        _screenState.value = BasketsScreenState.Loading
        docRef.get().addOnSuccessListener { document ->
            (document.get("items") as? List<String>).takeIf { it.isNullOrEmpty().not() }
                ?.let { list ->
                    _screenState.value = BasketsScreenState.Content(list.map {
                        gson.fromJson(
                            it,
                            MovieModel::class.java
                        )
                    })
                } ?: run {
                _screenState.value = BasketsScreenState.Error("Your basket list is empty")
            }
        }.addOnFailureListener {
            _screenState.value = BasketsScreenState.Error("Error while loading your basket")
        }
    }

}


sealed class BasketsScreenState() {
    data object Loading : BasketsScreenState()
    class Error(val error: String) : BasketsScreenState()
    class Content(val movies: List<MovieModel>) : BasketsScreenState()
}