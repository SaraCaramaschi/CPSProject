package com.example.cpsproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.cpsproject.managers.PatientsManager
import com.example.cpsproject.model.Patient
import kotlinx.android.synthetic.main.activity_add_patient.*


class AddPatientActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_patient)

        val name = intent.getStringExtra("name")
        val phase = intent.getStringExtra("phase")
        //aggiungere lo stesso per la foto se la vogliamo

        // TODO spinner
        // val gender: Gender
        //spinnerGender.adapter=ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,gender)
        //ArrayAdapter<CharSequence>() adapter=Arrayadapter.createfrom

        // Activity related to the button add patient, notifica OK !!!
        val btnAddPat = findViewById<Button>(R.id.btnAddPat)
        btnAddPat.setOnClickListener {
            // TODO togliere commenti vincoli
//            if (etName.text.toString().trim().isEmpty()) {
//                //Toast.makeText(this, "Name required", Toast.LENGTH_SHORT).show();
//                etName.error = "Name Required";
//                return@setOnClickListener
//            } else if (etSurname.text.toString().trim().isEmpty()) {
//                etSurname.error = "Surname Required"
//                return@setOnClickListener
//            } else if (etTax.text.toString().trim().isEmpty()) {
//                etTax.error = "Tax Code Required"
//                return@setOnClickListener
//            } else if (etTax.text.toString().trim().length != 16) {
//                etTax.error = "Tax Code not correct"
//                return@setOnClickListener
//            } else if (etBirthDate.text.toString().trim().length != 10) {
//                etBirthDate.error = "Birth Date not correct"
//                return@setOnClickListener
//            }

            // Variabile patient
            var patient = Patient( etName.text.toString(), etSurname.text.toString(),
            etNotes.text.toString(), etTax.text.toString(), etBirthDate.text.toString() ) // Local variable

            // Add patient to patientlist
            PatientsManager.addPatient(patient, applicationContext)

            // Go to patient page
            val intent = Intent(this, PatientPageActivity::class.java)
            intent.putExtra("keyPatient", patient)
            startActivity(intent)
        }

       btnReadPat.setOnClickListener {
            PatientsManager.readPatient(0, applicationContext)
           //PatientsManager.readPatient()
        }

        btnPatListBack.setOnClickListener {
            val intent = Intent(this, PatientListActivity::class.java)
            startActivity(intent)
        }

    }
}


// https://www.youtube.com/watch?v=y4npeX35B34 TOP VIDEOOOOOOOOOOOOOOOOOO, si ma per firebase
