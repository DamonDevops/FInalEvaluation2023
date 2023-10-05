package be.technifutur.trendingmovies2023.recycler.similar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import be.technifutur.trendingmovies2023.databinding.SimilarCellBinding
import be.technifutur.trendingmovies2023.network.model.MoviesResponse

class SimilarViewAdapter(private val movies : MoviesResponse) : RecyclerView.Adapter<SimilarViewHolder>() {
    private lateinit var binding :SimilarCellBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarViewHolder {
        binding = SimilarCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SimilarViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return movies.moviesList.size
    }

    override fun onBindViewHolder(holder: SimilarViewHolder, position: Int) {
        holder.bind(movies.moviesList[position])
    }
}