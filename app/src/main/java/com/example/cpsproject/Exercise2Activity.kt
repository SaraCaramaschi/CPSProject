package com.example.cpsproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.punchthrough.blestarterappandroid.ble.ConnectionManager
import kotlinx.android.synthetic.main.activity_exercise2.*

class Exercise2Activity : AppCompatActivity() {

    //TODO
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise2)

        tvExDescription2.text = "Make the patient write a free text (maximum 7 rows)"

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
            //ConnectionManager.download()
        }
    }
}