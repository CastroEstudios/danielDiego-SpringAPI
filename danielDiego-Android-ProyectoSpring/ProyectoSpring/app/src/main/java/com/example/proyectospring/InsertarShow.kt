package com.example.proyectospring


import com.example.proyectospring.interfaces.ShowsAPI
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class InsertarShow : AppCompatActivity() {

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

        var tipo: String= ""



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
                println(showid)
                println(titu)
                println(dire)
                println(repa)
                println(fe)
                println(ta)

            }else{

                val show = Show(
                    showId = showid,
                    tipo = tipo,
                    titulo = titu,
                    director = dire,
                    actores = repa,
                    pais = "",
                    fecha = fe,
                    lanzamiento = 0,
                    rating = "",
                    duracion = "",
                    tags = ta,
                    descripcion = ""
                )

                GlobalScope.launch(Dispatchers.IO) {
                    try {
                        val response = retrofit.create(ShowsAPI::class.java).createShow(show).execute()
                        if (response.isSuccessful) {
                            // Show created successfully
                            println("El show se creó con éxito")
                            println(response.code())
                        } else {
                            // Show creation failed
                            println("ERROR "+response.code())
                        }
                    } catch (e: Exception) {
                        // Network request failed
                    }
                }

                finish()

            }

        }

    }
}