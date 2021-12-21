package com.example.cpsproject.ble

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.Toast
import com.example.cpsproject.Phase1Activity
import com.example.cpsproject.Phase2Activity
import com.example.cpsproject.R
import com.example.cpsproject.managers.SessionManager
import com.punchthrough.blestarterappandroid.ble.ConnectionManager
import kotlinx.android.synthetic.main.activity_pen.*
import kotlinx.android.synthetic.main.activity_real_time.*
import timber.log.Timber

class PenActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pen)


        // Accessing to real time activity
        Toast.makeText(this@PenActivity,"Pen connected", Toast.LENGTH_SHORT).show()

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
            ConnectionManager.download()
            Toast.makeText(this@PenActivity,"Successful download ", Toast.LENGTH_SHORT).show()
        }

        btnProtocol.setOnClickListener {
            if (SessionManager.sessione.phase == 1){
                val intent1 = Intent(this, Phase1Activity::class.java)
                startActivity(intent1)
            }else if (SessionManager.sessione.phase == 2){
                val intent1 = Intent(this, Phase2Activity::class.java)
                startActivity(intent1)
            }
        }
    }
}