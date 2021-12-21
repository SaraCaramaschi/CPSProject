package com.example.cpsproject.managers

import android.content.Context
import com.example.cpsproject.model.PenData
import com.google.gson.Gson
import timber.log.Timber
import java.io.File

object PenManager {
    var penName: String?=null
    var battery : Double?=null
    var penData: PenData = PenData()
}