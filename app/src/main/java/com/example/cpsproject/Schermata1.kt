package com.example.cpsproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.cpsproject.ble.MainConnection
import kotlinx.android.synthetic.main.activity_add_clinician.*
import kotlinx.android.synthetic.main.activity_schermata0.*

class Schermata1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schermata1)

//PROVA PER CLINICO ILA MA I PROSSIMI DUE COMMENTI POSSONO ESSERE CANCELLATI
      //  val tvClinician=findViewById<TextView>(R.id.tvClinician)
       // tvClinician.setText("Doctor  ${etEmailLog} ")

        // bottone LOG OUT
        val btnLogOut= findViewById<Button>(R.id.btnLogOut)
        btnLogOut.setOnClickListener {
            val intent = Intent(this, Schermata0::class.java)
            startActivity(intent)
        }
        // bottone LISTA PAZIENTI
        val btnPatients = findViewById<Button>(R.id.btnPatients)
        btnPatients.setOnClickListener {
            val intent = Intent(this, PatientListActivity::class.java)
            startActivity(intent)
        }

        // bottone CONNESSIONE PENNA
        val btnConnection = findViewById<Button>(R.id.btnPenConnection)
        btnConnection.setOnClickListener {
            val intent = Intent(this, MainConnection::class.java)
            startActivity(intent)
        }
    }
}