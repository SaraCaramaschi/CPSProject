package com.example.cpsproject.ble

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class Pen1Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pen1)

        // Accessing to real time activity
        Toast.makeText(this@Pen1Activity,"Pen connected", Toast.LENGTH_SHORT).show()

        btnRealTime.setOnClickListener {
            val intent = Intent(this, RealTimeActivity::class.java)
            startActivity(intent)
        }

        // Formatting the pen
        btnFormat.setOnClickListener {
            ConnectionManager.format()
            Toast.makeText(this@Pen1Activity,"The pen has been formatted", Toast.LENGTH_SHORT).show()
        }

        // Downloading files from the pen to computer
        btnDownload.setOnClickListener {
            //PenManager.downloadJson(PenManager.penData!!, this)
            ConnectionManager.download()
            Toast.makeText(this@Pen1Activity,"Successful download ", Toast.LENGTH_SHORT).show()
        }

    }
}