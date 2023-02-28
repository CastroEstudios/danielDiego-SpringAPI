package com.example.proyectospring.interfaces

import com.example.proyectospring.Show
import com.example.proyectospring.Usuario
import retrofit2.Call
import retrofit2.http.*

interface UsuariosAPI {

    @GET("usuarios/{correo}")
    @Headers("Accept: application/json")
    fun buscarUsuario(@Path("correo") correo: String) : Call<Usuario>

    @POST("usuarios")
    fun createUsuario(@Body usuario: Usuario) : Call<Usuario>

    @DELETE("usuarios/{correo}")
    fun deleteUsuario(@Path("correo") correo: String?) : Call<Void>

    @PUT("usuarios/{correo}")
    fun updateUsuario(@Path("correo") correo: String?, @Body usuario: Usuario): Call<Usuario>

}