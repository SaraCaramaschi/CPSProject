package com.example.cpsproject

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Exercise1Activity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise1)

        val tvEx1Description = findViewById<TextView>(R.id.tvExDescription)

        tvEx1Description.setText("Make the patient write a grocery list with at least 6 elements")

    }
}