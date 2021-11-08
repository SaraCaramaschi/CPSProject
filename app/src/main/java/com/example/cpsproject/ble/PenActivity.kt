package com.example.cpsproject.ble

import android.bluetooth.BluetoothDevice
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.cpsproject.R
import com.example.cpsproject.Schermata1

class PenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pen)

        val btnRealTime = findViewById<Button>(R.id.btnRealTime)
        btnRealTime.setOnClickListener {
            // poi ufficialmente sarà realtimeactivity
            // alla fine proprio quando funziona tutto cancelliamo Bleoperationsactivity
            val intent = Intent(this, BleOperationsActivityProva::class.java)
            startActivity(intent)

            /*
                    Intent(this@MainConnection, PenActivity::class.java).also {
                    it.putExtra(BluetoothDevice.EXTRA_DEVICE, gatt.device)
                    startActivity(it)
                }

            */
        }

    }
}