package com.example.cpsproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cpsproject.managers.PatientsManager
import com.example.cpsproject.model.Patient

class PatientListActivity: AppCompatActivity() {
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var adapter: RecyclerView.Adapter<PatientAdapter.ViewHolder>
    lateinit var rvPatients: RecyclerView

    // QUI X RECYCLER CHE SI AGGIORNA
    var listPatients: ArrayList<Patient> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patients_list)

        // QUI X RECYCLER CHE SI AGGIORNA
        listPatients = PatientsManager.importPatientList(this)
        var adapter = PatientAdapter(listPatients)

        layoutManager = LinearLayoutManager(this)
        rvPatients = findViewById(R.id.rvPatients)
        rvPatients.layoutManager = layoutManager
        rvPatients.adapter = adapter
        val intentPage = Intent(this, PatientPageActivity::class.java)
        adapter.setOnItemClickListener(object : PatientAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                Toast.makeText(this@PatientListActivity,"you clicked on patient $position", Toast.LENGTH_SHORT).show()
                startActivity(intentPage)
                //TODO RIPEMPIRE PATIENT PAGE CON DATI DEL PAZIENTE
            }
        })



        val btnAddPatient = findViewById<Button>(R.id.btnNewPatient)
        btnAddPatient.setOnClickListener {
            val intent = Intent(this, AddPatientActivity::class.java)
            startActivity(intent)
        }

        val btnDeletePatient= findViewById<Button>(R.id.btnDeletePatient)
        btnDeletePatient.setOnClickListener{
            adapter.setOnItemClickListener(object : PatientAdapter.onItemClickListener{
                override fun onItemClick(position: Int) {
                    Toast.makeText(this@PatientListActivity,"you clicked on patient $position", Toast.LENGTH_SHORT).show()
                    //TODO QUI FUNZIONE PER ELIMINARE PAZIENTE
                }

           })
        }
        }

    }



//TODO c'è qualche errore che permette di scrivere dove non si dovrebbe quando si inseriscono
// i dati del pz: io sono riuscita a scrivere durante l'esecuzione dell'app nella scritta "Name"
