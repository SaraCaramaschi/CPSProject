package com.example.cpsproject.model

data class Patient(
    var name: String?,
    var surname: String?,
    var notes: String ?= null,
    var taxcode: String?,
    var birthdate: String?,

    // TODO variabili da sistemare:
    var dominantHand: Hand,
    var gender: Gender,

    var phase: Int = 1




)
