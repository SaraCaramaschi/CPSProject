package com.example.cpsproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_phase_1.*

class Phase1Activity: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phase_1)

        val tvDescription = findViewById<TextView>(R.id.tvDescription1)

        tvDescription.setText("In the same day the patient has to repeat the 3 exercises twice. The 3 exercises will be performed in random order with a break of at least 1 minute from one to another.\n" +
                        "Between the two session the patient has to wait at least 30 minutes. \n" + "When an exercise is completed a MARK will appear. If there are two marks near each exercise the phase 1 is completed."
            )

        btnEx1Ph1.setOnClickListener {
            val intent1 = Intent(this, Exercise1Activity::class.java)
            startActivity(intent1)
        }

        btnEx2Ph1.setOnClickListener {
            val intent = Intent(this, Exercise2Activity::class.java)
            startActivity(intent)
        }

        btnEx3Ph1.setOnClickListener {
            val intent = Intent(this, Exercise3Activity::class.java)
            startActivity(intent)
        }
    }

}




