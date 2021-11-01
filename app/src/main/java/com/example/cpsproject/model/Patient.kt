package com.example.cpsproject.model

data class Patient(
    var name: String,
    var surname: String,
    var notes: String ?= null,
    var taxcode: String,
    var birthdate: String,
    //TODO inserire da qualche parte la variabile della fase, qualcuno dovrà inserirla nell'app per fare in modo che venga visualizzata nella pt list
    var phase: String
    //TODO verificare che il compleanno sia una stringa
  //  var dominantHand: Hand,
   // var gender: Gender
)
