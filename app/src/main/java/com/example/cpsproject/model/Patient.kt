package com.example.cpsproject.model

data class Patient(
    var name: String,
    var surname: String,
//    var birthPlace: String,
    var notes: String ?= null,
//    var dominantHand: Hand,
//    var gender: Gender
)
