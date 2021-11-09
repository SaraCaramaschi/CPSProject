package com.example.cpsproject

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cpsproject.databinding.ActivityPatientBinding

//qui nel video c'era un import ma non diceva di cosa

class PatientActivity:AppCompatActivity(){
 private lateinit var binding:ActivityPatientBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient)
            val name= intent.getStringExtra("name")
            val phase= intent.getStringExtra("phase")
            //aggiungere lo stesso per la foto se la vogliamo

        binding.PatientName.text= name
        binding.PatientPhase.text= phase
    }
}