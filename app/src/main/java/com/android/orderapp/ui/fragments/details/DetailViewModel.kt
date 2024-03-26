package com.android.orderapp.ui.fragments.details

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.orderapp.data.model.BasketInfo
import com.android.orderapp.data.model.FavInfo
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
class DetailViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val firebaseAuth: FirebaseAuth,
    private val gson: Gson,
    private val firebaseFirestore: FirebaseFirestore
) : BaseViewModel() {




    private val _movieDetails = MutableLiveData<MovieModel>()
    val movieDetails: LiveData<MovieModel> = _movieDetails

    private val _favorites = MutableLiveData<List<MovieModel>>()
    val favorites: LiveData<List<MovieModel>> = _favorites

    val currentUser = firebaseAuth.currentUser?.uid
    val basketsDocRef = firebaseFirestore.collection("baskets").document("$currentUser")
    val favoritesDocRef = firebaseFirestore.collection("favorites").document("$currentUser")


    init {
        updateFavoritesState()
    }


    fun getMovieDetailsByIdAndUpdateBaskets(movieId: Int) = viewModelScope.launch {
        moviesRepository.getMovieDetailsById(movieId).onSuccess { movie ->
            _movieDetails.value = movie

            val list: ArrayList<String> = arrayListOf()

            basketsDocRef.get().addOnSuccessListener { document ->
                if (document.exists()) {
                    list.addAll((document.get("items") as? List<String>).orEmpty())
                } else {
                    basketsDocRef.set(BasketInfo(items = listOf()))
                }

                var movieString = gson.toJson(movie)
                if (list.contains(movieString)) {
                    Log.d("eklendi", "başarılı")
                } else {
                    list.add(movieString)
                    basketsDocRef.update("items", list)
                }
            }.addOnFailureListener {
                Log.e("addToCart", "Film sepete eklenirken hata oluştu: $it")
            }
        }.onFailure {
            Log.d("movies-test", "HomeViewModel.getMovies.Error ${it.message}")
        }
    }




    fun updateFavoritesState(): Boolean {
        val list: ArrayList<String> = arrayListOf()
        var found = false

        favoritesDocRef.get().addOnSuccessListener { document ->
            if (document.exists()) {
                list.addAll((document.get("items") as? List<String>).orEmpty())

                list.forEach { movie ->
                    if (list.any { it == movie }) {
                        found = true
                        return@forEach
                    }
                }
            }
        }.addOnFailureListener { exception ->
            Log.d(TAG, "Error getting documents: ", exception)
        }
        return found
    }






    /*
    fun updateBasketStatus(movie: MovieModel) {
        val list: ArrayList<String> = arrayListOf()

        docRef.get().addOnSuccessListener { document ->
            if (document.exists()) {
                list.addAll((document.get("items") as? List<String>).orEmpty())
            } else {
                docRef.set(BasketInfo(items = listOf()))
            }

            var movieString = gson.toJson(movie)
            if (list.contains(movieString)) {
                Log.d("eklendi", "başarılı")
            } else {
                list.add(movieString)
            }

            docRef.update("items", list)

        }.addOnFailureListener {
            Log.e("lif", "$it")
        }

    }

     */


}

