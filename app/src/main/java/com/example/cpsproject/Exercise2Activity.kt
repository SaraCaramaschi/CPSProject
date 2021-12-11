package com.example.cpsproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_exercise2.*

class Exercise2Activity : AppCompatActivity() {

    //TODO
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise2)

        tvExDescription2.text = "Make the patient write a free text (maximum 7 rows)"

    }
}