package com.example.proyectospring

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adaptador(var listaShows: ArrayList<Show>) :
    RecyclerView.Adapter<Adaptador.layout_show>() {

    inner class layout_show(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        var imagen: ImageView
        var titulo: TextView
        var director: TextView
        var reparto: TextView
        var tags:TextView
        var ano: TextView

        init {
            imagen = itemView.findViewById(R.id.imagen)
            titulo = itemView.findViewById(R.id.titulo)
            director = itemView.findViewById(R.id.director)
            reparto = itemView.findViewById(R.id.reparto)
            tags = itemView.findViewById(R.id.tags)
            ano = itemView.findViewById(R.id.ano)

            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): layout_show {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.layout_show, parent, false)
        return layout_show(item)
    }

    override fun onBindViewHolder(holder: layout_show, position: Int) {
        var showActual = listaShows[position]

        if(showActual.tipo?.lowercase().equals("movie")){
            holder.imagen.setImageResource(R.drawable.snow)
        }else if(showActual.tipo?.lowercase().equals("tv show")){
            holder.imagen.setImageResource(R.drawable.stranger)
        }

        holder.titulo.text = showActual.titulo
        holder.director.text = "Director: "+showActual.director
        holder.reparto.text = "Reparto: "+showActual.actores
        holder.tags.text = showActual.tags
        holder.ano.text = showActual.fecha

    }

    override fun getItemCount() = listaShows.size

}