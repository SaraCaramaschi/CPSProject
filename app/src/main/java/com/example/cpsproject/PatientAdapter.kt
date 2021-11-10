package com.example.cpsproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.cpsproject.managers.PatientsManager.patientsList
import com.example.cpsproject.managers.PatientsManager.readPatient
import com.example.cpsproject.model.Patient
import timber.log.Timber


class PatientAdapter(val contesto: Context) : RecyclerView.Adapter<PatientAdapter.ViewHolder>(){
    private var names= arrayOf<String>()
    private var phases= arrayOf<String>()

    // TODO IMPORTARE PATIENTLIST DA JSON

    inner class ViewHolder(itemView: View ): RecyclerView.ViewHolder(itemView){
        // scopo: prendere oggetto dalla lista creata e mostrarlo al recycler view
        var itemName: TextView
        var itemPhase: TextView

        init{
            itemName = itemView.findViewById(R.id.PatientName)
            itemPhase = itemView.findViewById(R.id.tvPatientPhase)

            Timber.d("parte importPatientList")
            var patList = importPatientList(contesto)
            for (i in patientsList.indices) {
               names[i] = patList[i].name.toString()
                phases[i] = patList[i].name.toString()
            }

            itemView.setOnClickListener{
                val position: Int = adapterPosition
                Toast.makeText(itemView.context, "you clicked on ${names[position]} ", Toast.LENGTH_LONG).show()
                // codice che fa passare a profilo del paziente
            }
            //var contesto = itemView.context
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientAdapter.ViewHolder {
        //Sarebbe da aggiungere anche le immagini https://www.youtube.com/watch?v=UCddGYMQJCo
        val v = LayoutInflater.from(parent.context).inflate(R.layout.activity_patient,parent,false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: PatientAdapter.ViewHolder, position: Int) {
        holder.itemName.text= names[position]
        holder.itemPhase.text= phases[position]

    }

    override fun getItemCount(): Int {
        return names.size
    }

    fun getContext(itemView: View): Context? {
        return itemView.context
    }

    private fun  importPatientList(context: Context): ArrayList<Patient>{
        var patList: ArrayList<Patient> = ArrayList()
        for (i in patientsList.indices) {
            var patientNew = readPatient(i, context)
            patList.add(patientNew)
        }
        return patList
    }
}


