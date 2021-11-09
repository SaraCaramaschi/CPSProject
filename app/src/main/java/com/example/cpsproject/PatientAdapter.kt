package com.example.cpsproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.cpsproject.managers.PatientsManager.patientsList
import com.example.cpsproject.managers.PatientsManager.readPatient


class PatientAdapter: RecyclerView.Adapter<PatientAdapter.ViewHolder>(){
    private var names= arrayOf<String>()
    private var phases= arrayOf<String>()


    inner class ViewHolder(itemView: View ): RecyclerView.ViewHolder(itemView){
        // scopo: prendere oggetto dalla lista creata e mostrarlo al recycler view
        var itemName: TextView
        var itemPhase: TextView

        init{
            itemName = itemView.findViewById(R.id.PatientName)
            itemPhase = itemView.findViewById(R.id.tvPatientPhase)

            itemView.setOnClickListener{
                val position: Int = adapterPosition
                Toast.makeText(itemView.context, "you clicked on ${names[position]} ", Toast.LENGTH_LONG).show()
                // codice che fa passare a profilo del paziente
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientAdapter.ViewHolder {
//        var names= arrayOf<String>()
//        var phases= arrayOf<String>()
        //Sarebbe da aggiungere anche le immagini https://www.youtube.com/watch?v=UCddGYMQJCo
        val v= LayoutInflater.from(parent.context).inflate(R.layout.activity_patient,parent,false)
        return ViewHolder(v)
        // TODO IMPORTARE PATIENTLIST DA JSON

        for (i in patientsList.indices) {
            var patientNew= readPatient(i)
            names[i]= patientNew.name + patientNew.surname
            phases[i]=patientNew.phase.toString()
        }
//        val v= LayoutInflater.from(parent.context).inflate(R.layout.activity_patient,parent,false)
//        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: PatientAdapter.ViewHolder, position: Int) {
        holder.itemName.text= names[position]
        holder.itemPhase.text= phases[position]

    }

    override fun getItemCount(): Int {
        return names.size
    }
//    inner class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
//        var itemImage: ImageView
//        var itemName: TextView
//        var itemPhase: TextView
//
//        init {
//            itemImage = itemView.findViewById(R.id.imageView)
//            itemName = itemView.findViewById(R.id.PatientName)
//            itemPhase = itemView.findViewById(R.id.tvPatientPhase)
//        }
//
//    }
    //TODO NON CAPISCO PERCHE' NON TROVI ARRAY NAMES E PHASES
//    override fun onCreate(savedInstanceState: Bundle?){
//     super.onCreate(savedInstanceState)
//     setContentView(R.layout.activity_patients_list)
//     setSupportActionBar(toolbar())
//        recyclerView

}



