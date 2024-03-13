package com.android.orderapp.ui.fragments.home

import android.util.Log
import android.widget.CheckBox
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.orderapp.R
import com.android.orderapp.data.adapter.MovieAdapter
import com.android.orderapp.data.model.FavInfo
import com.android.orderapp.data.model.MovieModel
import com.android.orderapp.data.repository.MoviesRepository
import com.android.orderapp.ui.base.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.getField
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val gson: Gson,
    private val moviesRepository: MoviesRepository,
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) : BaseViewModel() {

    private val _movieList = MutableLiveData<List<MovieModel>>()
    val movieList: LiveData<List<MovieModel>> = _movieList
    private val currentUser = firebaseAuth.currentUser?.uid

    init {
        getMovies()
    }

    fun getMovies() = viewModelScope.launch {
        moviesRepository.getLatestMovies(page = 1).onSuccess {
            _movieList.value = it.results
            Log.d("movies-test", "HomeViewModel.getMovies.Success : ${it.results}")
        }.onFailure {
            Log.d("movies-test", "HomeViewModel.getMovies.Error ${it.message}")
        }
    }

    fun updateFavoriteStatus(movie: MovieModel, isChecked: Boolean) {
        val list: ArrayList<String> = arrayListOf()
        var cbHave = isChecked
        val docRef = firebaseFirestore.collection("favorites").document("$currentUser")

        docRef.get().addOnSuccessListener { document ->
            if (document.exists()) {
                list.addAll((document.get("items") as? List<String>).orEmpty())
            } else {
                docRef.set(FavInfo(items = listOf()))
            }

            var movieString = gson.toJson(movie)
            if (list.contains(movieString)) {
                cbHave = true
                cbHave = isChecked
            } else {
                list.add(movieString)
                cbHave = false
                cbHave = isChecked
            }

            docRef.update("items", list)

        }.addOnFailureListener {
            Log.e("lif", "$it")
        }

    }

}