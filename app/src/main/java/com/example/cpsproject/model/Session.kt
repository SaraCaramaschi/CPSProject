package com.example.cpsproject.model

import com.google.type.DateTime
import java.util.*

// Da sistemare con cose precise del data model:

class Session {
    var device: String?=null
    var datetime: String?=null
    var sessionId: Int?=null
    var data: PenData?=null // modello che avevamo creato per il real time
    var patientPos: Int?=null

}