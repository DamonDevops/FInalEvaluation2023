package be.technifutur.trendingmovies2023.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import be.technifutur.trendingmovies2023.R
import be.technifutur.trendingmovies2023.databinding.FragmentDetailsPageBinding
import be.technifutur.trendingmovies2023.network.model.MoviesResponse
import be.technifutur.trendingmovies2023.network.service.MoviesServiceImpl
import be.technifutur.trendingmovies2023.recycler.similar.SimilarViewAdapter
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class DetailsPage : Fragment() {
    private lateinit var binding :FragmentDetailsPageBinding
    val args :DetailsPageArgs by navArgs()
    private val imageBuilderUrl = "https://image.tmdb.org/t/p/w500/"
    private lateinit var model :MoviesResponse
    private val moviesService by lazy { MoviesServiceImpl() }
    private var job : Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movie = args.movie

        getSimilarListAsync()
        Picasso.get()
            .load(imageBuilderUrl + movie.backdropPath)
            .placeholder(R.drawable.placeholder)
            .into(binding.movieBanner)
        Picasso.get()
            .load(imageBuilderUrl + movie.posterPath)
            .placeholder(R.drawable.placeholder)
            .into(binding.moviePoster)
        binding.rating.text = String.format("%.1f", movie.voteAverage)
        binding.movieTitle.text = movie.originalTitle
        binding.synopsisContent.text = movie.synopsis

        binding.arrowButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupView(){
        val similarRecycler = binding.similarrecyclerView

        similarRecycler.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL , false)
        similarRecycler.adapter = SimilarViewAdapter(model)
    }

    private fun getSimilarListAsync(){
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = moviesService.similarMovies(args.movie.id)
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        model = MoviesResponse(mutableListOf())
                        response.body()?.moviesList?.forEach {
                            model.moviesList.add(it)
                        }
                        setupView()
                    }
                } catch (e: HttpException) {
                    print(e)
                } catch (e: Throwable) {
                    print(e)
                }
            }
        }
    }
}