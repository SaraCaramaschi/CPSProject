package com.example.cpsproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class Exercise3Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise3)

        val tvEx1Description = findViewById<TextView>(R.id.tvExDescription)
        tvEx1Description.setText("Read to the patient 15 words from Batteria E.N.P.A. including 7 regular words, 3 irregular words and 5 not-words. Read them in a random order")

    }
}