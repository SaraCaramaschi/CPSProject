package com.example.cpsproject

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cpsproject.databinding.ActivityAddPatientBinding
import com.example.cpsproject.managers.PatientsManager
import com.example.cpsproject.managers.PatientsManager.patientsList
import com.example.cpsproject.model.Patient
//import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_add_patient.*
//import kotlinx.serialization.encodeToString
import org.jetbrains.anko.toast
import timber.log.Timber

//import kotlinx.serialization.Serializable
//import kotlinx.serialization.json.Json
import java.io.*
import java.lang.Exception


class AddPatientActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddPatientBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_patient)
        val name= intent.getStringExtra("name")
        val phase= intent.getStringExtra("phase")
        //aggiungere lo stesso per la foto se la vogliamo

binding.nameProfile.text= name
binding.phaseProfile.text= phase
//TODO perchè sono rossi????


// CAPIRE COSA FARE NELLA ACTIVITY PER GLI SPINNER
        // val gender: Gender
        //spinnerGender.adapter=ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,gender)
        //ArrayAdapter<CharSequence>() adapter=Arrayadapter.createfrom


        // Activity related to the button add patient, notifica OK !!!
        btnAddPat.setOnClickListener {
            if (etName.text.toString().trim().isEmpty()) {
                Toast.makeText(this, "Name required", Toast.LENGTH_SHORT).show();
                //etName.error = "Name Required";
                return@setOnClickListener
            } else if (etSurname.text.toString().trim().isEmpty()) {
                etSurname.error = "Surname Required"
                return@setOnClickListener
            } else if (etTax.text.toString().trim().isEmpty()) {
                etTax.error = "Tax Code Required"
                return@setOnClickListener
            } else if (etTax.text.toString().trim().length != 16) {
                etTax.error = "Tax Code not correct"
                return@setOnClickListener
            }


            // Add patient
            PatientsManager.addPatient( // ora ci sono 4 input (anche nella data class)
                Patient(
                    etName.text.toString(), etSurname.text.toString(),
                    etNotes.text.toString(), etTax.text.toString()
                )
            )

            //Create Json file
            PatientsManager.createJson(
                Patient(
                    etName.text.toString(), etSurname.text.toString(),
                    etNotes.text.toString(), etTax.text.toString()
                )
            )


            //TODO NON SO SE VA MESSO QUI O IN PATIENTSMANAGER (E MOLTI ALTRI DUBBI)
            // PARTE DI GINEVRA TRASPORTATA IN PATIENT MANAGER IN FUNZIONE CREATEJSON
            /*var newpatient = patientsList.last()
        val gson= Gson()
        //AGGIUNGO VARIABILE NEWPATIENT O CONSIDERO TUTTA LA LISTA?: val jsonList= gson.toJson(PatientsManager.patientsList, new Filewriter(JsonList))
        val jsonList= gson.toJson(newpatient)

        //COME CREO FILE IN CUI SALVARE IL JSON? HO CREATO PACKAGE IN CPSPROJECT
        File("JsonFiles").writeText(jsonList)
        */

            /*Timber.d(PatientsManager.patientsList.elementAt(0).name)
        Timber.d(PatientsManager.patientsList.elementAt(0).surname)
        Timber.d(PatientsManager.patientsList.elementAt(0).notes)
        Timber.d(PatientsManager.patientsList.elementAt(0).taxcode)
         */
        }

    }
}

// https://www.youtube.com/watch?v=y4npeX35B34 TOP VIDEOOOOOOOOOOOOOOOOOO, si ma per firebase
