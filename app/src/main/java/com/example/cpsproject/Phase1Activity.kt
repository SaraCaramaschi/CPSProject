package com.example.cpsproject

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import com.example.cpsproject.managers.PatientsManager
import com.example.cpsproject.managers.PenManager
import com.example.cpsproject.managers.SessionManager
import com.example.cpsproject.model.Session
import com.google.firebase.auth.FirebaseAuth
import com.punchthrough.blestarterappandroid.ble.ConnectionManager
import com.punchthrough.blestarterappandroid.ble.ConnectionManager.isConnected
import kotlinx.android.synthetic.main.activity_phase_1.*

class Phase1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phase_1)
       /* if (!ConnectionManager.currDevice!!.isConnected()) {
            Toast.makeText(this@Phase1Activity, "The pen disconnected!", Toast.LENGTH_SHORT)
                .show()
        }*/
        val tvDescription = findViewById<TextView>(R.id.tvDescription1)

        tvDescription.setText(
            "In the same day the patient has to repeat the 3 exercises twice. The 3 exercises will be performed in random order with a break of at least 1 minute from one to another.\n" +
                    "Between the two session the patient has to wait at least 30 minutes. \n" )

        btnEx1Ph1.setOnClickListener {
            val intent1 = Intent(this, Exercise1Activity::class.java)
            startActivity(intent1)
        }

        btnEx2Ph1.setOnClickListener {
            val intent = Intent(this, Exercise2Activity::class.java)
            startActivity(intent)
        }

        btnEx3Ph1.setOnClickListener {
            val intent = Intent(this, Exercise3Activity::class.java)
            startActivity(intent)
        }
    }


}




