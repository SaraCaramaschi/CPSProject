package com.example.cpsproject.managers

import android.content.Context
import com.example.cpsproject.model.Patient
import com.google.gson.Gson
import timber.log.Timber
import java.io.*



object PatientsManager {
    public var patientsList: ArrayList<Patient> = ArrayList()

    public fun addPatient(patient: Patient, context: Context) {
        patientsList.add(patient)
        savePatient(patient, context)
    }

    public fun savePatient(patient: Patient, context: Context) {
        val gson= Gson()
        val jsonPatient= gson.toJson(patient)

        Timber.d("json %s", jsonPatient)


        var fileName = context.filesDir.path.toString() + "/" + patient.taxcode + ".txt"
        var file = File(fileName) // cartella uguale ma con una roba in più

        val createdFile = file.createNewFile()
        Timber.d("Il filename e': %s",fileName)
        Timber.d("the file is created %s", createdFile)
        Timber.d("path %s", file.absolutePath)

        file.writeText(jsonPatient)
        Timber.d("questo è il file lettooo %s", readPatient(fileName))
    }

    public fun readPatient(fileName : String) : String {
        // la funzione readPatient funziona! quindi il file viene creato e salvato da qualche parte veramente
        // il nome del file è per esempio taxcode, ma al suo interno ha tutti i dati del json. perfetto
        return File(fileName).readText(Charsets.UTF_8)
    }

    public fun readLastPatient() {
        var lastPatient = patientsList.last()
        var taxcode = lastPatient.taxcode
        var file = File(taxcode)
        var jsonText=file.readText()
        val gson= Gson()
        var patientNew= gson.fromJson(jsonText, Patient::class.java)
        Timber.d("questo è il file lettooo %s", patientNew.name)

        //ottengo json da file OK
        //encode json in classe Patient
        //stampa tutti i campi
        //chiamo in un modo la classe poi faccio Timber.d( nomedellaclasse.nome + ...)
    }
}




//<<<<<<< Updated upstream
//=======
//
//
//
//
//>>>>>>> Stashed changes

