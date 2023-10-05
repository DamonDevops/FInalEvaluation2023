package be.technifutur.trendingmovies2023.recycler.search

import androidx.recyclerview.widget.RecyclerView
import be.technifutur.trendingmovies2023.R
import be.technifutur.trendingmovies2023.databinding.MovieCellBinding
import be.technifutur.trendingmovies2023.network.model.MovieResponse
import com.squareup.picasso.Picasso

class SearchViewHolder(private var viewBinding :MovieCellBinding) : RecyclerView.ViewHolder(viewBinding.root) {

    private val imageBuilderUrl = "https://image.tmdb.org/t/p/w500/"
    fun bind(movie :MovieResponse, clickListener: SearchViewAdapter.OnSelectItem){
        Picasso.get()
            .load(imageBuilderUrl + movie.posterPath)
            .placeholder(R.drawable.placeholder)
            .into(viewBinding.moviePoster)
        viewBinding.movieTitle.text = movie.originalTitle
        viewBinding.movieDate.text = movie.releaseDate
        viewBinding.rating.text = String.format("%.1f", movie.voteAverage)

        itemView.setOnClickListener {
            clickListener.onItemClick(movie)
        }
    }
}