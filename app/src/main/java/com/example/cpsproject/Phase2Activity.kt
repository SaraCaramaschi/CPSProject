package com.example.cpsproject

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
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

class Phase2Activity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phase_2)

        if (!ConnectionManager.currDevice!!.isConnected()) {
            Toast.makeText(this@Phase2Activity, "The pen disconnected!", Toast.LENGTH_SHORT)
                .show()
        }
        
        val btn1= findViewById<Button>(R.id.btnEx1Ph2)
        btn1.setOnClickListener {
            val intent = getIntent()
            val phase = intent.getIntExtra("phase",1)

            val intent1 = Intent(this, Exercise1Activity::class.java)
            intent1.putExtra("phase",2)
            startActivity(intent1)

        }
    }
}
