package com.example.cpsproject.managers

import android.content.Context

import com.example.cpsproject.model.Session
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import timber.log.Timber
import java.io.File

object SessionManager {
    var sessione: Session = Session()
}

fun saveDocument(session:Session, context: Context) {
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
              Timber.d("Record added succesfully")
              File(fileName).delete()
              Timber.d("File deleted")
          }

          .addOnFailureListener { e ->
              Timber.d("Error filed to add")
              saveDocument(session, context)
          }
}



