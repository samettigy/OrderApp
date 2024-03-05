package com.android.orderapp.ui.fragments.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.orderapp.data.model.MovieModel
import com.android.orderapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
) : BaseViewModel(){

    private val _selectedItem = MutableLiveData<MovieModel>()
    val selectedItem : LiveData<MovieModel> = _selectedItem

    fun setSelectedItem(movie : MovieModel) {
        _selectedItem.value = movie
    }

}