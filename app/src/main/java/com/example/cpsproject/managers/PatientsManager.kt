package com.example.cpsproject.managers

import com.example.cpsproject.model.Patient
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_add_patient.*

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter


object PatientsManager {
    public var patientsList: ArrayList<Patient> = ArrayList()

    public fun addPatient(patient: Patient){
        patientsList.add(patient)
    }

    public fun createJson(patient: Patient) {
        val json = Json.encodeToString(patient)

        var fileName = "paziente.js"
        var fileDirectory = "/Users/saracaramaschi/SecondoAnnoM/"
        var filepath = "/Users/saracaramaschi/SecondoAnnoM/paziente.js"
/*
            METODO 1: NON VA
            var newfile = File(fileDirectory, fileName)
            newfile.writeText(json) // UTF-8 (default)

            METODO 2: NON VA
            val file = File(fileDirectory, fileName)
            val fileOutputStream: FileOutputStream
            try {
                fileOutputStream = openFileOutput(filepath, MODE_PRIVATE) // sbagliato qui attenzione: filepath non è un path
                fileOutputStream.write(json.toByteArray())
                fileOutputStream.close()

            }catch (e: Exception){
                e.printStackTrace()
            }
            Toast.makeText(applicationContext,"data save",Toast.LENGTH_LONG).show()
            */

        // METODO 3: PURE NON VA
        try{
            // ERRORE: java.io.FileNotFoundException: /Users/saracaramaschi/SecondoAnnoM/paziente.js (No such file or directory)
            val br = BufferedWriter(FileWriter(filepath))
            br.write(json)
            br.close()
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

}