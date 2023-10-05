package be.technifutur.trendingmovies2023.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import be.technifutur.trendingmovies2023.R
import be.technifutur.trendingmovies2023.databinding.FragmentSearchPageBinding
import be.technifutur.trendingmovies2023.network.model.MovieResponse
import be.technifutur.trendingmovies2023.network.model.MoviesResponse
import be.technifutur.trendingmovies2023.network.service.MoviesService
import be.technifutur.trendingmovies2023.network.service.MoviesServiceImpl
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class SearchPage : Fragment() {
    private lateinit var binding: FragmentSearchPageBinding
    private lateinit var model :MoviesResponse
    private val moviesService by lazy { MoviesServiceImpl() }
    private var job :Job? = null
    private var searchedText :String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun setupView(list :MutableList<MovieResponse>){

    }
    private fun getMoviesListAsync(){
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = moviesService.searchedMovies(searchedText)
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        model = MoviesResponse(mutableListOf())
                        response.body()?.moviesList?.forEach {
                            model.moviesList.add(it)
                        }
                        setupView(model.moviesList)
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