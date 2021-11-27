package com.example.cpsproject

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.actionCodeSettings
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_add_clinician.*

//commento
class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_clinician)





        btnSubmitdata.setOnClickListener {
            if (etUsername.text.trim().isNotEmpty()||etPassword1.text.trim().isNotEmpty()|| etPassword2.text.trim().isNotEmpty()|| etEmail.text.trim().isNotEmpty()||etPassword1.text==etPassword2.text){
                val intent =Intent(this, PatientListActivity::class.java)
                startActivity(intent)


            } else if(etUsername.text.trim().isNotEmpty()||etPassword1.text.trim().isNotEmpty()|| etPassword2.text.trim().isNotEmpty()|| etEmail.text.trim().isNotEmpty()){
                    Toast.makeText(this,"Password does not match",Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(this,"Input required",Toast.LENGTH_LONG).show()
            }

            var etEmail= findViewById<EditText>(R.id.etEmail)
            var email=etEmail.text.toString()


                }

        }



    }


}
