package com.example.cpsproject.ble

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.cpsproject.R
import com.example.cpsproject.Schermata1
import kotlinx.android.synthetic.main.activity_real_time.*
import org.jetbrains.anko.find

class RealTimeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_real_time)
        // Riempire le text view del design con valori ottenuti da readBattery / readData

        //bottone per iniziare a riempire la textview con i dati (=iniziare il real time)
        val startrealtime = findViewById<Button>(R.id.Startrealtime)
        Startrealtime.setOnClickListener {
        /*val intent = getIntent()
            val acc_x = intent.getStringExtra("acc_x")
            textView6.text= "+acc_x+"*/
        }

    }
}