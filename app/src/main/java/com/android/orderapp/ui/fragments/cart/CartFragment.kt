package com.android.orderapp.ui.fragments.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.orderapp.R
import com.android.orderapp.data.adapter.MovieAdapter
import com.android.orderapp.data.adapter.MovieAdapterInteraction
import com.android.orderapp.data.model.MovieModel
import com.android.orderapp.databinding.FragmentCartBinding
import com.android.orderapp.ui.base.BaseFragment
import com.android.orderapp.ui.base.FragmentInflate
import com.android.orderapp.ui.fragments.favorite.FavoritesScreenState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : BaseFragment<CartViewModel, FragmentCartBinding>(), MovieAdapterInteraction {

    override val viewModel: CartViewModel by viewModels()
    override val viewBindingInflater: FragmentInflate<FragmentCartBinding>
        get() = FragmentCartBinding::inflate

    private var adapter: MovieAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.screenState.observe(viewLifecycleOwner, Observer { screenState ->
            when (screenState) {
                is BasketsScreenState.Content -> {
                    binding.contentView.visibility = View.VISIBLE
                    binding.loadingView.visibility = View.GONE
                    adapter = MovieAdapter(screenState.movies, this)
                    val recyclerView = binding.rvCartList
                    recyclerView.layoutManager = LinearLayoutManager(requireContext())
                    recyclerView.adapter = adapter
                }

                is BasketsScreenState.Error -> {
                    showToastMessage(screenState.error)
                    binding.contentView.visibility = View.GONE
                    binding.loadingView.visibility = View.GONE
                }

                BasketsScreenState.Loading -> {
                    binding.contentView.visibility = View.GONE
                    binding.loadingView.visibility = View.VISIBLE
                }
            }
        })


    }

    override fun onFavoriteClick(movie: MovieModel, isChecked: Boolean) {

    }

    override fun onItemClick(itemId: String) {
        val bundle = bundleOf("id" to itemId)
        findNavController().navigate(R.id.detailFragment, bundle)
    }

}

