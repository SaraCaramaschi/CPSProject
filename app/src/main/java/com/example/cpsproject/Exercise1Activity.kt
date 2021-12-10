package com.example.cpsproject

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cpsproject.managers.PatientsManager
import com.example.cpsproject.managers.PenManager
import com.example.cpsproject.managers.SessionManager
import com.example.cpsproject.model.Session
import com.punchthrough.blestarterappandroid.ble.ConnectionManager
import kotlinx.android.synthetic.main.activity_exercise1.*
import timber.log.Timber
import java.io.*
import java.net.URI
import java.time.LocalDateTime
import java.util.*
import java.util.stream.Collectors

class Exercise1Activity: AppCompatActivity() {
    var session: Session = Session()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise1)

        tvExDescription1.text = "Make the patient write a grocery list with at least 6 elements"

        btnStart1.setOnClickListener {
            ConnectionManager.StartOnBoard()

            // Inizializzazione di una sessione:
            startSession()

            // PARTE DI PROVA CON .TXT
            //provaAmano()
        }

        btnStop1.setOnClickListener {
            ConnectionManager.StopOnBoard()
        }

        // PER PROVARE: Poi vediamo in che modo inserirlo
        btnDownloadEx1.setOnClickListener {
            ConnectionManager.download()
        }
    }

    /*fun provaAmano() {
        var datiPenna: String = String()
        var datiPennaBA: ByteArray
        datiPenna = "4329BA032D003701F6FFF5FFFFFF00005F000000"
        datiPennaBA = datiPenna.toByteArray()
        Timber.d("Bytearray: datipennaba: " + datiPennaBA)
        ConnectionManager.readData(datiPennaBA)
    }*/



    @SuppressLint("NewApi")
    private fun startSession() {
        session.device = PenManager.penName.toString()
        session.datetime = LocalDateTime.now().toString()
        session.patientID = PatientsManager.selectedPatient.toString()

        SessionManager.sessione = session
    }
}
