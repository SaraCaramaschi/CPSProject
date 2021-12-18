package com.example.cpsproject

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.cpsproject.managers.PatientsManager.patientsList
import com.example.cpsproject.model.Patient
import kotlin.coroutines.coroutineContext
import com.example.cpsproject.managers.PatientsManager
import com.example.cpsproject.managers.PatientsManager.deletePatient


class PatientAdapter(val c: Context, listPatients : ArrayList<Patient>) : RecyclerView.Adapter<PatientAdapter.ViewHolder>(){

    // DOMANDONA: noi nella recycler view mostriamo i nomi o mostriamo i codici (per non farci hackerare yass) ???
    // Qui prendo tutti i nomi e le fasi della listaPaziente (che arriva dall'activity principale)
    private var names = listPatients.map{ it.name }
    private var phases = listPatients.map{ it.phase }
    private var surname = listPatients.map{ it.surname }
    private val selectedPosition= -1

     //TODO DOVE DEVO METTERLO https://www.youtube.com/watch?v=HMjI7cLsyfw


    inner class ViewHolder(itemView: View, listener: onItemClickListener ): RecyclerView.ViewHolder(itemView){
        var itemName: TextView
        var itemPhase: TextView


        init{
            itemName = itemView.findViewById(R.id.PatientName) as TextView
            itemPhase = itemView.findViewById(R.id.tvPatientPhase) as TextView


            itemView.setOnClickListener{
                listener.onClick(adapterPosition)
            }

        }





    }

   private lateinit var mListener: onItemClickListener


   interface onItemClickListener : SelectPatientAdapter.onItemClickListener {
        override fun onClick(position:Int)

   }


    fun setOnItemClickListener(listener: onItemClickListener){
        mListener=listener
   }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.activity_patient,parent,false)
        return ViewHolder(v, mListener)
    }

    override fun onBindViewHolder(holder: PatientAdapter.ViewHolder, position: Int) {
        holder.itemName.text = names[position] + " " + surname[position]
        holder.itemPhase.text = "Phase:" + phases[position].toString()




    }

    override fun getItemCount(): Int {
        return names.size
    }
}

