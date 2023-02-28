package com.example.proyectospring

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.widget.EditText
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectospring.interfaces.ShowsAPI
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class Pantalla : AppCompatActivity() {

    lateinit var adaptadorShow: Adaptador
    lateinit var recyclerShow: RecyclerView
    lateinit var listaShows: ArrayList<Show>
    lateinit var listaBusqueda: ArrayList<Show>
    lateinit var materialTool: MaterialToolbar
    lateinit var botonAnyadir: FloatingActionButton
    lateinit var botonRefrescar: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla)


        botonAnyadir = findViewById(R.id.botonAnadir)
        botonRefrescar = findViewById(R.id.botonRefrescar)

        recyclerShow = findViewById(R.id.recyclerViewShow)
        listaShows = ArrayList()
        listaBusqueda = ArrayList()

        listaShows.add(Show("Show_id","Movie","Snowpiercer","Boon Jong Ho","Cris Evans","ESPAÑA","April 17, 2021",2015,"PG-13","90 min","Dramas,Thriller","Cris evans calvo"))

        adaptadorShow = Adaptador(listaShows)
        recyclerShow.adapter = adaptadorShow
        recyclerShow.layoutManager = GridLayoutManager(this,1)

        obtenerShows()

        materialTool = findViewById(R.id.materialToolbar)
        setSupportActionBar(materialTool)

        botonAnyadir.setOnClickListener{

            val miIntent = Intent(this,InsertarShow::class.java)
            startActivity(miIntent)

            obtenerShows()
        }

        botonRefrescar.setOnClickListener{
            obtenerShows()
        }
    }


    public fun obtenerShows(){

        var antes = listaShows.size
        listaShows.clear()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var interfaz = retrofit.create(ShowsAPI::class.java)

        interfaz.obetenerShows().enqueue(object : Callback<List<Show>>{

            override fun onResponse(call: Call<List<Show>>, response: Response<List<Show>>) {

                if (response.code() == 200){
                    println("HEY")
                    val r = response.body()
                    if (r!=null){

                        for (show in r){
                            listaShows.add(show)
                        }
                    }
                }else{
                    println("HAY ALGO MAL")
                }
                adaptadorShow = Adaptador(listaShows)
                recyclerShow.adapter = adaptadorShow

                adaptadorShow.notifyItemRangeRemoved(0, antes);
                //tell the recycler view how many new items we added
                adaptadorShow.notifyItemRangeInserted(0, listaShows.size);
            }

            override fun onFailure(call: Call<List<Show>>, t: Throwable) {
                println(t.message)
            }
        })
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.menu,menu)

        val searchItem = menu.findItem(R.id.search)
        val basura = menu.findItem(R.id.borrar)
        val update = menu.findItem(R.id.update)

        val searchView = searchItem.actionView as SearchView?

        basura.setOnMenuItemClickListener {

            var layoutDialogo = LayoutInflater.from(this)
                .inflate(R.layout.layout_dialogo, null, false)
            val dialogo = MaterialAlertDialogBuilder(MainActivity@this)

            dialogo.setTitle("Introduzca el titulo del show a borrar")
            dialogo.setView(layoutDialogo)
            dialogo.setPositiveButton("OK") { dialog, which ->

                val titulo = layoutDialogo.findViewById<AppCompatEditText>(R.id.dialogoTitulo)

                val retrofit = Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8080/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                retrofit.create(ShowsAPI::class.java).deleteShow(titulo.text.toString()).enqueue(object : Callback<Void>{
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        println("BORRADO CON ÉXITO: "+ response.code())
                        Toast.makeText(applicationContext,"${titulo.text.toString()} Borrado con éxito", Toast.LENGTH_LONG).show()
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        println("NO SE PUDO BORRAR: "+t.message)
                    }
                })

            }

            dialogo.setNegativeButton("Cancel", null)
            dialogo.show()
            obtenerShows()
            true
        }

        val editText = searchView?.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)

        editText?.setTextColor(0xFFFFFFFF.toInt())

        searchView!!.maxWidth = Int.MAX_VALUE

        searchView.setOnCloseListener {
            var antes = adaptadorShow.listaShows .size
            adaptadorShow.listaShows = listaShows
            //tell the recycler view that all the old items are gone
            adaptadorShow.notifyItemRangeRemoved(0, antes);
            //tell the recycler view how many new items we added
            adaptadorShow.notifyItemRangeInserted(0, listaShows.size);

            false
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(texto: String?): Boolean {
                listaBusqueda.clear()
                for (show in listaShows){
                    if (texto != null) {
                        if(show.titulo?.take(texto.length)?.toLowerCase().equals(texto)){
                            listaBusqueda.add(show)
                        }else{
                        }
                    }
                }
                adaptadorShow = Adaptador(listaBusqueda)
                recyclerShow.adapter = adaptadorShow
                return false
            }
        })

        update.setOnMenuItemClickListener {

            var layoutDialogo = LayoutInflater.from(this)
                .inflate(R.layout.layout_dialogo, null, false)
            val dialogo = MaterialAlertDialogBuilder(MainActivity@this)

            dialogo.setTitle("Introduzca el titulo del show a actualizar")
            dialogo.setView(layoutDialogo)
            dialogo.setPositiveButton("OK") { dialog, which ->

                val titulo = layoutDialogo.findViewById<AppCompatEditText>(R.id.dialogoTitulo)

                var miIntent = Intent(this,ActualizarShow::class.java)

                miIntent.putExtra("titulo",titulo.text.toString())

                startActivity(miIntent)

            }

            dialogo.setNegativeButton("Cancel", null)
            dialogo.show()
            obtenerShows()
            true
        }

        return true
    }

}