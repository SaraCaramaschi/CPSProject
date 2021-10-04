package com.example.cpsproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class Connection : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connection)

        val clickme = findViewById<Button>(R.id.btnscanconnection)

        clickme.setOnClickListener {
            Toast.makeText(this, "The pen is already connected", Toast.LENGTH_SHORT).show()
        }
    }
}