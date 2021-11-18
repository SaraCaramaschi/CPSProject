package com.example.cpsproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class Exercise2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise2)

        val tvEx1Description = findViewById<TextView>(R.id.tvExDescription)

        tvEx1Description.setText("Make the patient write a free text (maximum 7 rows)")


    }
}