package com.example.cpsproject

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cpsproject.managers.PatientsManager
import com.example.cpsproject.managers.PenManager
import com.example.cpsproject.model.Session
import com.punchthrough.blestarterappandroid.ble.ConnectionManager
import kotlinx.android.synthetic.main.activity_exercise1.*
import java.time.LocalDateTime
import java.util.*

class Exercise1Activity: AppCompatActivity() {
    lateinit var session: Session

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise1)

        tvExDescription1.text = "Make the patient write a grocery list with at least 6 elements"

        btnStart1.setOnClickListener {
            ConnectionManager.StartOnBoard()
            // Inizializzazione di una sessione:
            startSession()
        }

        btnStop1.setOnClickListener {
            ConnectionManager.StopOnBoard()
        }

        // PER PROVARE: Poi vediamo in che modo inserirlo
        btnDownloadEx1.setOnClickListener {
            ConnectionManager.download()
        }
    }

    @SuppressLint("NewApi")
    private fun startSession() {
        session.device = PenManager.penName
        session.datetime = LocalDateTime.now().toString()
        session.patientPos = PatientsManager.selectedPatient

    }
}