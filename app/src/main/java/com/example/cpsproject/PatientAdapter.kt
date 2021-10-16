package com.example.cpsproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class PatientAdapter: RecyclerView.Adapter<PatientAdapter.ViewHolder>() {
    private var patientsName = arrayOf("Mario Rossi", "Sara Caram","Vichi Attols Dj")
    private var patientsPhase = arrayOf("Phase1","Phase1","Phase2")
    private var patientsItems = intArrayOf(R.drawable.user,R.drawable.user,R.drawable.user)

    inner class ViewHolder(itemView: View ): RecyclerView.ViewHolder(itemView){

        var itemImage: ImageView
        var itemName: TextView
        var itemPhase: TextView

        init{
            itemImage = itemView.findViewById(R.id.imageView)
            itemName = itemView.findViewById(R.id.PatientName)
            itemPhase = itemView.findViewById(R.id.PatientPhase)

            itemView.setOnClickListener{
                val position: Int = adapterPosition
                Toast.makeText(itemView.context, "you clicked on ${patientsName[position]} ", Toast.LENGTH_LONG).show()
                // codice che fa passare a profilo del paziente

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.activity_patient, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: PatientAdapter.ViewHolder, position: Int) {
        holder.itemName.text = patientsName[position]
        holder.itemPhase.text = patientsPhase[position]
        holder.itemImage.setImageResource(patientsItems[position])
    }

    override fun getItemCount(): Int {
       return patientsName.size
    }
}