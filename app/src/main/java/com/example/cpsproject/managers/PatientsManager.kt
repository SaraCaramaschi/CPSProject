package com.example.cpsproject.managers

import com.example.cpsproject.model.Patient
import com.google.gson.Gson

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File


object PatientsManager {
    public var patientsList: ArrayList<Patient> = ArrayList()

    public fun addPatient(patient: Patient){
        patientsList.add(patient)
    }

    /*public fun createJson(patient: Patient) {
        val json = Json.encodeToString(patient)

        var filename: String = "/Users/saracaramaschi/paziente"
        File(filename).writeText(json) // UTF-8 (default)
    }*/

    //Provo a mettere qui la conversione della patient list in json

    // val jsonList= Gson().toJson(patientsList)

}