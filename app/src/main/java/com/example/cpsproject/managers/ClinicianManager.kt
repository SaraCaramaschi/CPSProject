package com.example.cpsproject.managers

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.cpsproject.model.Clinician
import com.example.cpsproject.model.Patient
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import timber.log.Timber
import java.io.BufferedReader
import java.io.File

object ClinicianManager {

    public var clinicianList: ArrayList<Clinician> = ArrayList()

    //public var selectedPatient: Int?=null
    var email: String = String()
    var password: String = String()
    var username: String = String()

    fun addClinician(clinician: Clinician, context: Context) {
        clinicianList.add(clinician)
        saveFirestoreClinician(clinician, context)

    }

    //salva in locale
    fun saveClinician(clinician: Clinician, context: Context) {
        val gson = Gson()
        val jsonClinician = gson.toJson(clinician)
        val db = Firebase.firestore

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
    fun saveFirestoreClinician(clinician: Clinician, context: Context) {
        val gson = Gson()
        val jsonclinician = gson.toJson(clinician)
        val dbn = FirebaseFirestore.getInstance()
        var mapclinician: Map<String, Any> = HashMap()
        mapclinician = Gson().fromJson(jsonclinician, mapclinician.javaClass)


        var email = clinician.email
        var folder = context.getDir("CliniciansFolder", Context.MODE_PRIVATE)
        var fileName = folder.path.toString() + "/" + email + ".txt"


        dbn.collection("clinicians")
            .add(mapclinician)
            .addOnSuccessListener {
                //ELIMINA LOCALE (ANCHE SE QUANDO AGGIUNGO PAZIETE SE AL PRIMO COLPO ME LO CARICA SU CLOUD
                //NON è NECESSARIO ELIMINARE LOCALE)--> FORSE DA TOGLIERE
                Timber.d("Record added succesfully")
                File(fileName).delete()
                Timber.d("File deleted")
            }

            .addOnFailureListener { e ->
                Timber.tag(ContentValues.TAG).w(e, "Error filed to add")
                //TODO CODICE PER SALVARE IN LOCALE SE QUALOCSA VA STORTO--> verficare se funziona
                saveClinician(clinician, context)
            }

        //TODO CODICE PER CARICARE FILE IN LOCALE
        //FORSE SAREBBE MEGLIO METTERE QUESTO PEZZO DI FUNZIONE OGNI VOLTA CHE CLINICO APRE L'APP(?)
        //COSì CHE NON SERVA CHE CARICHI UN NUOVO FILE PER CARICARE I PRECEDENTI

        if (!folder.listFiles().isEmpty()) {
            File(context.getDir("CliniciansFolder", Context.MODE_PRIVATE).path).walk().forEach {
                Timber.d(it.path)
                if (it.isFile) {
                    val clin = readClinicianJson(it, context)
                    if (clin != clinician) {
                        val gson = Gson()
                        var jsonClin = gson.toJson(clin)
                        var fileNameNew = folder.path.toString() + "/" + clin.email + ".txt"
                        var mapclinicianNew: Map<String, Any> = HashMap()
                        mapclinicianNew = Gson().fromJson(jsonClin, mapclinician.javaClass)
                        dbn.collection("patients")
                            .add(mapclinicianNew)
                            .addOnSuccessListener {
                                //Elimino locale
                                Timber.d("Record added succesfully")
                                File(fileNameNew).delete()
                                Timber.d("File deleted")

                            }
                            .addOnFailureListener { e ->
                                Log.w(ContentValues.TAG, "Error filed to add", e)
                                //TODO CODICE PER RISALVARE IN LOCALE--> verificare se funziona
                                saveClinician(clinician, context)
                            }
                    }
                }
            }
        }
    }

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


        // Funzione per leggere documenti da firebstore
        fun getDocumentsClinician(context: Context): ArrayList<Clinician> {
            // [START get_document]
            val db = Firebase.firestore

            val docRef = db.collection("clinicians")
            docRef.get().addOnSuccessListener { result ->
                for (document in result) {
                    if (document != null) {
                        Log.d(ContentValues.TAG, "${document.id}=>${document.data}")
                    } else {
                        Timber.d("No such document")
                    }

                    var dbClinician = document.toObject(Clinician::class.java)

                    if (!clinicianList.contains(dbClinician)) {
                        clinicianList.add(dbClinician)
                    }

                }
            }
                .addOnFailureListener { exception ->
                    Timber.e(exception, "Error getting document")

                }
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

            if (!File(fodername).list().contains(fileName)) {
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





