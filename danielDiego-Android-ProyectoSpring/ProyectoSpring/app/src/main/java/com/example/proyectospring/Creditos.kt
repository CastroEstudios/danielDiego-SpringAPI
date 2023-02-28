package com.example.proyectospring

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectospring.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class Creditos : AppCompatActivity() {
    lateinit var botonDeLaRisa: Button
    lateinit var binding: ActivityMainBinding
    lateinit var bottomMenu: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_creditos)

        botonDeLaRisa = findViewById(R.id.botonDeRisa)

        botonDeLaRisa.setOnClickListener {
            val miIntent = Intent(this,LogIn::class.java)
            startActivity(miIntent)
        }

    }

}