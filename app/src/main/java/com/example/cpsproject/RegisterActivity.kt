package com.example.cpsproject

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cpsproject.managers.ClinicianManager
import com.example.cpsproject.model.Clinician
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_add_patient.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.etName
import kotlinx.android.synthetic.main.activity_register.etSurname

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnSubmitdata.setOnClickListener {

            when {
                TextUtils.isEmpty(etName.text.toString().trim() { it <= ' ' }) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please insert your name",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(etSurname.text.toString().trim() { it <= ' ' }) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please insert your surname",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(etEmail.text.toString().trim() { it <= ' ' }) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter Email", Toast.LENGTH_SHORT
                    )
                        .show()
                }
                TextUtils.isEmpty(etPassword1.text.toString().trim() { it <= ' ' }) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please write a password",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(etPassword2.text.toString().trim() { it <= ' ' }) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please confirm the password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                etPassword1.text.toString().trim() != etPassword2.text.toString().trim() -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Password does not match",
                        Toast.LENGTH_SHORT
                    ).show()
                }


                else -> {
                    var etEmail = findViewById<EditText>(R.id.etEmail)
                    var email = etEmail.text.toString().trim { it <= ' ' }
                    var etPassword = findViewById<EditText>(R.id.etPassword1)
                    var password = etPassword.text.toString().trim { it <= ' ' }
                    var etName = findViewById<EditText>(R.id.etName)
                    var name = etName.text.toString().trim { it <= ' ' }
                    var etSurname = findViewById<EditText>(R.id.etSurname)
                    var surname = etSurname.text.toString().trim { it <= ' ' }

                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val firebaseUser: FirebaseUser = task.result!!.user!!

                                val clinician = Clinician(
                                    etName.text.toString(),
                                    etSurname.text.toString(),
                                    etEmail.text.toString(),
                                    firebaseUser.uid
                                )
                                ClinicianManager.addClinician(clinician, applicationContext)

                                Toast.makeText(
                                    this@RegisterActivity, "You are regisetred succesfully!",
                                    Toast.LENGTH_SHORT
                                ).show()

                                val intent = Intent(this, LoginActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(
                                    this@RegisterActivity,
                                    task.exception!!.message.toString(),
                                    Toast.LENGTH_SHORT
                                )
                                    .show()

                            }
                        }


                }

            }


        }
    }


}


