package com.example.cpsproject.managers

import com.example.cpsproject.model.PenData

object PenManager {
    public var penName: String?=null
    public var battery : Double?=null
    public var penData: PenData = PenData()
    //potremmo salvare il nome della penna: penmanager.pen name troviamo questa cosa
    //potremmo salvare batteria e tutti i dati.
}