package com.android.orderapp.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.orderapp.R
import com.android.orderapp.data.model.MovieModel
import com.bumptech.glide.Glide


class MovieAdapter(
    var moviesList: ArrayList<MovieModel>,
    private val onItemClick: (MovieModel) -> Unit
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {


    private val IMAGE_BASE: String = "https://image.tmdb.org/t/p/w500/"

    class MovieViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        var moviePoster: ImageView = view.findViewById(R.id.moviePoster)
        val movieTitle: TextView = view.findViewById(R.id.movieTitle)
        val movieReleaseDate: TextView = view.findViewById(R.id.movieReleaseDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentItem = moviesList[position]

        holder.itemView.setOnClickListener {
            onItemClick(currentItem)
        }

        Glide.with(holder.itemView)
            .load(IMAGE_BASE + currentItem.posterPath)
            .into(holder.moviePoster)

        holder.movieTitle.text = currentItem.title
        holder.movieReleaseDate.text = currentItem.releaseDate
    }

}