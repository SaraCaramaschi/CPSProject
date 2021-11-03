package com.example.cpsproject

import com.example.cpsproject.model.Patient
import com.example.cpsproject.managers.PatientsManager.patientsList
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class JsonCreator {

    }
    public fun createJson() {
        for (i in patientsList.indices) {
            val json = Json.encodeToString(patientsList[i])

            //scrivere in file l'i-esimo paziente
        }
}