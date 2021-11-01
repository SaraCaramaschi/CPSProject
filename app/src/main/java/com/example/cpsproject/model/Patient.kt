package com.example.cpsproject.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

@Serializable // arriva da qui https://kotlinlang.org/docs/serialization.html#example-json-serialization
data class Patient( // ora ci sono 4 input
    var name: String,
    var surname: String,
    var notes: String ?= null,
    var taxcode: String,

    //Inserire da qualche parte la variabile della fase, qualcuno dovrà inserirla nell'app per fare in modo che venga visualizzata nella pt list

    // PENSO CI STIA METTERLO NELLA SCHERMATA PAZIENTE MA NON NELL'ADD PATIENT: TIPO CHE DI DEFAULT PHASE 1 E POI
    // QUANDO CLINICO FARà FASE 2 CAMBIAMO

    //TODO variabili da sistemare:

    // var dominantHand: Hand,
    // var gender: Gender
    // var phase: String
    // var birthdate: String,
)
