package com.example.cpsproject

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cpsproject.databinding.ActivityAddPatientBinding
import com.example.cpsproject.managers.PatientsManager
import com.example.cpsproject.managers.PatientsManager.patientsList
import com.example.cpsproject.model.Patient
import kotlinx.android.synthetic.main.activity_add_patient.*


class AddPatientActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
<<<<<<< HEAD
       super.onCreate(savedInstanceState)
       setContentView(R.layout.activity_add_patient)

=======
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_patient)
        val name = intent.getStringExtra("name")
        val phase = intent.getStringExtra("phase")
        //aggiungere lo stesso per la foto se la vogliamo

//binding.nameProfile.text= name
//binding.phaseProfile.text= phase
//TODO perchè sono rossi????
>>>>>>> master


// CAPIRE COSA FARE NELLA ACTIVITY PER GLI SPINNER
        // val gender: Gender
        //spinnerGender.adapter=ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,gender)
        //ArrayAdapter<CharSequence>() adapter=Arrayadapter.createfrom


        // Activity related to the button add patient, notifica OK !!!
        btnAddPat.setOnClickListener {
            // TODO perchè sono a commento questi controlli? mi sembravano corretti
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


            // Add patient
            PatientsManager.addPatient( // ora ci sono 4 input (anche nella data class)
                Patient(
                    etName.text.toString(), etSurname.text.toString(),
                    etNotes.text.toString(), etTax.text.toString(), etBirthDate.text.toString(),
                ), applicationContext
            )




            //TODO NON SO SE VA MESSO QUI O IN PATIENTSMANAGER (E MOLTI ALTRI DUBBI)
            // PARTE DI GINEVRA TRASPORTATA IN PATIENT MANAGER IN FUNZIONE CREATEJSON


            /*Timber.d(PatientsManager.patientsList.elementAt(0).name)
    Timber.d(PatientsManager.patientsList.elementAt(0).surname)
    Timber.d(PatientsManager.patientsList.elementAt(0).notes)
    Timber.d(PatientsManager.patientsList.elementAt(0).taxcode)
     */
            val intent = Intent(this, PatientsListActivity::class.java)
            startActivity(intent)

        }

        btnReadPat.setOnClickListener {
            PatientsManager.readLastPatient()
        }

    }
}


// https://www.youtube.com/watch?v=y4npeX35B34 TOP VIDEOOOOOOOOOOOOOOOOOO, si ma per firebase
