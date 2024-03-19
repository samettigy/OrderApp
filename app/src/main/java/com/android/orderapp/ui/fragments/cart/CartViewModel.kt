package com.android.orderapp.ui.fragments.cart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.orderapp.data.model.LibrariesInfo
import com.android.orderapp.data.model.MovieModel
import com.android.orderapp.data.repository.MoviesRepository
import com.android.orderapp.ui.base.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val gson: Gson,
    private val firebaseFirestore: FirebaseFirestore,
    private val moviesRepository: MoviesRepository
) : BaseViewModel() {

    private val _screenState = MutableLiveData<BasketsScreenState>(BasketsScreenState.Loading)
    val screenState: LiveData<BasketsScreenState> = _screenState
    private val _movieDetails = MutableLiveData<MovieModel>()
    val movieDetails: LiveData<MovieModel> = _movieDetails

    val currentUser = firebaseAuth.currentUser?.uid
    val basketsDocRef = firebaseFirestore.collection("baskets").document("$currentUser")
    val librariesDocRef = firebaseFirestore.collection("libraries").document("$currentUser")

    init {
        getBaskets()
    }


    fun getBaskets() {
        _screenState.value = BasketsScreenState.Loading
        basketsDocRef.get().addOnSuccessListener { document ->
            (document.get("items") as? List<String>).takeIf { it.isNullOrEmpty().not() }
                ?.let { list ->
                    _screenState.value = BasketsScreenState.Content(list.map {
                        gson.fromJson(
                            it,
                            MovieModel::class.java
                        )
                    })
                    getMovieDetailsByIdAndUpdateLibraries(list)
                } ?: run {
                _screenState.value = BasketsScreenState.Error("Your basket list is empty")
            }
        }.addOnFailureListener {
            _screenState.value = BasketsScreenState.Error("Error while loading your basket")
        }
    }


    fun getMovieDetailsByIdAndUpdateLibraries(basketList: List<String>) = viewModelScope.launch {

        val list: ArrayList<String> = arrayListOf()

        librariesDocRef.get().addOnSuccessListener { document ->
            if (document.exists()) {
                list.addAll((document.get("items") as? List<String>).orEmpty())
            } else {
                librariesDocRef.set(LibrariesInfo(items = listOf()))
            }

            var movieString = gson.toJson(basketList)
            if (list.contains(movieString)) {
                Log.d("eklendi", "başarılı")
            } else {
                list.add(movieString)
                librariesDocRef.update("items", list)
            }
        }.addOnFailureListener {
            Log.e("addToCart", "Film sepete eklenirken hata oluştu: $it")
        }
    }
}


sealed class BasketsScreenState() {
    data object Loading : BasketsScreenState()
    class Error(val error: String) : BasketsScreenState()
    class Content(val movies: List<MovieModel>) : BasketsScreenState()
}