package be.technifutur.trendingmovies2023.network.service

import be.technifutur.trendingmovies2023.network.model.MoviesResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

const val API_URL = "https://api.themoviedb.org/3/"
const val API_KEY = "9d9a7e51d2dd50e8d80b528c5cbef4ad"
interface MoviesService {
    @Headers("Content-type: application/json")
    @GET("search/movie")
    suspend fun searchedMovies(
        @Query("query") query :String,
        @Query("api_key") key :String,
    ) : Response<MoviesResponse>
}

class MoviesServiceImpl{
    fun getRetrofit() : Retrofit {
        val okBuilder = OkHttpClient().newBuilder().apply {
            connectTimeout(60, TimeUnit.SECONDS)
            callTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
        }
        return Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okBuilder.build())
            .build()
    }

    suspend fun searchedMovies(query :String) : Response<MoviesResponse> = getRetrofit().create(MoviesService::class.java).searchedMovies(query, API_KEY)
}