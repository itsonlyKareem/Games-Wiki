package network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ApiService {
    @GET("/games")
    fun fetchGames(
       @Query("key") key: String,
       @Query("page") page: Int,



        @Header("X-RapidAPI-Host")
        host: String = "rawg-video-games-database.p.rapidapi.com",

        @Header("X-RapidAPI-Key")
        rapidKey: String = "eb9893c9e1msh3a6ecd062c4fad6p101e33jsn7f5a1980d8e3"

    ) : Call<ResponseBody>
}