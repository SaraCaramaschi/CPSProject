package com.example.cpsproject

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.cpsproject.model.Patient

class PatientAdapter2 (private val context:Activity,private val arrayList: ArrayList<Patient>):ArrayAdapter<Patient>(context,
R.layout.activity_patient
    , arrayList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

         val inflater:LayoutInflater= LayoutInflater.from(context)
         val view: View = inflater.inflate(R.layout.activity_patient,null)


       // QUESTA RIGA NON SO SE SERVE PERCHè NON SO SE METTEREMO LE IMMAGINI
        // val imageView: ImageView=view.findViewById<>(R.id.profilr_pic)
        //TODO se aggiungi qualcosa da visualizzare nlla lista pz aggiungi qui le variabili
        val txusername: TextView=view.findViewById(R.id.PatientName)
        val txphase: TextView=view.findViewById(R.id.tvPatientPhase)



        //DA AGGIUNGERE SOLO SE METTIAMO LE FOTO
        // imageView.setImageResource(arrayList[position].imageId)

        //TODO da modificare per fare in modod che oltra la nome ci sia anche il cognome sulla pt list

        txusername.text=arrayList[position].name
        txphase.text= "Fase " + arrayList[position].phase

        return view
    }
    }




