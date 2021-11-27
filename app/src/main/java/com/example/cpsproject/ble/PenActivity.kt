package com.example.cpsproject.ble

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.cpsproject.R
import com.punchthrough.blestarterappandroid.ble.ConnectionManager
import kotlinx.android.synthetic.main.activity_pen.*

class PenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pen)

        // Accessing to real time activity
        btnRealTime.setOnClickListener {
            val intent = Intent(this, RealTimeActivity::class.java)
            startActivity(intent)
        }

        // Formatting the pen
        btnFormat.setOnClickListener {
            ConnectionManager.format()
            Toast.makeText(this@PenActivity,"The pen has been formatted", Toast.LENGTH_SHORT).show()
        }

        // Downloading files from the pen to computer
        btnDownload.setOnClickListener {
            //PenManager.downloadJson(PenManager.penData!!, this)
            ConnectionManager.download()
        }
    }
}