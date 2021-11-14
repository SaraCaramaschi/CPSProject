package com.example.cpsproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.cpsproject.model.Patient

class PatientAdapter(listPatients : ArrayList<Patient>) : RecyclerView.Adapter<PatientAdapter.ViewHolder>(){

    // DOMANDONA: noi nella recycler view mostriamo i nomi o mostriamo i codici (per non farci hackerare yass) ???

    // Qui prendo tutti i nomi e le fasi della listaPaziente (che arriva dall'activity principale)
    private var names = listPatients.map{ it.name }
    private var phases = listPatients.map{ it.phase }
    private var surname = listPatients.map{ it.surname }

    inner class ViewHolder(itemView: View, listener: onItemClickListener ): RecyclerView.ViewHolder(itemView){
        var itemName: TextView
        var itemPhase: TextView

        init{
            itemName = itemView.findViewById(R.id.PatientName)
            itemPhase = itemView.findViewById(R.id.tvPatientPhase)

//           itemView.setOnClickListener{
//                val position: Int = adapterPosition
//                Toast.makeText(itemView.context, "you clicked on ${names[position]} ", Toast.LENGTH_LONG).show()
//                // TODO codice che fa passare a profilo del paziente
//            }
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }

    }

    private lateinit var mListener: onItemClickListener
    interface onItemClickListener{
        fun onItemClick(position:Int)

    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener=listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.activity_patient,parent,false)
        return ViewHolder(v, mListener)
    }

    override fun onBindViewHolder(holder: PatientAdapter.ViewHolder, position: Int) {
        holder.itemName.text= names[position]+ " "+ surname[position]
        holder.itemPhase.text= "Phase:" + phases[position].toString()


    }

    override fun getItemCount(): Int {
        return names.size
    }
}


