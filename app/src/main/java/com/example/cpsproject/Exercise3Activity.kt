package com.example.cpsproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.cpsproject.managers.SessionManager
import com.example.cpsproject.managers.saveDocument
import com.example.cpsproject.model.Session
import com.punchthrough.blestarterappandroid.ble.ConnectionManager
import kotlinx.android.synthetic.main.activity_exercise3.*
import timber.log.Timber

class Exercise3Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise3)

        tvExDescription3.text = "Read to the patient 15 words from Batteria E.N.P.A. including 7 regular words, 3 irregular words and 5 not-words. Read them in a random order"

        btnStart3.setOnClickListener {
            ConnectionManager.StartOnBoard()
        }

        btnStop3.setOnClickListener {
            ConnectionManager.StopOnBoard()
        }

        btnDownloadEx3.setOnClickListener {
            Toast.makeText(
                this@Exercise3Activity,
                "Download started",
                Toast.LENGTH_SHORT
            ).show()

            var provaSess: Session = provaamanoSessione()
            saveDocument(provaSess, applicationContext)
            //saveDocument(SessionManager.sessione, applicationContext)
            //ConnectionManager.download()
        }
    }

    private fun provaamanoSessione(): Session {
        var session =Session()
        session.device = "CIAOPROVA"
        Timber.d("ok prova a mano")
        return session
    }
}