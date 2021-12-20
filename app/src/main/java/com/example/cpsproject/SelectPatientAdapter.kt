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

class SelectPatientAdapter(val c: Context, listPatientsAll: ArrayList<Patient>) :
    RecyclerView.Adapter<SelectPatientAdapter.ViewHolder>() {

    private var names = listPatientsAll.map { it.name }
    private var phases = listPatientsAll.map { it.phase }
    private var surname = listPatientsAll.map { it.surname }
    private val selectedPosition = -1
    var checkBoxStateArray = SparseBooleanArray()


    inner class ViewHolder(itemView: View, listener: onItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        var itemNameAll: TextView
        var itemPhaseAll: TextView
        var checkbox = itemView.checkBox

        init {
            itemNameAll = itemView.findViewById(R.id.PatientNameAll) as TextView
            itemPhaseAll = itemView.findViewById(R.id.tvPatientPhaseAll) as TextView
            checkbox.setOnClickListener {
                if (!checkBoxStateArray.get(adapterPosition, false)) {
                    checkbox.isChecked = true
                    checkBoxStateArray.put(adapterPosition, true)
                } else {
                    checkbox.isChecked = false
                    checkBoxStateArray.put(adapterPosition, false)
                }
            }


            itemView.setOnClickListener {
                listener.onClick(adapterPosition)
            }
        }

    }

    private lateinit var mListener: onItemClickListener


    interface onItemClickListener {
        fun onClick(position: Int)
    }

    fun setOnItemClickListener(listener: SelectPatientAdapter.onItemClickListener) {
        mListener = listener
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SelectPatientAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_patient_all, parent, false)
        return ViewHolder(v, mListener)
    }

    override fun onBindViewHolder(holder: SelectPatientAdapter.ViewHolder, position: Int) {
        holder.itemNameAll.text = names[position] + " " + surname[position]
        holder.itemPhaseAll.text = "Phase:" + phases[position].toString()


        holder.checkbox.isChecked = checkBoxStateArray.get(position, false)

        holder.checkbox.text = "CheckBox $position"


    }

    override fun getItemCount(): Int {
        return names.size
    }

}