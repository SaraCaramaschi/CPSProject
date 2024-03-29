package com.example.cpsproject

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cpsproject.managers.PatientsManager
import com.example.cpsproject.managers.PatientsManager.deletePatient
import com.example.cpsproject.model.Patient
import timber.log.Timber

class PatientListActivity: AppCompatActivity() {
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var adapter: RecyclerView.Adapter<PatientAdapter.ViewHolder>
    lateinit var rvPatients: RecyclerView
    //lateinit var rvDelete: RecyclerView


    //
    //
    /* fun removeItem(position:Int){
        patientsList.remove(position);
       // PatientAdapter.notifyItemRemoved(position);

    }*/
    //
    //


    // QUI X RECYCLER CHE SI AGGIORNA
    var listPatients: ArrayList<Patient> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patients_list)

        // QUI X RECYCLER CHE SI AGGIORNA


        listPatients = PatientsManager.importPatientList(this)
        var adapter = PatientAdapter(this, listPatients)

        layoutManager = LinearLayoutManager(this)
        rvPatients = findViewById(R.id.rvPatients)
        rvPatients.layoutManager = layoutManager
        rvPatients.adapter = adapter

        //PASSA AD PAGINA PAZIENTE
        val intentPage = Intent(this, PatientPageActivity::class.java)


        adapter.setOnItemClickListener(object : PatientAdapter.onItemClickListener {
            override fun onClick(position: Int) {
//                Toast.makeText(
//                    this@PatientListActivity,
//                    "you clicked on patient $position",
//                    Toast.LENGTH_SHORT
//                ).show()
                var pos = position
                intentPage.putExtra("position", pos)
                startActivity(intentPage)
                //TODO RIEMPIRE PATIENT PAGE CON DATI DEL PAZIENTE--> fatto ma manca da acquisire enum ecc
            }

        })

        //AGGIUNGE PAZIENTE
        val btnAddPatient = findViewById<Button>(R.id.btnNewPatient)
        btnAddPatient.setOnClickListener {
            val intent = Intent(this, AddPatientActivity::class.java)
            startActivity(intent)
        }

        //TENTATIVI DELETE


        //Toast.makeText(this, "Long click on the patient that you want to delete", Toast.LENGTH_SHORT).show()
        /*adapter.setOnItemClickListener(object : PatientAdapter.onItemClickListener {
                override fun onClick(position: Int) {
                }

                override fun onLongClick(position: Int) {
                    Toast.makeText(
                        this@PatientListActivity,
                        "you clicked on patient $position",
                        Toast.LENGTH_SHORT
                    ).show()
                    var pos = position
                    intentDelete.putExtra("position", pos)
                    startActivity(intentDelete)
                }
            }
            )*/
    }
}











//
        /*override fun deletePatient (position: Int) {
            Toast.makeText(
                this@PatientListActivity, "you clicked on patient $position", Toast.LENGTH_SHORT).show()
            var pos = position
            intentPage.putExtra("position", pos)
            startActivity(intentPage)
        }*/

// tentativo 1

        /*
           override fun onDeleteClick(position: Int) {
               deletePatient( this@PatientListActivity, position) //questo è un metodo ch ein java è stato definito come foto ila

            }
            */

        //

        // ELIMINA PAZIENTE --> fatto  un po' da ila quindi non fidarti


        //ILA
        /*val delete= findViewById<ImageView>(R.id.ic_delete)
        val intentdelete=Intent(this,DeleteMessageActivity::class.java)

            delete.setOnClickListener{
            intentdelete.putExtra("position",position)
            val intentNew= Intent(this, EditPatientActivity::class.java)
            intentNew.putExtra("position",pos)
            startActivity(intentNew)
        */




         /*   val btnDeletePatient = findViewById<Button>(R.id.btnDeletePatient)
            btnDeletePatient.setOnClickListener {
                val intent = Intent(this, DeleteMessageActivity::class.java)
                startActivity(intent)
*/





            //QUI VOLEVA SELEZIONARE I PAZIENTI CON setonLONGclicklistener ma ovviamente non riesco [NON SENTE CLICK LUNGO E VA A PATIENT PAGE]e ho abbandonato
            // --> ho fatto con menù a lato del singolo paziente (codice nell'adapter, non mi piace)

         /*   Toast.makeText(this@PatientListActivity,"Select a patient for a long time", Toast.LENGTH_SHORT).show()

           adapter.setOnItemClickListener(object : PatientAdapter.onItemClickListener{
               override fun onClick(position: Int) {
                   Toast.makeText(this@PatientListActivity,"Long click detected", Toast.LENGTH_SHORT).show()
                   var pos=position
                   intent.putExtra("position",pos)
                   startActivity(intent) // OPPURE ALERT DIALOG (come ho fatto con menù)--> VOLEVO CHIEDERE CONFERMA DELL'ELIMINAZIONE
                   deletePatient(this@PatientListActivity, pos) //DOVREBBE ESSERE FUNZIONE CHE ELIMINA FILE JSON (IMPLEMENTATA NEL MANAGER)
               }


           }
        })
            */



//TODO c'è qualche errore che permette di scrivere dove non si dovrebbe quando si inseriscono
// i dati del pz: io sono riuscita a scrivere durante l'esecuzione dell'app nella scritta "Name"
