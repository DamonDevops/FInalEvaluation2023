package be.technifutur.trendingmovies2023.recycler.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import be.technifutur.trendingmovies2023.databinding.MovieCellBinding
import be.technifutur.trendingmovies2023.network.model.MovieResponse
import be.technifutur.trendingmovies2023.network.model.MoviesResponse

class SearchViewAdapter(private val movies :MoviesResponse, private val clickListener: OnSelectItem) : RecyclerView.Adapter<SearchViewHolder>() {
    private lateinit var binding :MovieCellBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        binding = MovieCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return movies.moviesList.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(movies.moviesList[position], clickListener)
    }
    interface OnSelectItem{
        fun onItemClick(movie :MovieResponse)
    }
}