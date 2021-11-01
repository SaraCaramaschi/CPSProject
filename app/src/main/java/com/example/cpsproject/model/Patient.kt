package com.example.cpsproject.model



data class Patient(
    var name: String,
    var surname: String,
    var notes: String ?= null,
    var taxcode: String,
    var birthdate: String,
    //TODO inserire da qualche parte la variabile della fase, qualcuno dovrà inserirla nell'app per fare in modo che venga visualizzata nella pt list
    // PENSO CI STIA METTERLO NELLA SCHERMATA PAZIENTE MA NON NELL'ADD PATIENT: TIPO CHE DI DEFAULT PHASE 1 E POI
    // QUANDO CLINICO FARà FASE 2 CAMBIAMO
    var phase: String
    //TODO verificare che il compleanno sia una stringa
  //  var dominantHand: Hand,
   // var gender: Gender

)
