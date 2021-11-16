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
        //var mMenus: ImageView
        var mDeleteImage: ImageView


        init{
            itemName = itemView.findViewById(R.id.PatientName)
            itemPhase = itemView.findViewById(R.id.tvPatientPhase)
          // mMenus= itemView.findViewById(R.id.mMenus)
           //mMenus.setOnClickListener{ popupMenus(it) }
            mDeleteImage= itemView.findViewById(R.id.ic_delete)


            //da qui ila inizia a scrivere cose
            mDeleteImage.setOnClickListener{
                val position: Int = adapterPosition
                listener.onDeleteClick(position)

            }
//FINE ILA

//           // SARA
//             itemView.setOnClickListener{
//                val position: Int = adapterPosition
//                Toast.makeText(itemView.context, "you clicked on ${names[position]} ", Toast.LENGTH_LONG).show()
//                }

           // textView.setOnClickListener(new View.OnClickListener()



         //   )




            itemView.setOnClickListener{
                listener.onClick(adapterPosition)
                //listener.onLongClick(adapterPosition)
            }

        }

        //DA QUA IN POI PER IMPLEMENTARE MENU' A LATO DI OGNI PAZIENTE DELLA RECYCLE LIST (PER ELIMINARE E MODIFICARE CIASCUN PAZIENTE) https://www.youtube.com/watch?v=-PIKVIJb7Xs
        //MI SEMBRA STRANO SIA TUTTO IN ADAPTER
        //MODIFICARE PAZIENTE MEGLIO METTERE UNA ICONA NELLA PATIENT PAGE (COSì TROPPO COMPLICATO)
        //PER ELIMINARE NON SO, CARINO IL DIALOG ALERT
//
/*
        private fun popupMenus(v: View) {
           val position= names[adapterPosition] //prima c'era namesinvece di patienlist
            val popupMenus= PopupMenu(c, v)
            popupMenus.inflate(R.menu.show_menu)
            popupMenus.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.editText->{


                        //val v= LayoutInflater.from(c).inflate(R.layout.activity_add_patient,null) //non sono sicura che l'activity sia add patient
                       //TODO QUESTO NON CAPISCO
                        // val name=v.findViewById<?????>(R.id.PatientName)
//ILA
                               /*AlertDialog.Builder(c)
                                   .setView(v)
                                   .setPositiveButton("ok"){
                                       dialog,->
                                       position.name=name.text.toString()
                                       notifyDataSetChanged()
                                       Toast.makeText(c,"User information edit", Toast.LENGTH_SHORT).show()
                                      dialog.dismiss()
                                   }

                                   .setNegativeButton("cancel"){
                                           dialog,->
                                       dialog.dismiss()
                                   }
                                   .create()
                                   .show()



                        //FINE ILA
                        */


                        //TODO NON SAPREI COME FARE (MEGLIO FARLO DA PATIENT PAGE

//QUESTO SOTTO COMMENTATO DA ILA POSSIBILE TOGLIERE SE NON FUNZIONA
                        Toast.makeText(c,"EditText Button is Clicked", Toast.LENGTH_SHORT).show()
                        true
                    }
                    R.id.delete->{
                       AlertDialog.Builder(c)
                            .setTitle("Delete")
                            .setIcon(R.drawable.ic_warning)
                            .setMessage("Are you scure delete this Patient")


                            .setPositiveButton("Yes") {
                                    dialog, which ->
                                //INIZIO ILA
                               /* patientsList.removeAt(adapterPosition)
                                notifyDataSetChanged()
                                Toast.makeText(c,"Delete Button is Clicked", Toast.LENGTH_SHORT).show()
                                dialog.dismiss()
                            }*/

                                //FINE ILA


                                deletePatient(c, adapterPosition) //Elimino File Json--> NON VA
                                notifyDataSetChanged()
                                Toast.makeText(c,"Deleted this Patient", Toast.LENGTH_SHORT).show()
//
                                dialog.dismiss()
                           }

                           .setNegativeButton("No"){
                                   dialog, which  ->
                                dialog.dismiss()
////
                            }



                            .create()
                            .show()

                        true
                    }
                    else->true
               }
            }
            popupMenus.show()
            val popup = PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible=true
            val menu = popup.get(popupMenus)
            menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                .invoke(menu, true)
        }
*/




    }

   private lateinit var mListener: onItemClickListener

   interface onItemClickListener{
        fun onClick(position:Int)

        //ILA RIGA SENZA SENSO???
   fun onDeleteClick(position: Int)

//     //fun onLongClick(position: Int)

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

//        // per cambiare il colore a un elemento selezionato (non funziona)
        if (selectedPosition == position) {
            holder.itemView.setBackgroundColor(Color.parseColor("#9999A1"))
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFFFF"))
        }


    }

    override fun getItemCount(): Int {
        return names.size
    }
}

