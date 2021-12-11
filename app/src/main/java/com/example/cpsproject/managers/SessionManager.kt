package com.example.cpsproject.managers

import com.example.cpsproject.model.Session

object SessionManager {
    var sessione: Session = Session()
    var patientConnection: Int?=null
    var downloadFinito: Boolean = false


    // Funzioni per caricare la sessione:
    // input da passargli poi dove verrà chiamata: SessionManager.sessione
    public fun saveSessionOnline(sess: Session){
        //TODO da implementare
    }

    // Funzione per pulire la sessione una volta caricata (banalmente cancellare le cose che ci sono)

    public fun ereaseSessione(sess: Session){
        //TODO da implementare

    }

}