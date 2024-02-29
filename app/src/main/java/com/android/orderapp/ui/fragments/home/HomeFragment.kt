package com.android.orderapp.ui.fragments.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.orderapp.data.adapter.MovieAdapter
import com.android.orderapp.data.model.MovieModel
import com.android.orderapp.databinding.FragmentHomeBinding
import com.android.orderapp.ui.base.BaseFragment
import com.android.orderapp.ui.base.FragmentInflate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {
    override val viewModel: HomeViewModel by viewModels()
    override val viewBindingInflater: FragmentInflate<FragmentHomeBinding>
        get() = FragmentHomeBinding::inflate


    private lateinit var adapter: MovieAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val recyclerView = binding.rvMovieList
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = MovieAdapter(ArrayList())
        recyclerView.adapter = adapter

        viewModel.movieList.observe(viewLifecycleOwner, Observer { movies ->
            adapter.moviesList = movies as ArrayList<MovieModel>
            adapter.notifyDataSetChanged()
        })

        viewModel.getMovies()


        view.isFocusableInTouchMode = true
        view.requestFocus()
        view.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
                val alertDialogBuilder = AlertDialog.Builder(requireContext())
                alertDialogBuilder.setMessage("Do you want to exit the application?")
                alertDialogBuilder.setPositiveButton("Evet") { _, _ ->
                    requireActivity().finish()
                }
                alertDialogBuilder.setNegativeButton("Hayır") { _, _ ->

                }
                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()
                true
            } else {
                false
            }
        }


    }


}