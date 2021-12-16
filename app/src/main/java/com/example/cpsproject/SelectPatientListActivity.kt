package com.example.cpsproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cpsproject.managers.PatientsManager
import com.example.cpsproject.model.Patient
import com.punchthrough.blestarterappandroid.ble.ConnectionManager
import com.punchthrough.blestarterappandroid.ble.ConnectionManager.isConnected
import kotlinx.android.synthetic.main.activity_patient_all.*

class SelectPatientListActivity : AppCompatActivity() {
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var adapterGlobal: RecyclerView.Adapter<SelectPatientAdapter.ViewHolder>
    lateinit var rvPatients: RecyclerView

    //TODO NON SO SE BISOGNA RICHIAMARE ARRAY PAZIENTI PER VISUALIZZARE SUBITO LA LISTA--> in realtà basta importare in schermata 1

    // QUI X RECYCLER CHE SI AGGIORNA
    var listPatients: ArrayList<Patient> = ArrayList()
    var listAllPatients: ArrayList<Patient> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_patient_list)

        // GET USER

     /*   var currentuser = FirebaseAuth.getInstance().getCurrentUser()?.getUid()
        var ID: String = String()
        if (currentuser != null) {
            ID = currentuser
        }*/

        // QUI X RECYCLER CHE SI AGGIORNA
        //Importare pazienti da firebase
        listPatients = PatientsManager.getDocumentsAllPatient(this)
        //listPatients=PatientsManager.importPatientList(this)
        val adapter = SelectPatientAdapter(this, listPatients)
        layoutManager = LinearLayoutManager(this)
        rvPatients = findViewById(R.id.rvPatients)
        rvPatients.layoutManager = layoutManager
        rvPatients.adapter = adapter

        val search = findViewById<SearchView>(R.id.searchView)
        //search.addTextChangedListener

        /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
            menuInflater.inflate(R.menu.menu, menu)

            return super.onCreateOptionsMenu(menu)
        }*/

        //TODO SELECT PATIENT
        //PASSA AD PAGINA PAZIENTE
        val intentPage = Intent(this, PatientPageActivity::class.java)

        /*adapter.setOnItemClickListener(object : SelectPatientAdapter.onItemClickListener {
            override fun onClick(position: Int) {
                Toast.makeText(
                    this@SelectPatientListActivity,
                    "you clicked on patient $position",
                    Toast.LENGTH_SHORT
                ).show()

                var pos = position
                PatientsManager.selectedPatient = pos

                intentPage.putExtra("position", pos)
                startActivity(intentPage)

            }

        })

        if (!ConnectionManager.currDevice!!.isConnected()) {
            Toast.makeText(this@SelectPatientListActivity, "The pen disconnected!", Toast.LENGTH_SHORT)
                .show()
        }*/

        //AGGIUNGE PAZIENTE Al clinico
        val btnAddSelectedPatient = findViewById<Button>(R.id.addSelectedPatient)
        btnAddSelectedPatient.setOnClickListener {

        }

        adapter.setOnItemClickListener(object : SelectPatientAdapter.onItemClickListener {
            override fun onClick(position: Int) {
                Toast.makeText(
                    this@SelectPatientListActivity,
                    "you clicked on patient $position",
                    Toast.LENGTH_SHORT
                ).show()
                //TODO BOX CHECKED
                var pos = position
                PatientsManager.selectedPatient = pos

                intentPage.putExtra("position", pos)
                startActivity(intentPage)

            }

        })


 }





    }



//TODO c'è qualche errore che permette di scrivere dove non si dovrebbe quando si inseriscono
// i dati del pz: io sono riuscita a scrivere durante l'esecuzione dell'app nella scritta "Name"

