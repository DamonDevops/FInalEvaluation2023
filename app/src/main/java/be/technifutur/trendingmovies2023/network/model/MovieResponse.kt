package be.technifutur.trendingmovies2023.network.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieResponse(
    @SerializedName("id")
    val id :Int,
    @SerializedName("original_title")
    val originalTitle :String?,
    @SerializedName("backdrop_path")
    val backdropPath :String?,
    @SerializedName("poster_path")
    val posterPath :String?,
    @SerializedName("vote_average")
    val voteAverage :Float?,
    @SerializedName("overview")
    val synopsis :String?,
    @SerializedName("release_date")
    val releaseDate :String?
) :Parcelable