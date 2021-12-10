package com.example.cpsproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cpsproject.managers.PatientsManager
import com.example.cpsproject.model.Patient

class PatientListActivity: AppCompatActivity() {
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var adapter: RecyclerView.Adapter<PatientAdapter.ViewHolder>
    lateinit var rvPatients: RecyclerView
    //lateinit var rvDelete: RecyclerView

    /* fun removeItem(position:Int){
        patientsList.remove(position);
       // PatientAdapter.notifyItemRemoved(position);

    }*/
    //TODO NON SO SE BISOGNA RICHIAMARE ARRAY PAZIENTI PER VISUALIZZARE SUBITO LA LISTA--> in realtà basta importare in schermata 1
    //val pos= intent.getIntExtra("position", 0)

    // QUI X RECYCLER CHE SI AGGIORNA
    var listPatients: ArrayList<Patient> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patients_list)

        // QUI X RECYCLER CHE SI AGGIORNA

        //Importare pazienti da firebase
        listPatients = PatientsManager.getDocumentsPatient(this)
        //listPatients=PatientsManager.importPatientList(this)
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
                PatientsManager.selectedPatient = pos

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
