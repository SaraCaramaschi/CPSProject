package com.example.cpsproject.managers

import android.content.Context
import com.example.cpsproject.model.PenData
import com.google.gson.Gson
import timber.log.Timber
import java.io.File

object PenManager {
    public var penName: String?=null
    public var battery : Double?=null
    public var penData: PenData = PenData()
    //potremmo salvare il nome della penna: penmanager.pen name troviamo questa cosa
    //potremmo salvare batteria e tutti i dati.
    // PROVA DOWNLOAD JSON FILE ON LOCAL
    public fun downloadJson(penData: PenData, context: Context){
        val gson = Gson()
        val jsonData = gson.toJson(penData)
        var folder = context.getDir("PenDataFolder", Context.MODE_PRIVATE)
        Timber.d("questo è nuova cartella: %s", folder.path.toString())

        var fileName = folder.path.toString() + "/" + penData.acc_x + ".txt"
        var file = File(fileName)
        val createdFile = file.createNewFile()

        Timber.d("Il filename e': %s", fileName)
        Timber.d("the file is created %s", createdFile)
        Timber.d("path %s", file.absolutePath)

        file.writeText(jsonData)
    }

    // Non ci serve "readJson" dei dati della penna perchè questi dati saranno solo poi scritti online firebase

}