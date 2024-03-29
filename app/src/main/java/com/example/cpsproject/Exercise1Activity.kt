package com.example.cpsproject

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.punchthrough.blestarterappandroid.ble.ConnectionManager
import kotlinx.android.synthetic.main.activity_exercise1.*

class Exercise1Activity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise1)

        tvExDescription1.text = "Make the patient write a grocery list with at least 6 elements"

        btnStart1.setOnClickListener {
            ConnectionManager.StartOnBoard()
        }

        btnStop1.setOnClickListener {
            ConnectionManager.StopOnBoard()
        }

        // PER PROVARE: Poi vediamo in che modo inserirlo
        btnDownloadEx1.setOnClickListener {
            ConnectionManager.download()
        }
    }
}