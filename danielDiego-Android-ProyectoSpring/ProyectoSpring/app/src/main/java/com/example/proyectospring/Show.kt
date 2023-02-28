package com.example.proyectospring

import java.io.Serializable

data class Show(var showId: String? = null
                   ,var tipo : String? = null,
                   var titulo : String? = null,
                   var director: String? = null,
                   var actores: String? = null,
                    var pais: String? = null,
                    var fecha: String? = null,
                    var lanzamiento: Int? = -1,
                    var rating: String? = null,
                    var duracion: String? = null,
                    var tags:String? = null,
                    var descripcion:String? = null)