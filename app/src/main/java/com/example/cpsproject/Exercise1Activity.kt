package com.example.cpsproject

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cpsproject.managers.PatientsManager
import com.example.cpsproject.managers.PenManager
import com.example.cpsproject.model.PenData
import com.example.cpsproject.model.Session
import com.punchthrough.blestarterappandroid.ble.ConnectionManager
import kotlinx.android.synthetic.main.activity_exercise1.*
import java.time.LocalDateTime

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
            // TODO Otteniamo eventuale oggetto DATA come??
            // fillSession(data, session)
        }
    }

    @SuppressLint("NewApi")
    private fun startSession() {
        session.device = PenManager.penName
        session.datetime = LocalDateTime.now().toString()
        session.patientPos = PatientsManager.selectedPatient

    }
    private fun fillSession(data:PenData, session: Session) {
        session.data = data
    }
}