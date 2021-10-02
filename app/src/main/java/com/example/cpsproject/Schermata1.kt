package com.example.cpsproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Schermata1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schermata1)

        // bottone LOG OUT
        val btnLogOut= findViewById<Button>(R.id.btnLogOut)
        btnLogOut.setOnClickListener {
            val intent = Intent(this, Schermata0::class.java)
            startActivity(intent)
        }
        // bottone LISTA PAZIENTI
        val btnPatients = findViewById<Button>(R.id.btnPatients)
        btnPatients.setOnClickListener {
            val intent = Intent(this, PatientsList::class.java)
            startActivity(intent)
        }
        // bottone CONNESSIONE PENNA
        val btnConnection = findViewById<Button>(R.id.btnConnection)
        btnConnection.setOnClickListener {
            val intent = Intent(this, Connection::class.java)
            startActivity(intent)
        }
    }
}