package com.example.cpsproject.model

import com.google.type.DateTime
import java.util.*
import kotlin.collections.ArrayList

// Da sistemare con cose precise del data model:

class Session {
    var device: String = ""
    var datetime: String = ""
    var patientID: String = ""
    var sessionData: ArrayList<PenData> = ArrayList()
}