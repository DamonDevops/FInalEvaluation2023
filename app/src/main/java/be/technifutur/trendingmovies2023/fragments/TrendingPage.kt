package be.technifutur.trendingmovies2023.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import be.technifutur.trendingmovies2023.databinding.FragmentTrendingPageBinding
import be.technifutur.trendingmovies2023.network.model.MovieResponse
import be.technifutur.trendingmovies2023.network.model.MoviesResponse
import be.technifutur.trendingmovies2023.network.service.MoviesServiceImpl
import be.technifutur.trendingmovies2023.recycler.trending.TrendingViewAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class TrendingPage : Fragment(), TrendingViewAdapter.OnSelectTrending {
    private lateinit var binding :FragmentTrendingPageBinding
    private lateinit var model : MoviesResponse
    private val moviesService by lazy { MoviesServiceImpl() }
    private var job : Job? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrendingPageBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showNothingPage(false)
        getTrendingAsync()
    }
    private fun setupView(){
        val trendingRecycler = binding.trendingRecyclerView

        trendingRecycler.layoutManager = GridLayoutManager(requireContext(), 3)
        trendingRecycler.adapter = TrendingViewAdapter(model, this)
    }
    private fun showNothingPage(check :Boolean){
        binding.nothingLayout.isVisible = check
    }
    private fun getTrendingAsync(){
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = moviesService.trendingMovies()
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        model = MoviesResponse(mutableListOf())
                        response.body()?.moviesList?.forEach {
                            model.moviesList.add(it)
                        }
                        if(model.moviesList.isNotEmpty()){
                            setupView()
                            showNothingPage(false)
                        }
                        else{
                            showNothingPage(true)
                        }
                    }else{
                        showNothingPage(true)
                    }
                } catch (e: HttpException) {
                    print(e)
                } catch (e: Throwable) {
                    print(e)
                }
            }
        }
    }
    override fun onTrendClick(movie :MovieResponse) {
        val destination = TrendingPageDirections.actionTrendingPageToDetailsPage(movie)
        findNavController().navigate(destination)
    }
}