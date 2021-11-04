package com.example.cpsproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import timber.log.Timber

// ok ultima versione
class Schermata0 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schermata0)

        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        // bottone da schermata 0 a schermata 1
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)
        btnSubmit.setOnClickListener {
            val intent = Intent(this, Schermata1::class.java)
            startActivity(intent)
        }

        val btnSignIn = findViewById<Button>(R.id.btnSignIn)
        btnSignIn.setOnClickListener {
            val intent = Intent(this, AddClinicianActivity::class.java)
            startActivity(intent)
        }
    }
}



