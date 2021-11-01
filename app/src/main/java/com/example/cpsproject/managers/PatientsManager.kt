package com.example.cpsproject.managers

import com.example.cpsproject.model.Patient

object PatientsManager {
    public var patientsList: ArrayList<Patient> = ArrayList()

    public fun addPatient(patient: Patient, toString: String){
        patientsList.add(patient)
    }

}