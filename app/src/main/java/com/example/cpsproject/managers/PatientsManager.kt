package com.example.cpsproject.managers

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.example.cpsproject.model.Patient
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_add_patient.*

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import timber.log.Timber
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
        Timber.d("This is the json data: $json") // NON LO STAMPA

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
        /*try{
            // ERRORE: java.io.FileNotFoundException: /Users/saracaramaschi/SecondoAnnoM/paziente.js (No such file or directory)
            val br = BufferedWriter(FileWriter(filepath))
            br.write(json)
            br.close()
        }catch (e: Exception){
            e.printStackTrace()
        }*/

        //METODO 4:
        // https://medium.com/android-news/android-saving-model-object-in-shared-preferences-ce3c1d4f4573
        //Shared Preference field used to save and retrieve JSON string
        lateinit var preferences: SharedPreferences

        //Name of Shared Preference file
        private const val PREFERENCES_FILE_NAME = "PREFERENCES_FILE_NAME"

        /**
         * Call this first before retrieving or saving object.
         * @param application Instance of application class
         */
        fun with(application: Application) {
            preferences = application.getSharedPreferences(
                PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
        }

        /**
         * Saves object into the Preferences.
         *
         * @param `object` Object of model class (of type [T]) to save
         * @param key Key with which Shared preferences to
         **/
        fun <Patient> put(`object`: com.example.cpsproject.model.Patient, key: String) {
            //Convert object to JSON String.
            val jsonString = GsonBuilder().create().toJson(`object`)
            //Save that String in SharedPreferences
            preferences.edit().putString(key, jsonString).apply()
        }



    }

}