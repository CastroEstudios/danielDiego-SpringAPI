package com.example.proyectospring

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.proyectospring.interfaces.ShowsAPI
import com.example.proyectospring.interfaces.UsuariosAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LogIn : AppCompatActivity() {
    lateinit var botonLogin: Button
    lateinit var botonRegistrar: Button
    lateinit var botonCreditos: Button
    lateinit var fieldCorreo: EditText
    lateinit var fieldContrasegna: EditText
    lateinit var contexto: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        botonLogin = findViewById(R.id.botonLogin)
        botonRegistrar = findViewById(R.id.botonRegistrar)
        botonCreditos = findViewById(R.id.botonCreditos)
        fieldCorreo = findViewById(R.id.correoField)
        fieldContrasegna = findViewById(R.id.contrasegnaField)
        contexto = this

        botonLogin.setOnClickListener{obtenerShows()}
        botonRegistrar.setOnClickListener{registrar()}
        botonCreditos.setOnClickListener {
            val miIntentC = Intent(contexto,Creditos::class.java)
            startActivity(miIntentC)
        }

    }

    fun obtenerShows(){

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var interfaz = retrofit.create(UsuariosAPI::class.java)

        interfaz.buscarUsuario(correo = fieldCorreo.text.toString()).enqueue(object : Callback<Usuario> {
            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                val usuarioIntroducido = Usuario(correo = fieldCorreo.text.toString(), contrasegna = fieldContrasegna.text.toString())
                if (response.code() == 200){
                    println("El usuario existe")
                    val r = response.body()
                    if (r!=null){
                        val usuarioBD: Usuario = r
                        if(usuarioBD.contrasegna.equals(usuarioIntroducido.contrasegna)) {
                            val miIntent = Intent(contexto,Pantalla::class.java)
                            startActivity(miIntent)
                        }else {
                            Toast.makeText(contexto,"Datos incorrectos",Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    println("El usuario no existe")
                }
            }
            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                Toast.makeText(contexto,"Error de conexión",Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun registrar() {

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var interfaz = retrofit.create(UsuariosAPI::class.java)

        interfaz.buscarUsuario(correo = fieldCorreo.text.toString()).enqueue(object : Callback<Usuario> {
            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                if (response.code() == 200){
                    Toast.makeText(contexto,"Ya existe un usuario con ese correo.",Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                GlobalScope.launch(Dispatchers.IO) {
                    try {
                        val usuarioIntroducido = Usuario(correo = fieldCorreo.text.toString(), contrasegna = fieldContrasegna.text.toString())
                        val response = retrofit.create(UsuariosAPI::class.java).createUsuario(usuarioIntroducido).execute()
                        if (response.isSuccessful) {
                            GlobalScope.launch(Dispatchers.Main) {
                                Toast.makeText(contexto,"El usuario se creó con éxito",Toast.LENGTH_SHORT).show()
                            }
                            println(response.code())
                        } else {
                            println("ERROR " + response.code())
                            GlobalScope.launch(Dispatchers.Main) {
                                Toast.makeText(contexto,"Error al crear el usuario",Toast.LENGTH_SHORT).show()
                            }
                        }
                    } catch (e: Exception) {
                        GlobalScope.launch(Dispatchers.Main) {
                            Toast.makeText(contexto,"Error de respuesta",Toast.LENGTH_SHORT).show()
                        }
                        println("ERROR " + e.message)
                    }
                }
            }

        })
    }

}