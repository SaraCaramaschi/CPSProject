package com.example.cpsproject.model

data class Patient(
    var name: String?=null,
    var surname: String?=null,
    var notes: String ?= null,
    var taxcode: String?=null,
    var birthdate: String?=null,
    var dominantHand: Hand=Hand.Right,
    var gender: Gender=Gender.Female,
    var cliniciansID: ArrayList<String?> = ArrayList(),
    var phase: Int = 1
)

