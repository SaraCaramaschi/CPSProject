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

class ConnectionMessageActivity: AppCompatActivity()  {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connection_message)

        //VOLEVO FARE UNA ACTIVITY PER FAR COMPARIRE UN MESSAGGIO PER CHIDERE CONFERMA DELL'ELIMINAZIONE
        // poi ho visto che si può fare un "alert dialog"

        val intent= getIntent()
        val phase= intent.getIntExtra("phase", 0)

        val tvMessage = findViewById<TextView>(R.id.tvMessage)
        tvMessage.setText("Did you connect the pen?")

        val btnYes = findViewById<Button>(R.id.btnYes1)
        val btnNo = findViewById<Button>(R.id.btnNo1)

        btnNo.setOnClickListener{
            var intent= Intent(this, MainConnection::class.java)
            startActivity(intent)
        }

        btnYes.setOnClickListener{
            if (phase == 2){
                var intent = Intent(this, Phase2Activity::class.java)
                startActivity(intent)
            }else{
                var intent = Intent(this, Phase1Activity::class.java)
                startActivity(intent)
            }

        }


    }


}
