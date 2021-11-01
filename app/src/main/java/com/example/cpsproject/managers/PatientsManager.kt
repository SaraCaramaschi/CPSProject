package com.example.cpsproject.managers

import com.example.cpsproject.model.Patient
import com.google.gson.Gson

object PatientsManager {
    public var patientsList: ArrayList<Patient> = ArrayList()

    public fun addPatient(patient: Patient){
        patientsList.add(patient)
    }

    //Provo a mettere qui la conversione della patient list in json

    // val jsonList= Gson().toJson(patientsList)

}