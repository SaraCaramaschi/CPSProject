package com.example.cpsproject

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_register.*

//commento
class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)





        btnSubmitdata.setOnClickListener {

            when {
                TextUtils.isEmpty(etUsername.text.toString().trim() { it <= ' ' }) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please inserrt a username",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(etUsername.text.toString().trim() { it <= ' ' }) -> {
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
                    var etUsername = findViewById<EditText>(R.id.etUsername)
                    var username = etUsername.text.toString().trim { it <= ' ' }

                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email , password).addOnCompleteListener(
                        { task ->
                            if (task.isSuccessful) {
                                val firebaseUser: FirebaseUser = task.result!!.user!!

                                Toast.makeText(
                                    this@RegisterActivity, "You are regisetred succesfully!",
                                    Toast.LENGTH_SHORT
                                ).show()

                                val intent = Intent(this, PatientListActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra("user_id", firebaseUser.uid)
                                intent.putExtra("email_id", email)
                                intent.putExtra("username", username)
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
                        })




                            }

                        }





                }
            }


       }


