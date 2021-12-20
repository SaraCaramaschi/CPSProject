package com.example.cpsproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.cpsproject.managers.SessionManager
import com.example.cpsproject.managers.saveDocument
import com.punchthrough.blestarterappandroid.ble.ConnectionManager
import kotlinx.android.synthetic.main.activity_exercise1.*
import kotlinx.android.synthetic.main.activity_exercise3.*
import kotlinx.android.synthetic.main.activity_exercise1.btnPatPhaseBack as btnPatPhaseBack1

class Exercise3Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise3)

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
            saveDocument(SessionManager.sessione, applicationContext)
            ConnectionManager.download()
        }
        btnPatPhaseBack.setOnClickListener {
            val intent = Intent(this, PatientPageActivity::class.java)
            startActivity(intent)
        }
    }
}