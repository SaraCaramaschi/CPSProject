package com.example.cpsproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager.*
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.cpsproject.managers.ClinicianManager
import com.example.cpsproject.model.Clinician
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import timber.log.Timber
import java.util.*

// ok ultima versione
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var emailLog: String = String()
        var passwordLog: String = String()
        var clinician: Clinician


        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        btnLanguage.setOnClickListener {
            val intent = Intent(this, LanguageActivity::class.java)
            startActivity(intent)
        }

        val btnSignIn = findViewById<Button>(R.id.btnSignIn)
        btnSignIn.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // bottone da schermata login a schermata register
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)
        btnSubmit.setOnClickListener {

            when {
                TextUtils.isEmpty(etEmailLog.text.toString().trim() { it <= ' ' }) -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "Please insert the emil",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(etPasswordLog.text.toString().trim() { it <= ' ' }) -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "Please enter the password", Toast.LENGTH_SHORT
                    )
                        .show()
                }

                else -> {
                    var etEmailLog = findViewById<EditText>(R.id.etEmailLog)
                    emailLog = etEmailLog.text.toString().trim { it <= ' ' }
                    ClinicianManager.email = emailLog

                    var etPasswordLog = findViewById<EditText>(R.id.etPasswordLog)
                    passwordLog = etPasswordLog.text.toString().trim { it <= ' ' }
                    ClinicianManager.password = passwordLog

                    FirebaseAuth.getInstance().signInWithEmailAndPassword(emailLog, passwordLog)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {

                                Toast.makeText(
                                    this@LoginActivity, "You are logged in succesfully!",
                                    Toast.LENGTH_SHORT
                                ).show()

                                val intent =
                                    Intent(this@LoginActivity, Schermata1Activity::class.java)

//TODO USERNAME DA PASSARE COME I DATI DEL PAZIENTE, per ora mostriamo l'id di firebase automatico
                                /*intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra(
                                    "user_id",
                                    FirebaseAuth.getInstance().currentUser!!.uid
                                )

                                intent.putExtra("email_id", emailLog)*/
                                intent.putExtra("email_id", emailLog)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(
                                    this@LoginActivity,
                                    task.exception!!.message.toString(),
                                    Toast.LENGTH_SHORT
                                )
                                    .show()

                            }
                        }


                }

                //if (etEmailLog.text.trim().isNotEmpty() || etPasswordLog.text.trim().isNotEmpty()) {
                //val intent = Intent(this, Schermata1::class.java)
                //startActivity(intent)
                //TODO VERIFICA IDENTITA' OK ora l'ho tolto cosi posso entrare, poi lo rimettiamo
                //} else {
                //Toast.makeText(this, "Imput required", Toast.LENGTH_LONG).show()
                //}
            }




        }
    }

    private fun changeLanguage(lang: String) {
        val sharedPreferences = getDefaultSharedPreferences(applicationContext)
        val language = sharedPreferences.getString("language",lang)
        Toast.makeText(applicationContext,language,Toast.LENGTH_SHORT).show()

        if(language=="English"){
            Toast.makeText(applicationContext,"English",Toast.LENGTH_SHORT).show()
            language("en-us")
        }else if(language=="Italian"){
            Toast.makeText(applicationContext,"Italian",Toast.LENGTH_SHORT).show()
            language("it-rIT")
        }
    }

    fun language(language: String){
        val locale = Locale(language)
        Locale.setDefault(locale)
        val resources = resources
        val configuration = resources.getConfiguration()
        configuration.locale = locale
        resources.updateConfiguration(configuration, resources.getDisplayMetrics())
    }
}



