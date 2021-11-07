package com.example.cpsproject.ble

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cpsproject.R

class RealTimeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_real_time)
        // Riempire le text view del design con valori ottenuti da readBattery / readData
    }
}