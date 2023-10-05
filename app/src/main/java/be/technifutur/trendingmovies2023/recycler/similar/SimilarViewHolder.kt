package be.technifutur.trendingmovies2023.recycler.similar

import androidx.recyclerview.widget.RecyclerView
import be.technifutur.trendingmovies2023.R
import be.technifutur.trendingmovies2023.databinding.SimilarCellBinding
import be.technifutur.trendingmovies2023.network.model.MovieResponse
import com.squareup.picasso.Picasso

class SimilarViewHolder(private var viewBinding : SimilarCellBinding) : RecyclerView.ViewHolder(viewBinding.root) {
    private val imageBuilderUrl = "https://image.tmdb.org/t/p/w500/"
    fun bind(movie : MovieResponse){
        Picasso.get()
            .load(imageBuilderUrl + movie.posterPath)
            .placeholder(R.drawable.placeholder)
            .into(viewBinding.poster)
        viewBinding.title.text = movie.originalTitle
    }
}