package com.example.proyectospring.interfaces

import com.example.proyectospring.Show
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ShowsAPI {

    @GET("shows")
    @Headers("Accept: application/json")
    fun obetenerShows() : Call<List<Show>>

    @GET("shows/{titulo}")
    @Headers("Accept: application/json")
    fun buscarTipo(@Path("titulo") tipo: String) : Call<Show>

    @POST("shows")
    fun createShow(@Body show: Show) : Call<Show>

    @DELETE("shows/{titulo}")
    fun deleteShow(@Path("titulo") titulo : String?) : Call<Void>

    @PUT("shows/{titulo}")
    fun updateShow(@Path("titulo") titulo: String?, @Body show: Show): Call<Show>

}