package com.example.cpsproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import timber.log.Timber

// ok ultima versione
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        // bottone da schermata 0 a schermata 1
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)
        btnSubmit.setOnClickListener {
            //if (etEmailLog.text.trim().isNotEmpty() || etPasswordLog.text.trim().isNotEmpty()) {
            val intent = Intent(this, Schermata1::class.java)
            startActivity(intent)
            //TODO VERIFICA IDENTITA' OK ora l'ho tolto cosi posso entrare, poi lo rimettiamo
            //} else {
            //Toast.makeText(this, "Imput required", Toast.LENGTH_LONG).show()
            //}
        }

        val btnSignIn = findViewById<Button>(R.id.btnSignIn)
        btnSignIn.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }




    }
}



