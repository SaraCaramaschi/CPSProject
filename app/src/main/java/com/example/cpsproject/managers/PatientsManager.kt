package com.example.cpsproject.managers

import android.content.Context
import android.os.Bundle
import com.example.cpsproject.model.Patient
import com.google.gson.Gson
import timber.log.Timber
import java.io.*
import android.os.Environment
import android.util.Log
import android.util.Log.d
import java.io.File.separator
import android.widget.Toast






object PatientsManager {
    public var patientsList: ArrayList<Patient> = ArrayList()


    public fun addPatient(patient: Patient, context: Context) {
        patientsList.add(patient)
        savePatient(patient, context)
    }

    public fun savePatient(patient: Patient, context: Context) {
        val gson= Gson()
        val jsonPatient= gson.toJson(patient)
        //val folder_main = "NewFolder"

        Timber.d("json %s", jsonPatient)

        //CREATE NEW DIRECOTY IN FILESDIR DIRECOTY (https://developer.android.com/training/data-storage/app-specific#kotlin)
        // anche se volevo metterlo da un'altra parte
        var folder = context.getDir("PatientsFolder", Context.MODE_PRIVATE)
        Timber.d("questo è nuova cartella: %s", folder.path.toString())

        //var fileName = context.filesDir.path.toString() + "/" + patient.taxcode + ".txt" (Chiara)
        var fileName = folder.path.toString() + "/" + patient.taxcode + ".txt"
        var file = File(fileName) // cartella uguale ma con una roba in più

        val createdFile = file.createNewFile()
        Timber.d("Il filename e': %s",fileName)
        Timber.d("the file is created %s", createdFile)
        Timber.d("path %s", file.absolutePath)

        file.writeText(jsonPatient)
       // Timber.d("questo è il file lettooo %s", readPatient(fileName))
    }


    public fun readPatient(i:Int, context: Context) { // FUNZIONA !!!
        var lastPatient = patientsList[i]
        var folder = context.getDir("PatientsFolder", Context.MODE_PRIVATE)         // ripresi da savePatient
        var fileName = folder.path.toString() + "/" + lastPatient.taxcode + ".txt"  // ripresi da savePatient

        //Creating a new Gson object to read data
        var gson = Gson()
        //Read the PostJSON.json file
        val bufferedReader: BufferedReader = File(fileName).bufferedReader()
        // Read the text from buffferReader and store in String variable
        val inputString = bufferedReader.use { it.readText() }

        //Convert the Json File to Gson Object
        var patient = gson.fromJson(inputString, Patient::class.java)

        //Initialize the String Builder
        var stringBuilder = StringBuilder("Patient Details\n---------------------")

        stringBuilder?.append("\nName: " + patient.name.toString())
        stringBuilder?.append(("\nCognome: " + patient.surname.toString()))
        stringBuilder?.append(("\nCodice fiscale: " + patient.taxcode.toString()))
        stringBuilder?.append(("\nNote: " + patient.notes.toString()))
        stringBuilder?.append(("\nCompleanno: " + patient.birthdate.toString()))

        //Display the all Json object in text View
        Timber.d("Stringbuilder: %s", stringBuilder.toString())
    }
}


