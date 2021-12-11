package com.example.cpsproject.managers

import android.annotation.SuppressLint
import android.content.Context
import com.example.cpsproject.model.Patient
import com.google.firebase.database.*
import com.google.gson.Gson
import timber.log.Timber
import java.io.*
import java.lang.reflect.Array.get


@SuppressLint("StaticFieldLeak")
object PatientsManager {
    public var patientsList: ArrayList<Patient> = ArrayList()
    public var selectedPatient: Int?=null

    public fun addPatient(patient: Patient, context: Context) {
        patientsList.add(patient)
        savePatient(patient, context)
    }
//In locale --> OK
    public fun savePatient(patient: Patient, context: Context) {
        val gson = Gson()
        val jsonPatient = gson.toJson(patient)

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
        saveRealtimePatient(jsonPatient, patient, context)

    }


    // Funzione che salva nuovo paziente su realtime database-->OK
    fun saveRealtimePatient(jsonpatient: String, patient: Patient, context: Context) {
        val db: DatabaseReference
        db= FirebaseDatabase.getInstance( "https://thinkpen-28d8a-default-rtdb.europe-west1.firebasedatabase.app").getReference("Patients")
        //var mappatient: Map<String, Any> = HashMap()
        //mappatient = Gson().fromJson(jsonpatient, mappatient.javaClass)

        var taxcode = patient.taxcode
        var folder = context.getDir("PatientsFolder", Context.MODE_PRIVATE)
        var fileName = folder.path.toString() + "/" + taxcode + ".txt"

        db.child(patient.taxcode.toString()).setValue(patient).addOnSuccessListener {
            Timber.d("Record added succesfully!")
            File(fileName).delete()
            Timber.d("File deleted")
        }
            .addOnFailureListener{
                Timber.d( "Error filed to add!")
                //TODO CODICE PER SALVARE IN LOCALE SE QUALOCSA VA STORTO--> verificare se funziona
                savePatient(patient, context)
            }
  /*      dbn.collection("patients")
            .add(mappatient)
            .addOnSuccessListener {
                //ELIMINA LOCALE (ANCHE SE QUANDO AGGIUNGO PAZIETE SE AL PRIMO COLPO ME LO CARICA SU CLOUD
                //NON è NECESSARIO ELIMINARE LOCALE)--> FORSE DA TOGLIERE
                Timber.d("Record added succesfully")
                File(fileName).delete()
                Timber.d("File deleted")
            }

            .addOnFailureListener { e ->
                Timber.tag(TAG).w(e, "Error filed to add")
                //TODO CODICE PER SALVARE IN LOCALE SE QUALOCSA VA STORTO--> verificare se funziona
                savePatient(patient, context)
            }*/

        //TODO CODICE PER CARICARE FILE IN LOCALE
        //FORSE SAREBBE MEGLIO METTERE QUESTO PEZZO DI FUNZIONE OGNI VOLTA CHE CLINICO APRE L'APP(?)
        //COSì CHE NON SERVA CHE CARICHI UN NUOVO FILE PER CARICARE I PRECEDENTI
/*
        if (!folder.listFiles().isEmpty()) {
            File(context.getDir("PatientsFolder", Context.MODE_PRIVATE).path).walk().forEach {
                Timber.d(it.path)
                if (it.isFile) {
                    val pat = readPatientJson(it, context)
                    if (pat != patient) {
                        val gson = Gson()
                        var jsonPat = gson.toJson(pat)
                        var fileNameNew = folder.path.toString() + "/" + pat.taxcode + ".txt"
                        var mappatientNew: Map<String, Any> = HashMap()
                        mappatientNew = Gson().fromJson(jsonPat, mappatient.javaClass)
                        dbn.collection("patients")
                            .add(mappatientNew)
                            .addOnSuccessListener {
                                //Elimino locale
                                Timber.d("Record added succesfully")
                                File(fileNameNew).delete()
                                Timber.d("File deleted")

                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Error filed to add", e)
                                //TODO CODICE PER RISALVARE IN LOCALE--> verificare se funziona
                                savePatient(patient, context)
                            }
                    }
                }
            }
        }*/
    }
/*

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
    }*/
/*
    //Da json a data class OK
    fun readPatientDatabase(file: File, context: Context): Patient {
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

    // Aggiorna lista pazienti da locale OK--> ma non verrà più usata
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
                val pat = readPatientDatabase(it, context)
                if (!patientsList.contains(pat)) {
                    patientsList.add(pat)
                }

            }
        }

        return patientsList
    }
 */


    // Funzione per leggere documenti da realtime database-->OK TODO non mostra subito lista!
    fun getDocumentsPatient(context: Context): ArrayList<Patient> {

       /* val db = Firebase.firestore
        val docRef = db.collection("patients")
        docRef.get().addOnSuccessListener { result ->
            for (document in result) {
                if (document != null) {
                    Log.d(TAG, "${document.id}=>${document.data}")
                } else {
                    Timber.d("No such document")
                }
                var dbPatient = document.toObject(Patient::class.java)

                if (!patientsList.contains(dbPatient)) {
                    patientsList.add(dbPatient)
                }
            }
        }
            .addOnFailureListener { exception ->
                Timber.e(exception, "Error getting document")
            }*/

        val db: DatabaseReference=FirebaseDatabase.getInstance("https://thinkpen-28d8a-default-rtdb.europe-west1.firebasedatabase.app").getReference("Patients")
        //val dbPatients=db.child("Patients")
        val patientArrayList:ArrayList<Patient>
        db.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot){
                if(snapshot.exists()){
                    for (patientsSnapshot in snapshot.children){
                        var patientNew=patientsSnapshot.getValue(Patient::class.java)
                        if (patientNew != null&& !patientsList.contains(patientNew)) {
                            patientsList.add(patientNew)
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        }
        )
        return patientsList
    }


    // FUNZIONE SARA:
    /*
    private fun obtainIdentifier(): ArrayList<Patient>{
        var allPatients = ArrayList<Patient>()
        var db = Firebase.firestore.collection("patients")
        db.addSnapshotListener {
            snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Failure")
                return@addSnapshotListener
            }
            if (snapshot!=null){
                var documents = snapshot.documents

                // converting documentsnapshot object to our patient object
                documents.forEach {
                    val patient = it.toObject(Patient::class.java)
                    if (patient!=null){
                        patient.firestoreId = it.id
                        allPatients.add(patient)
                    }
                }
            }
        }
        return allPatients
    }*/


    //Funzione elimina paziente ma in locale e realtime database-->OK
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
        db=FirebaseDatabase.getInstance("https://thinkpen-28d8a-default-rtdb.europe-west1.firebasedatabase.app").getReference("Patients")
        //val patientsRef = db.collection("patients")
        //cerco paziente con quel taxcode
        db.child(patientDeleted.taxcode.toString()).removeValue().addOnSuccessListener {
            Timber.d("Deleted")
        }.addOnFailureListener{
            Timber.d("Not deleted")
        }
       /*val queryTaxCode = patientsRef.whereEqualTo("taxcode", "${patientDeleted.taxcode}")
        queryTaxCode.get().addOnSuccessListener { result ->

            if (result != null) {
                val document = result.documents
                document.forEach {
                    //val patient=it.toObject(Patient::class.java)
                    //if (patient != null){
                    var id = it.id
                    patientsRef.document(id).delete().addOnSuccessListener {
                        Timber.d("Deleted")
                    }
                        .addOnFailureListener {
                            Timber.d("Not deleted")
                        }
                }
            } else {
                Timber.d("No such document")
            }
        }*/
    }
}




