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

        var btnBack=findViewById<Button>(R.id.btnBackToPatientPage1)
        var intentBack=Intent(this, PatientPageActivity::class.java)
        btnBack.setOnClickListener{
            startActivity(intentBack)
        }


    }


}




