package com.android.orderapp.ui.fragments.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.orderapp.data.model.MovieModel
import com.android.orderapp.data.repository.MoviesRepository
import com.android.orderapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : BaseViewModel() {

    private val _movieDetails = MutableLiveData<MovieModel>()
    val movieDetails: LiveData<MovieModel> = _movieDetails


    fun getMovieDetailsById(movieId: Int) = viewModelScope.launch {
        moviesRepository.getMovieDetailsById(movieId).onSuccess {
            _movieDetails.value = it
        }.onFailure {
            Log.d("movies-test", "HomeViewModel.getMovies.Error ${it.message}")
        }
    }

}