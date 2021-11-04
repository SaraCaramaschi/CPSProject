package com.example.cpsproject.managers

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.cpsproject.model.Patient
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_add_patient.*

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
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

        Timber.d("the file is created %s", createdFile)
        Timber.d("path %s", file.absolutePath)

        file.writeText(jsonPatient)

        Timber.d("questo è il file lettooo %s", readPatient(fileName))
    }

    public fun readPatient(fileName : String) : String {
        return File(fileName).readText(Charsets.UTF_8)
    }

    public fun readLastPatient() {
        var lastPatient = patientsList.last()
        var taxcode = lastPatient.taxcode

        var file = File(taxcode)

        //ottengo json da file
        //encode json in classe Patient
        //stampa tutti i campi

        //chiamo in un modo la classe poi faccio Timber.d( nomedellaclasse.nome + ...)
    }
}


/*
    public fun createJson(patient: Patient) {
        val json = Json.encodeToString(patient)
        //Log.d("This is the json data:", json) // LO STAMPA OK SUPER !
    }


        val file = File(System.getProperty("user.home"), "paziente.js")
        var fileName = "paziente.js"
        var fileDirectory = "/Users/saracaramaschi/SecondoAnnoM/"
        var filepath = "/Users/saracaramaschi/SecondoAnnoM/paziente.js"

*/


/* QUESTA ERA PARTE DI UNA GINEVRA ILLUSA CHE POTESSE ESSERE COSIì SEMPLICE
    val gson= Gson()
    val jsonList= gson.toJson(patientsList)
    File("File.json").writeText(jsonList)
*/

        //PER FARE FILE


    //METODO 1: NON VA, questo penso proprio non vada bene
            /*
            var newfile = File(fileDirectory, fileName)
            newfile.writeText(json) // UTF-8 (default)*/

            //METODO 2: NON VA
        /*
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

    //METODO non so quale: NON VA
       // file.bufferedWriter().use { bw -> bw.write(json) }

        //METODO 4: non va
        // https://medium.com/android-news/android-saving-model-object-in-shared-preferences-ce3c1d4f4573
        //Shared Preference field used to save and retrieve JSON string
        /*lateinit var preferences: SharedPreferences

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
*/

        // altro metodo
        /*FileOutputStream(file).use { fos ->
            OutputStreamWriter(fos, Charsets.UTF_8).use { osw ->
                BufferedWriter(osw).use { bf -> bf.write(json) }
            }
        }*/





