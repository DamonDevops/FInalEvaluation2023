package be.technifutur.trendingmovies2023.network.model

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("results")
    var moviesList :MutableList<MovieResponse>
)