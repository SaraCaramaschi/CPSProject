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


}