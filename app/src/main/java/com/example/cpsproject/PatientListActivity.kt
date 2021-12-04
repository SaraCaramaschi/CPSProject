package com.example.cpsproject

import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cpsproject.managers.PatientsManager
import com.example.cpsproject.managers.PatientsManager.deletePatient
import com.example.cpsproject.model.Patient
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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

        //Importare pazienti da firebase

        //TODO QUA DOVREBBE RICHIAMARE FUNZIONE GET DOCUMENT DA FIREBASE (commento se no non funziona)
        //listPatients = PatientsManager.getDocuments(this)
        listPatients=PatientsManager.importPatientList(this)
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

//TODO c'è qualche errore che permette di scrivere dove non si dovrebbe quando si inseriscono
// i dati del pz: io sono riuscita a scrivere durante l'esecuzione dell'app nella scritta "Name"
