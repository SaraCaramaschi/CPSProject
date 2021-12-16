package com.example.cpsproject

import android.content.Context
import android.graphics.Color
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cpsproject.model.Patient
import kotlinx.android.synthetic.main.activity_patient_all.view.*

class SelectPatientAdapter(val c: Context, listPatients : ArrayList<Patient>) : RecyclerView.Adapter<SelectPatientAdapter.ViewHolder>(){

    // DOMANDONA: noi nella recycler view mostriamo i nomi o mostriamo i codici (per non farci hackerare yass) ???
    // Qui prendo tutti i nomi e le fasi della listaPaziente (che arriva dall'activity principale)
    private var names = listPatients.map{ it.name }
    private var phases = listPatients.map{ it.phase }
    private var surname = listPatients.map{ it.surname }
    private val selectedPosition= -1
    var checkBoxStateArray= SparseBooleanArray()

    //TODO DOVE DEVO METTERLO https://www.youtube.com/watch?v=HMjI7cLsyfw


    inner class ViewHolder(itemView: View, listener: onItemClickListener ): RecyclerView.ViewHolder(itemView){
        var itemName: TextView
        var itemPhase: TextView
        var checkbox= itemView.checkBox

        init{
            itemName = itemView.findViewById(R.id.PatientName) as TextView
            itemPhase = itemView.findViewById(R.id.tvPatientPhase) as TextView
            checkbox.setOnClickListener{
                if(!checkBoxStateArray.get(adapterPosition,false))
                {
                    checkbox.isChecked=true
                    checkBoxStateArray.put(adapterPosition,true)
                }
            else{
                checkbox.isChecked=false
                    checkBoxStateArray.put(adapterPosition,false)
                }
            }


            itemView.setOnClickListener{
                listener.onClick(adapterPosition)
            }
        }

    }

    private lateinit var mListener: onItemClickListener



    interface onItemClickListener{
        fun onClick(position:Int)
    }

    fun setOnItemClickListener(listener: SelectPatientAdapter.onItemClickListener){
        mListener=listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectPatientAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.activity_all_patient_list,parent,false)
        return ViewHolder(v, mListener)
    }

    override fun onBindViewHolder(holder: SelectPatientAdapter.ViewHolder, position: Int) {
        holder.itemName.text = names[position] + " " + surname[position]
        holder.itemPhase.text = "Phase:" + phases[position].toString()


        holder.checkbox.isChecked = checkBoxStateArray.get(position, false)

        holder.checkbox.text="CheckBox $position"


    }

    override fun getItemCount(): Int {
        return names.size
    }

}