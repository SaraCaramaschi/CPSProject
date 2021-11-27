package com.example.cpsproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.cpsproject.ble.MainConnection
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_schermata1.*

class Schermata1Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schermata1)

        val userId=intent.getStringExtra("user_id")
        val emailId=intent.getStringExtra("email_id")
        val username=intent.getStringExtra("username")
        tvClinician.text="$username"



//PROVA PER CLINICO ILA MA I PROSSIMI DUE COMMENTI POSSONO ESSERE CANCELLATI
      //  val tvClinician=findViewById<TextView>(R.id.tvClinician)
       // tvClinician.setText("Doctor  ${etEmailLog} ")

        // bottone LOG OUT
        val btnLogOut= findViewById<Button>(R.id.btnLogOut)
        btnLogOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
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