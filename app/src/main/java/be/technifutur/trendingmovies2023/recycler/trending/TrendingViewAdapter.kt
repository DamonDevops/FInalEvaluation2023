package be.technifutur.trendingmovies2023.recycler.trending

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import be.technifutur.trendingmovies2023.databinding.TrendingCellBinding
import be.technifutur.trendingmovies2023.network.model.MovieResponse
import be.technifutur.trendingmovies2023.network.model.MoviesResponse

class TrendingViewAdapter(private val movies :MoviesResponse, private val clickListener: OnSelectTrending) : RecyclerView.Adapter<TrendingViewHolder>() {
    private lateinit var binding : TrendingCellBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingViewHolder {
        binding = TrendingCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrendingViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return movies.moviesList.size
    }

    override fun onBindViewHolder(holder: TrendingViewHolder, position: Int) {
        holder.bind(movies.moviesList[position], clickListener)
    }
    interface OnSelectTrending{
        fun onTrendClick(movie :MovieResponse)
    }
}