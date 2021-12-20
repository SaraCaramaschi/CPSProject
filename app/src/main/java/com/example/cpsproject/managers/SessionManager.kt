package com.example.cpsproject.managers

import android.content.Context
import com.example.cpsproject.model.Patient

import com.example.cpsproject.model.Session
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import timber.log.Timber
import java.io.BufferedReader
import java.io.File



object SessionManager {
    var sessione: Session = Session()
}

 public fun saveDocument(session:Session, context: Context) {
     val gson = Gson()
     val jsonDocument = gson.toJson(session)

     Timber.d("json %s", jsonDocument)

     var folder = context.getDir("RecordingSessionFolder", Context.MODE_PRIVATE)


     var fileName = folder.path.toString() + "/" + session.sessionId + ".txt"
     var file = File(fileName) // cartella uguale ma con una roba in più


     file.writeText(jsonDocument)
     saveFirestoreRecordingSession(jsonDocument, session, context)
 }

  fun saveFirestoreRecordingSession(jsonDocument:String, session: Session, context: Context){
    val db= Firebase.firestore
          val docref=db.collection("RecordingSessions")
    var mapsession: Map<String, Any> = HashMap()
    mapsession = Gson().fromJson(jsonDocument, mapsession.javaClass)

    var id = session.sessionId
    var folder = context.getDir("RecordingSessionFolder", Context.MODE_PRIVATE)
    var fileName = folder.path.toString() + "/" + id + ".txt"

    db.collection("RecordingSessions")
          .add(mapsession)
          .addOnSuccessListener {
              //ELIMINA LOCALE (ANCHE SE QUANDO AGGIUNGO PAZIETE SE AL PRIMO COLPO ME LO CARICA SU CLOUD
              //NON è NECESSARIO ELIMINARE LOCALE)--> FORSE DA TOGLIERE
              Timber.d("Record added succesfully")
              File(fileName).delete()
              Timber.d("File deleted")
          }

          .addOnFailureListener { e ->
              Timber.d("Error filed to add")
              //CODICE PER SALVARE IN LOCALE SE QUALOCSA VA STORTO
              saveDocument(session, context)
          }

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

fun readSessionJson(file: File, context: Context): Session {
    //Creating a new Gson object to read data
    var gson = Gson()
    //Read the PostJSON.json file
    val bufferedReader: BufferedReader = file.bufferedReader()
    // Read the text from buffferReader and store in String variable
    val inputString = bufferedReader.use { it.readText() }

    //Convert the Json File to Gson Object
    var session = gson.fromJson(inputString, Session::class.java)
    return session
}

 fun checkRecordingsLocal(context: Context) {
    var folder =
        context.getDir("RecordingSessionFolder", Context.MODE_PRIVATE)

    if (!folder.listFiles().isEmpty()) {
        File(context.getDir("RecordingSessionFolder", Context.MODE_PRIVATE).path).walk().forEach {
            //Timber.d(it.path)
            if (it.isFile) {
                val rec = readSessionJson(it,context)
                val gson = Gson()
                var jsonDoc = gson.toJson(rec)
                var fileNameCheck = folder.path.toString() + "/" + rec.sessionId + ".txt"

                //elimino da database
                val db=Firebase.firestore
                var mapsession: Map<String, Any> = HashMap()
                mapsession = Gson().fromJson(jsonDoc, mapsession.javaClass)
                db.collection("RecrdingSessions")
                    .add(mapsession)
                    .addOnSuccessListener {
                        Timber.d("Record added succesfully")
                        File(fileNameCheck).delete()
                        Timber.d("File deleted")
                    }

                    .addOnFailureListener { e ->
                        Timber.d("Error filed to add")
                        saveDocument(rec, context)
                    }

            }

        }
    }
}




//se dovesse servire per leggere i file salvati sul database
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


