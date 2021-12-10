package com.example.cpsproject.managers

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.cpsproject.model.Clinician
import com.example.cpsproject.model.Patient
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import timber.log.Timber
import java.io.BufferedReader
import java.io.File
import kotlin.collections.contains as contains1

object ClinicianManager {

    public var clinicianList: ArrayList<Clinician> = ArrayList()

    //public var selectedPatient: Int?=null
    var email: String = String()
    var password: String = String()
    var username: String = String()

    fun addClinician(clinician: Clinician, context: Context) {
        clinicianList.add(clinician)
        saveRealtimeClinician(clinician, context)

    }

    //salva in locale
    fun saveClinician(clinician: Clinician, context: Context) {
        val gson = Gson()
        val jsonClinician = gson.toJson(clinician)

        Timber.d("json %s", jsonClinician)

        //CREATE NEW DIRECOTY IN FILESDIR DIRECOTY (https://developer.android.com/training/data-storage/app-specific#kotlin)

        var folder = context.getDir("CliniciansFolder", Context.MODE_PRIVATE)
        Timber.d("questo è nuova cartella: %s", folder.path.toString())

        //var fileName = context.filesDir.path.toString() + "/" + patient.taxcode + ".txt" (Chiara)
        var fileName = folder.path.toString() + "/" + clinician.email + ".txt"
        var file = File(fileName) // cartella uguale ma con una roba in più

        val createdFile = file.createNewFile()
        Timber.d("Il filename e': %s", fileName)
        Timber.d("the file is created %s", createdFile)
        Timber.d("path %s", file.absolutePath)

        file.writeText(jsonClinician)
        //saveFireStore(patient, context)

    }


    // Funzione che salva nuovo paziente su firestore
    fun saveRealtimeClinician(clinician: Clinician, context: Context) {
        val gson = Gson()
        val jsonclinician = gson.toJson(clinician)
        val db: DatabaseReference
        db= FirebaseDatabase.getInstance( "https://thinkpen-28d8a-default-rtdb.europe-west1.firebasedatabase.app").getReference("Clinicians")

        var email = clinician.email
        var folder = context.getDir("CliniciansFolder", Context.MODE_PRIVATE)
        var fileName = folder.path.toString() + "/" + email + ".txt"

        db.child(clinician.email).setValue(clinician).addOnSuccessListener {
            Timber.d("Record added succesfully!")
            File(fileName).delete()
            Timber.d("File deleted")
        }
            .addOnFailureListener{
                Timber.d( "Error filed to add!")
                //TODO CODICE PER SALVARE IN LOCALE SE QUALOCSA VA STORTO--> verificare se funziona
                saveClinician(clinician, context)
            }

        //TODO SALVARE IN DATABSE FILE RIMASTI IN LOCALE
    }
/*
        //Da json a data class OK
        fun readClinicianJson(file: File, context: Context): Clinician {
            //Creating a new Gson object to read data
            var gson = Gson()
            //Read the PostJSON.json file
            val bufferedReader: BufferedReader = file.bufferedReader()
            // Read the text from buffferReader and store in String variable
            val inputString = bufferedReader.use { it.readText() }

            //Convert the Json File to Gson Object
            var clinician = gson.fromJson(inputString, Clinician::class.java)
            return clinician
        }
*/

        // Funzione per leggere documenti da realtime database
        fun getDocumentsClinician(context: Context): ArrayList<Clinician> {
            // [START get_document]
            val db: DatabaseReference=FirebaseDatabase.getInstance("https://thinkpen-28d8a-default-rtdb.europe-west1.firebasedatabase.app").getReference("Clinicians")
            //val dbPatients=db.child("Patients")
            db.addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot){
                    if(snapshot.exists()){
                        for (clinicianSnapshot in snapshot.children){
                            var clinicianNew=clinicianSnapshot.getValue(Clinician::class.java)
                            if (clinicianNew != null && !clinicianList.contains(clinicianNew)) {
                                clinicianList.add(clinicianNew)
                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            }
            )
            return clinicianList
        }


        //Funzione elimina paziente ma solo in locale
        fun deleteClinician(context: Context, i: Int) {
            var clinicianDeleted = clinicianList[i]
            clinicianList.remove(clinicianDeleted)
            var folder = context.getDir("CliniciansFolder", Context.MODE_PRIVATE)
            var fileName = folder.path.toString() + "/" + clinicianDeleted.email + ".txt"
            File(fileName).delete()
            Timber.d("File deleted")
            var fodername = folder.path.toString()

            if (!File(fodername).list().contains1(fileName)) {
                Timber.d("File has been really deleted") //LO STAMPA! JSON LO ELIMINA, DOBBIAMO RIAGGIORNARE PAZIENTI IN KOTLIN
            }

            //Firestore delete
            val db = Firebase.firestore
            val cliniciansRef = db.collection("patients")
            //cerco paziente con quel taxcode
            val queryTaxCode = cliniciansRef.whereEqualTo("email", "${clinicianDeleted.email}")
            queryTaxCode.get().addOnSuccessListener { result ->

                if (result != null) {
                    val document = result.documents
                    document.forEach {
                        //val patient=it.toObject(Patient::class.java)
                        //if (patient != null){
                        var id = it.id
                        cliniciansRef.document(id).delete().addOnSuccessListener {
                            Timber.d("Deleted")
                        }
                            .addOnFailureListener {
                                Timber.d("Not deleted")
                            }
                    }
                } else {
                    Timber.d("No such document")
                }
            }
        }
    }





