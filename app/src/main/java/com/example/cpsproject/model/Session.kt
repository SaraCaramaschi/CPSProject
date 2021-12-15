package com.example.cpsproject.model

import com.google.type.DateTime
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

// Da sistemare con cose precise del data model:

class Session {
    var device: String?=null
    var datetime: LocalDate?=null
    var sessionId: Int?=null //penso serva?
    var phase: Int?=null
    var acquisitions: ArrayList<Acquisition> = arrayListOf()
    var patientID: String?=null
    var clinicianID: String?=null
    var nFile: Int?=null

    // var device: String = ""
    // var datetime: String = ""
    // var patientID: String = ""
    // var phase: Int=1
    // var sessionData: ArrayList<PenData> = ArrayList()
}