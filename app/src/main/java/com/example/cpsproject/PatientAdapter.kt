package com.example.cpsproject


import android.widget.Filter.FilterResults as filterResults

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Insets.add
import android.text.AlteredCharSequence
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.graphics.Insets.add
import androidx.core.view.OneShotPreDrawListener.add
import androidx.recyclerview.widget.RecyclerView
import com.example.cpsproject.managers.PatientsManager.patientsList
import com.example.cpsproject.model.Patient
import kotlin.coroutines.coroutineContext
import com.example.cpsproject.managers.PatientsManager
import com.example.cpsproject.managers.PatientsManager.deletePatient


class PatientAdapter(val c: Context, listPatients : ArrayList<Patient>) : RecyclerView.Adapter<PatientAdapter.ViewHolder>(), Filterable{

    // DOMANDONA: noi nella recycler view mostriamo i nomi o mostriamo i codici (per non farci hackerare yass) ???
    // Qui prendo tutti i nomi e le fasi della listaPaziente (che arriva dall'activity principale)
    private var names = listPatients.map{ it.name }
    private var phases = listPatients.map{ it.phase }
    private var surname = listPatients.map{ it.surname }
    private val selectedPosition= -1
    var itemModalList= ArrayList<Patient>()
 var itemFilter =ArrayList<Patient>();
     //TODO DOVE DEVO METTERLO https://www.youtube.com/watch?v=HMjI7cLsyfw

  fun setData(itemModalList: ArrayList<Patient>){
     this.itemModalList=itemModalList
         notifyDataSetChanged()
 }

    inner class ViewHolder(itemView: View, listener: onItemClickListener ): RecyclerView.ViewHolder(itemView) {
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

   interface onItemClickListener{
        fun onClick(position:Int)

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








//FILTRO 1
override fun getFilter(): Filter {

    return object :Filter(){
        override fun performFiltering(charsequence: CharSequence?): filterResults {
            var filterResults = filterResults()
            if (charsequence == null || charsequence.isEmpty()) {

            } else {
                var searchChr: String = charsequence.toString().toLowerCase();
                var searchPtList: ArrayList<Patient> = ArrayList();
                for (item in itemFilter) {
                    if (item.name?.toLowerCase()
                            ?.contains(searchChr) == true || item.surname?.toLowerCase()
                                ?.contains(searchChr) == true
                                            ) {
                        searchPtList.add(item)
                    }
                }
                filterResults.count = itemFilter.size
                filterResults.values = itemFilter;

            }
            return filterResults;
        }


        override fun publishResults(p0: CharSequence?, p1: filterResults?) {
            patientsList = p1?.values as ArrayList<Patient>
            notifyDataSetChanged()
        }

    }

}

//1
    /* override fun getFilter(): Filter {

         return object :Filter(){
             override fun performFiltering(charsequence: CharSequence?): FilterResults {
                 var filterResults = FilterResults()
                 if (charsequence == null || charsequence.isEmpty()) {

                 } else {
                     var searchChr: String = charsequence.toString().toLowerCase();
                     var patientList: List<Patient>;
                     for (item in itemFilter) {
                         if (item.name.toLowerCase()
                                 .contains(searchChr) || items.surname.toLowerCase()
                                 .contains(searchChr)
                         ) {
                             patientList.add(patientList)
                         }
                     }
                     filterResults.count = itemFilter.size
                     filterResults.values = itemFilter;

                 }
                 return FilterResults;
             }

             override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                 patientsList = p1.values as ArrayList<Patient>
                 notifyDataSetChanged()
             }

         }

     }*/

    //2
    /*override fun getFilter(): Filter {
        return filter
    }

    private val filter: Filter = object : Filter() {
        var filteredList: MutableList<Patient> = arrayListOf()
        override fun performFiltering(constraint: CharSequence): FilterResults {

            if (constraint.isEmpty()) {
                filteredList.addAll(patientsList)
            }
            else {
                val filterPattern = constraint.toString().toLowerCase().trim { it <= ' ' }
                for (item in 0..patientsList.size) {
                    if (patientsList[item].name!!.contains(filterPattern) || patientsList[item].surname!!.contains(filterPattern))

                    {
                        filteredList.add(patientsList[item])
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
            filteredList = filterResults.values as MutableList<Patient>
            notifyDataSetChanged()
        }
    }*/


}

