package com.example.cpsproject

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cpsproject.ble.MainConnection
import com.example.cpsproject.managers.PatientsManager
import com.example.cpsproject.model.Patient
import timber.log.Timber

class ConnectionMessageActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connection_message)

        val intent = getIntent()
        val phase = intent.getIntExtra("phase", 0)

        val btnYes = findViewById<Button>(R.id.btnYes1)
        val btnNo = findViewById<Button>(R.id.btnNo1)

        btnNo.setOnClickListener {
            var intent = Intent(this, MainConnection::class.java)
            startActivity(intent)
        }

        btnYes.setOnClickListener {
            Timber.d("$phase")
            if (phase == 2) {
                var intent = Intent(this, Phase2Activity::class.java)
                startActivity(intent)
            } else {
                var intent = Intent(this, Phase1Activity::class.java)
                startActivity(intent)
            }

        }


    }


}
