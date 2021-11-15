package com.example.cpsproject.managers

import android.annotation.SuppressLint
import android.content.Context
import com.example.cpsproject.model.Patient
import com.google.gson.Gson
import timber.log.Timber
import java.io.*
import com.example.cpsproject.PatientAdapter


@SuppressLint("StaticFieldLeak")
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


    //DOMANDA: in teoria non serve più giusto?
    public fun readPatient(i:Int, context: Context): Patient { // FUNZIONA !!!
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

        return patient
    }

    //Da json a data class
    fun readPatientJson(file : File, context: Context) : Patient {
        //Creating a new Gson object to read data
        var gson = Gson()
        //Read the PostJSON.json file
        val bufferedReader: BufferedReader = file.bufferedReader()
        // Read the text from buffferReader and store in String variable
        val inputString = bufferedReader.use { it.readText() }

        //Convert the Json File to Gson Object
        var patient = gson.fromJson(inputString, Patient::class.java)
        return patient
    }

    // Aggiorna lista pazienti
    fun  importPatientList(context: Context): ArrayList<Patient> {
        Timber.d("Dentro a IMPORTPATIENTLIST") // Non lo stampa mai :(

        var folder = context.getDir("PatientsFolder", Context.MODE_PRIVATE)
        if (folder.listFiles().isEmpty()) {
            Timber.d("EMPTY FOLDER")
            return ArrayList()
        }

        File(context.getDir("PatientsFolder", Context.MODE_PRIVATE).path).walk().forEach {
            Timber.d(it.path)
            if (it.isFile) {
                val pat = readPatientJson(it, context)
                if(!patientsList.contains(pat)) {
                    patientsList.add(pat)
                }

            }
        }

//        context.getDir("PatientsFolder", Context.MODE_PRIVATE).walk().forEach {
//            Timber.d(it.path)
//            //patientsList.add(readPatientJson(it.absoluteFile, context))
//        }
//        var patList: ArrayList<Patient> = ArrayList()
//        Timber.d(patientsList.indices.toString())
//
//        for (i in patientsList.indices) { //TODO entrare nel folder e passare file
//            var patientNew = readPatient(i, context)
//            patList.add(patientNew)
//        }
        return patientsList
    }

    fun deletePatient(context: Context, i: Int){
        var lastPatient = patientsList[i]
        var folder = context.getDir("PatientsFolder", Context.MODE_PRIVATE)
        var fileName = folder.path.toString() + "/" + lastPatient.taxcode + ".txt"
        File(fileName).delete()
        Timber.d("File deleted")


    }
}


