package com.example.cpsproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.punchthrough.blestarterappandroid.ble.ConnectionManager
import kotlinx.android.synthetic.main.activity_exercise1.*
import kotlinx.android.synthetic.main.activity_exercise2.*
import kotlinx.android.synthetic.main.activity_exercise1.btnPatPhaseBack as btnPatPhaseBack1

class Exercise2Activity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise2)


        btnStart2.setOnClickListener {
            ConnectionManager.StartOnBoard()
        }

        btnStop2.setOnClickListener {
            ConnectionManager.StopOnBoard()
        }

        btnDownloadEx2.setOnClickListener {
            Toast.makeText(
                this@Exercise2Activity,
                "You need to perform all three exercises before downloading files",
                Toast.LENGTH_LONG
            ).show()

        }

        btnPatPhaseBack.setOnClickListener {
            val intent = Intent(this, Phase1Activity::class.java)
            startActivity(intent)
        }
    }
}