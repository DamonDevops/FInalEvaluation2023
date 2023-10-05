package be.technifutur.trendingmovies2023.recycler.trending

import androidx.recyclerview.widget.RecyclerView
import be.technifutur.trendingmovies2023.R
import be.technifutur.trendingmovies2023.databinding.TrendingCellBinding
import be.technifutur.trendingmovies2023.network.model.MovieResponse
import com.squareup.picasso.Picasso

class TrendingViewHolder(private var viewBinding : TrendingCellBinding) : RecyclerView.ViewHolder(viewBinding.root) {
    private val imageBuilderUrl = "https://image.tmdb.org/t/p/w500/"
    fun bind(movie : MovieResponse, clickListener: TrendingViewAdapter.OnSelectTrending){
        Picasso.get()
            .load(imageBuilderUrl + movie.posterPath)
            .placeholder(R.drawable.placeholder)
            .into(viewBinding.posterImage)
        viewBinding.rating.text = String.format("%.1f", movie.voteAverage)

        itemView.setOnClickListener {
            clickListener.onTrendClick(movie)
        }
    }
}