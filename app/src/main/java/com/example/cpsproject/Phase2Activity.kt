package com.example.cpsproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity

class Phase2Activity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phase_2)

        val tvDescription2 = findViewById<TextView>(R.id.tvDescription2)

        tvDescription2.setText("In this phase the patient has to do the 3 exercises once a month. \n"+
        "In the personal page of the patient there is the progression of the phase 2. \n"+"When an exercise is completed a MARK will appear.")

        val btn1= findViewById<Button>(R.id.btnEx1Ph2)
        btn1.setOnClickListener {
            val intent1 = Intent(this, Exercise1Activity::class.java)
            startActivity(intent1)
        }
    }


}
