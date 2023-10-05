package be.technifutur.trendingmovies2023.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import be.technifutur.trendingmovies2023.databinding.FragmentSearchPageBinding
import be.technifutur.trendingmovies2023.network.model.MovieResponse
import be.technifutur.trendingmovies2023.network.model.MoviesResponse
import be.technifutur.trendingmovies2023.network.service.MoviesServiceImpl
import be.technifutur.trendingmovies2023.recycler.search.SearchViewAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class SearchPage : Fragment(), SearchViewAdapter.OnSelectItem {
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
        binding.searchField.isVisible = false
        showNothingPage(false)
        getMoviesListAsync()

        binding.searchButton.setOnClickListener {
            binding.searchField.isVisible = true
            binding.searchTitle.isVisible = false

            binding.searchField.doAfterTextChanged {
                searchedText = it.toString()
                getMoviesListAsync()
            }
        }
    }

    private fun setupView(){
        binding.searchRecyclerView.isVisible = true
        val searchRecycler = binding.searchRecyclerView

        searchRecycler.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL , false)
        searchRecycler.adapter = SearchViewAdapter(model, this)
    }
    private fun showNothingPage(check :Boolean){
        binding.nothingLayout.isVisible = check
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
                        if(model.moviesList.isNotEmpty()){
                            setupView()
                            binding.searchRecyclerView.adapter?.notifyDataSetChanged()
                            showNothingPage(false)
                        }
                        else{
                            binding.searchRecyclerView.isVisible = false
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
    override fun onItemClick(movie :MovieResponse) {
        val destination = SearchPageDirections.actionSearchPageToDetailsPage(movie)
        findNavController().navigate(destination)
    }
}