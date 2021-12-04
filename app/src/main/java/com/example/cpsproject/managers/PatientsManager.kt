package com.example.cpsproject.managers

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.example.cpsproject.model.Patient
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import timber.log.Timber
import java.io.*


@SuppressLint("StaticFieldLeak")
object PatientsManager {
    public var patientsList: ArrayList<Patient> = ArrayList()

    public fun addPatient(patient: Patient, context: Context) {
        patientsList.add(patient)
        savePatient(patient, context)
    }

    public fun savePatient(patient: Patient, context: Context) {
        val gson = Gson()
        val jsonPatient = gson.toJson(patient)
        val db = Firebase.firestore

        Timber.d("json %s", jsonPatient)

        //CREATE NEW DIRECOTY IN FILESDIR DIRECOTY (https://developer.android.com/training/data-storage/app-specific#kotlin)

        var folder = context.getDir("PatientsFolder", Context.MODE_PRIVATE)
        Timber.d("questo è nuova cartella: %s", folder.path.toString())

        //var fileName = context.filesDir.path.toString() + "/" + patient.taxcode + ".txt" (Chiara)
        var fileName = folder.path.toString() + "/" + patient.taxcode + ".txt"
        var file = File(fileName) // cartella uguale ma con una roba in più

        val createdFile = file.createNewFile()
        Timber.d("Il filename e': %s", fileName)
        Timber.d("the file is created %s", createdFile)
        Timber.d("path %s", file.absolutePath)

        file.writeText(jsonPatient)
        // Timber.d("questo è il file lettooo %s", readPatient(fileName))

        //SALVATAGGIO FIREBASE MA NON FUNZIONA (SEGUITO ISTRUZIONI KOTLIN)
//    db.collection("patients")
//            .add(jsonPatient)
//            .addOnSuccessListener { documentReference ->
//                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
//            }
//            .addOnFailureListener { e ->
//                Log.w(TAG, "Error adding document", e)
//            }

        saveFireStore(jsonPatient, patient, context)


    }

    public fun saveFireStore(jsonpatient: String, patient: Patient, context: Context) {
        val dbn = FirebaseFirestore.getInstance()
        var mappatient: Map<String, Any> = HashMap()
        mappatient = Gson().fromJson(jsonpatient, mappatient.javaClass)
        var taxcode = patient.taxcode
        var folder = context.getDir("PatientsFolder", Context.MODE_PRIVATE)
        var fileName = folder.path.toString() + "/" + taxcode + ".txt"


        dbn.collection("patients")
            .add(mappatient)
            .addOnSuccessListener {
                //Elimino locale
                Log.d(TAG, "Record added succesfully")
                File(fileName).delete()
                Timber.d("File deleted")
                //TODO CODICE PER VEDERE SE IN LOCALE CI SONO ALTRI FILE DA CARICARE
                var folder = context.getDir("PatientsFolder", Context.MODE_PRIVATE)

            }

            .addOnFailureListener { e ->
                Log.w(TAG, "Error filed to add", e)
                //TODO CODICE PER SALVARE IN LOCALE
            }

    }


    //DOMANDA: in teoria non serve più giusto?
    public fun readPatient(i: Int, context: Context): Patient { // FUNZIONA !!!
        var lastPatient = patientsList[i]
        var folder =
            context.getDir("PatientsFolder", Context.MODE_PRIVATE)         // ripresi da savePatient
        var fileName =
            folder.path.toString() + "/" + lastPatient.taxcode + ".txt"  // ripresi da savePatient

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
    fun readPatientJson(file: File, context: Context): Patient {
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

    // Aggiorna lista pazienti in locale
    fun importPatientList(context: Context): ArrayList<Patient> {
        Timber.d("Dentro a IMPORTPATIENTLIST")

        var folder = context.getDir("PatientsFolder", Context.MODE_PRIVATE)
        if (folder.listFiles().isEmpty()) {
            Timber.d("EMPTY FOLDER")
            return ArrayList()
        }

        File(context.getDir("PatientsFolder", Context.MODE_PRIVATE).path).walk().forEach {
            Timber.d(it.path)
            if (it.isFile) {
                val pat = readPatientJson(it, context)
                if (!patientsList.contains(pat)) {
                    patientsList.add(pat)
                }

            }
        }

        return patientsList
    }

//TODO FUNZIONE PER LEGGERE DOCUMENTI DA FIRESTORE
    /*
    fun getDocuments(context: Context): ArrayList<Patient> {
        // [START get_document]

        val db = Firebase.firestore

        val docRef = db.collection("patients")
        docRef.get().addOnSuccessListener { result ->
            for (document in result) {
                Log.d(TAG, "${document.id}=>${document.data}")
                var dbPatient = document.toObject(Patient::class.java)
                if (!patientsList.contains(dbPatient)) {
                    patientsList.add(dbPatient)
                }


            }
        }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting document", exception)

            }
        return patientsList
    }*/




    fun deletePatient(context: Context, i: Int) {
        var patientDeleted = patientsList[i]
        patientsList.remove(patientDeleted)
        var folder = context.getDir("PatientsFolder", Context.MODE_PRIVATE)
        var fileName = folder.path.toString() + "/" + patientDeleted.taxcode + ".txt"
        File(fileName).delete()
        Timber.d("File deleted")
        var fodername = folder.path.toString()

        if (!File(fodername).list().contains(fileName)) {
            Timber.d("File has been really deleted") //LO STAMPA! JSON LO ELIMINA, DOBBIAMO RIAGGIORNARE PAZIENTI IN KOTLIN
        }

    }
}





