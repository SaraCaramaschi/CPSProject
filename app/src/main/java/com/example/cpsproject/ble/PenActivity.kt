package com.example.cpsproject.ble

import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.cpsproject.R
import com.example.cpsproject.Schermata1
import com.example.cpsproject.managers.PenManager
import com.example.cpsproject.model.PenData
import com.punchthrough.blestarterappandroid.ble.ConnectionManager
import kotlinx.android.synthetic.main.activity_pen.*

class PenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pen)

        btnRealTime.setOnClickListener {
            val intent = Intent(this, RealTimeActivity::class.java)
            startActivity(intent)
        }

        btnFormat.setOnClickListener {
            ConnectionManager.format()
            Toast.makeText(this@PenActivity,"The pen has been formatted", Toast.LENGTH_SHORT).show()
        }

        btnDownload.setOnClickListener {
            PenManager.downloadJson(PenManager.penData!!, this)
        }
    }
}