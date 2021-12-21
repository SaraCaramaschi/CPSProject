package com.example.cpsproject.managers

import android.annotation.SuppressLint
import android.content.Context
import com.example.cpsproject.model.Patient
import com.google.firebase.database.*
import com.google.gson.Gson
import timber.log.Timber
import java.io.BufferedReader
import java.io.File


@SuppressLint("StaticFieldLeak")
object PatientsManager {
    var patientsList: ArrayList<Patient> = ArrayList()
    var patientsAllList: ArrayList<Patient> = ArrayList()
    var selectedPatient: Int? = null

    fun addPatient(patient: Patient, context: Context) {
        patientsList.add(patient)
        savePatient(patient, context)
    }

    //Saving in local storage
    fun savePatient(patient: Patient, context: Context) {
        val gson = Gson()
        val jsonPatient = gson.toJson(patient)

        Timber.d("json %s", jsonPatient)

        var folder = context.getDir("PatientsFolder", Context.MODE_PRIVATE)
        var fileName = folder.path.toString() + "/" + patient.taxcode + ".txt"
        var file = File(fileName) // cartella uguale ma con una roba in più
        val createdFile = file.createNewFile()

        file.writeText(jsonPatient)
        saveRealtimePatient(jsonPatient, patient, context)
    }


    // Saving new patient on realtime database
    fun saveRealtimePatient(jsonpatient: String, patient: Patient, context: Context) {

        val db: DatabaseReference
        db =
            FirebaseDatabase.getInstance("https://thinkpen-28d8a-default-rtdb.europe-west1.firebasedatabase.app")
                .getReference("Patients")

        var taxcode = patient.taxcode
        var folder = context.getDir("PatientsFolder", Context.MODE_PRIVATE)
        var fileName = folder.path.toString() + "/" + taxcode + ".txt"

        db.child(patient.taxcode.toString()).setValue(patient).addOnSuccessListener {
            File(fileName).delete()
        }
            .addOnFailureListener {
                Timber.d("Error filed to add!")
                savePatient(patient, context)
            }
    }

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

    fun checkPatientLocal(context: Context) {
        var folder =
            context.getDir("PatientsFolder", Context.MODE_PRIVATE)

        if (!folder.listFiles().isEmpty()) {
            File(context.getDir("PatientsFolder", Context.MODE_PRIVATE).path).walk().forEach {
                //Timber.d(it.path)
                if (it.isFile) {
                    val pat = readPatientJson(it, context)
                    //val gson = Gson()
                    //var jsonPat = gson.toJson(pat)
                    var fileNameCheck = folder.path.toString() + "/" + pat.taxcode + ".txt"

                    //elimino da database
                    val db: DatabaseReference
                    db =
                        FirebaseDatabase.getInstance("https://thinkpen-28d8a-default-rtdb.europe-west1.firebasedatabase.app")
                            .getReference("Patients")

                    db.child(pat.taxcode.toString()).setValue(pat).addOnSuccessListener {
                        //     Timber.d("Record added succesfully!")
                        File(fileNameCheck).delete()
                        //   Timber.d("File deleted")
                    }
                        .addOnFailureListener {
                            Timber.d("Error filed to add!")
                        }
                }
            }
        }
    }

    // Reading from realtime database
    fun getDocumentsPatient(context: Context, ID: String): ArrayList<Patient> {
        val db: DatabaseReference =
            FirebaseDatabase.getInstance("https://thinkpen-28d8a-default-rtdb.europe-west1.firebasedatabase.app")
                .getReference("Patients")
        val myPatientsList: ArrayList<Patient> = ArrayList()
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var i = 0
                if (snapshot.exists()) {
                    for (patientsSnapshot in snapshot.children) {
                        var patientNew = patientsSnapshot.getValue(Patient::class.java)
                        Timber.d("patient new %s", patientNew.toString())
                        Timber.d("snapshot %s", patientsSnapshot.toString())

                        if (patientNew != null && !myPatientsList.contains(patientNew) && patientNew.cliniciansID.contains(
                                ID
                            )
                        ) {
                            Timber.d("found my patient! %s", i)
                            myPatientsList.add(patientNew)
                            i++

                        }

                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Timber.d("cancelled")
            }
        }
        )
        return myPatientsList
    }

    // Reading all patients from realtime database
    fun getDocumentsAllPatient(context: Context, ID: String): ArrayList<Patient> {

        val db: DatabaseReference =
            FirebaseDatabase.getInstance("https://thinkpen-28d8a-default-rtdb.europe-west1.firebasedatabase.app")
                .getReference("Patients")

        val patientsListAll: ArrayList<Patient> = ArrayList()
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (patientsSnapshot in snapshot.children) {
                        var patientNew = patientsSnapshot.getValue(Patient::class.java)
                        if (patientNew != null && !patientsListAll.contains(patientNew) && !patientNew.cliniciansID.contains(
                                ID
                            )
                        ) {
                            patientsListAll.add(patientNew)
                        }
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Timber.d("patient cancelled")
            }
        }
        )
        return patientsListAll
    }

    //Delete patient
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

        //Realtime database delete
        val db: DatabaseReference
        db =
            FirebaseDatabase.getInstance("https://thinkpen-28d8a-default-rtdb.europe-west1.firebasedatabase.app")
                .getReference("Patients")
        db.child(patientDeleted.taxcode.toString()).removeValue().addOnSuccessListener {
            Timber.d("Deleted")
        }.addOnFailureListener {
            Timber.d("Not deleted")
        }
    }
}