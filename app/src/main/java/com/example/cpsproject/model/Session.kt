package com.example.cpsproject.model

import com.google.type.DateTime
import java.util.*
import kotlin.collections.ArrayList

// Da sistemare con cose precise del data model:

class Session {
    var device: String?=null
    var datetime: String?=null
    var sessionId: Int?=null //penso serva?
    var phase: Int?=null
    var sessionData: ArrayList<PenData> ?=null
    var patientID: String?=null
    var clinicianID: String?=null

   // var device: String = ""
    //var datetime: String = ""
    //var patientID: String = ""
   // var phase: Int=1
    //var sessionData: ArrayList<PenData> = ArrayList()
}