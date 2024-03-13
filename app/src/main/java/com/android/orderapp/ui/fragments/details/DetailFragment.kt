package com.android.orderapp.ui.fragments.details

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.android.orderapp.data.model.MovieModel
import com.android.orderapp.databinding.FragmentDetailBinding
import com.android.orderapp.di.imageBase
import com.android.orderapp.ui.base.BaseFragment
import com.android.orderapp.ui.base.FragmentInflate
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailFragment : BaseFragment<DetailViewModel, FragmentDetailBinding>() {

    override val viewModel: DetailViewModel by viewModels()
    override val viewBindingInflater: FragmentInflate<FragmentDetailBinding>
        get() = FragmentDetailBinding::inflate


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val movieId = arguments?.getString("id")
        movieId?.let { viewModel.getMovieDetailsById(it.toInt()) }


        binding.btnAddToCart.setOnClickListener {
            viewModel.getMovieDetailsById(movieId!!.toInt())
        }


        viewModel.movieDetails.observe(viewLifecycleOwner, Observer { movieDetails ->

            Glide.with(this)
                .load(imageBase + movieDetails.posterPath)
                .apply(RequestOptions().centerCrop())
                .into(binding.imgMovie)

            Glide.with(this)
                .load(imageBase + movieDetails.posterPath)
                .apply(RequestOptions().centerCrop())
                .into(binding.imgMovieBack)

            binding.tvMovieTitle.text = movieDetails.title
            binding.tvMovieDateRelease.text = movieDetails.releaseDate
            binding.tvMovieRating.text = movieDetails.voteAverage.toString()
            binding.tvMovieOverview.text = movieDetails.overview

        })


    }
}