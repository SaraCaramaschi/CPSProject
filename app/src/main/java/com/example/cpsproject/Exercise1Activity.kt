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
import com.example.cpsproject.model.PenData
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise1)

        val intent = getIntent()
        val phase = intent.getIntExtra("phase",1)

        btnStart1.setOnClickListener {
            ConnectionManager.StartOnBoard()
        }

        btnStop1.setOnClickListener {
            ConnectionManager.StopOnBoard()
            Toast.makeText(
                this@Exercise1Activity,
                "Remember to wait one minute in between one exercise and the other",
                Toast.LENGTH_LONG
            ).show()
        }

        btnDownloadEx1.setOnClickListener {
            Toast.makeText(
                this@Exercise1Activity,
                "You need to perform all three exercises before downloading files",
                Toast.LENGTH_LONG
            ).show()
            //ConnectionManager.download()
        }
    }

    fun provaAmano() {
        var datiPenna: String = String()
        var datiPennaBA: ByteArray
        datiPenna = "4329BA032D003701F6FFF5FFFFFF00005F000000"
        datiPennaBA = datiPenna.toByteArray()
        Timber.d("Bytearray: datipennaba: " + datiPennaBA)
        ConnectionManager.readData(datiPennaBA)
    }


    private fun fillSession(data:PenData, session: Session) {
        //session.data = data --> sono gine, l'ho commentato perchè se no non andava nada
    }
}
