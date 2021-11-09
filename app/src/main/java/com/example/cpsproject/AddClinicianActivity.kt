package com.example.cpsproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cpsproject.ble.MainConnection
import kotlinx.android.synthetic.main.activity_add_clinician.*
import layout.addpatient2

class AddClinicianActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_clinician)


        btnSubmitdata.setOnClickListener {
            if (etUsername.text.trim().isNotEmpty()||etPassword1.text.trim().isNotEmpty()|| etPassword2.text.trim().isNotEmpty()|| etEmail.text.trim().isNotEmpty()||etPassword1.text==etPassword2.text){
                val intent =Intent(this, PatientsListActivity::class.java)
                startActivity(intent)


            } else if(etUsername.text.trim().isNotEmpty()||etPassword1.text.trim().isNotEmpty()|| etPassword2.text.trim().isNotEmpty()|| etEmail.text.trim().isNotEmpty()){
                    Toast.makeText(this,"Password does not match",Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(this,"Input required",Toast.LENGTH_LONG).show()
            }

        }

    }
}
