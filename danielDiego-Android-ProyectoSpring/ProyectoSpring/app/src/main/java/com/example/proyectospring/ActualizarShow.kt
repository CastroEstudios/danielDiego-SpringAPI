package com.example.proyectospring


import android.content.Intent
import android.os.Build
import com.example.proyectospring.interfaces.ShowsAPI
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class ActualizarShow : AppCompatActivity() {

    lateinit var id :EditText
    lateinit var titulo: EditText
    lateinit var director: EditText
    lateinit var reparto: EditText
    lateinit var tags: EditText
    lateinit var fecha: EditText
    lateinit var grupo: RadioGroup
    lateinit var movie: RadioButton
    lateinit var show: RadioButton
    lateinit var insertar: Button

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertar_show)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var fondo :ConstraintLayout = findViewById(R.id.constraintLayout)

        fondo.setBackgroundColor(getColor(R.color.fondo))

        id = findViewById(R.id.insertaId)
        titulo = findViewById(R.id.InsertaTitulo)
        director = findViewById(R.id.InsertaDirector)
        reparto = findViewById(R.id.insertaActores)
        tags = findViewById(R.id.insertaTags)
        fecha = findViewById(R.id.insertaFecha)
        grupo = findViewById(R.id.grupoRadio)
        movie = findViewById(R.id.movie)
        show = findViewById(R.id.show)
        insertar = findViewById(R.id.botonInsertar)


        insertar.text="Actualizar"

        var pais : String?=""
        var lanzamiento: Int? = -1
        var tipo: String?= ""
        var rating: String? = ""
        var duracion: String? = ""
        var descripcion: String? = ""

        val intent = intent

        val devuelto = intent.getStringExtra("titulo")

        val repo = devuelto?.let { retrofit.create(ShowsAPI::class.java).buscarTipo(it).enqueue(object : Callback<Show>{
            override fun onResponse(call: Call<Show>, response: Response<Show>) {
                if (response.code() == 200){
                    println("HEY")
                    val r = response.body()
                    if (r!=null){
                            id.setText(r.showId)
                            titulo.setText(r.titulo)
                            director.setText(r.director)
                            reparto.setText(r.actores)
                            tags.setText(r.tags)
                            fecha.setText(r.fecha)
                            tipo = r.tipo
                            pais = r.pais
                            lanzamiento = r.lanzamiento
                            rating = r.rating
                            duracion = r.duracion
                            descripcion =r.descripcion
                        if (tipo == "Movie"){
                            movie.isChecked = true
                        }else{
                            show.isChecked = true
                        }
                    }
                }else{
                    println("HAY ALGO MAL")
                }
            }

            override fun onFailure(call: Call<Show>, t: Throwable) {
                println(t.message)
            }


        } ) }

        grupo.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener{group, checkedId->
            if (movie.isChecked){
                tipo = "Movie"
            }else if(show.isChecked){
                tipo = "TV Show"
            }
        })

        insertar.setOnClickListener{

            var showid : String = id.text.toString()
            var titu : String = titulo.text.toString()
            var dire : String = director.text.toString()
            var repa : String = reparto.text.toString()
            var fe : String = fecha.text.toString()
            var ta : String = tags.text.toString()


            if (showid == "" || titu == "" || dire == "" || repa == "" || fe == "" || ta == "" || tipo == ""){

                Toast.makeText(applicationContext,"No deje campos vacíos por favor",Toast.LENGTH_LONG).show()

            }else{

                val show = Show(
                    showId = showid,
                    tipo = tipo,
                    titulo = titu,
                    director = dire,
                    actores = repa,
                    pais = pais,
                    fecha = fe,
                    lanzamiento = lanzamiento,
                    rating = rating,
                    duracion = duracion,
                    tags = ta,
                    descripcion = descripcion
                )

                val response = retrofit.create(ShowsAPI::class.java).updateShow(devuelto,show)
                response.enqueue(object : Callback<Show> {
                        override fun onResponse(call: Call<Show>, response: Response<Show>) {
                            if (response.isSuccessful) {
                                println("El show se actualizó con éxito")
                                println(response.code())
                            } else {
                                println("ERROR "+response.code())
                            }
                        }

                        override fun onFailure(call: Call<Show>, t: Throwable) {
                            // handle failure
                        }
                    })

                finish()

            }

        }

    }
}