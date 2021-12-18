package com.example.cpsproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cpsproject.managers.PatientsManager
import com.example.cpsproject.managers.PatientsManager.patientsAllList
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_patient_all.*

class SelectPatientListActivity : AppCompatActivity() {
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var adapterGlobal: RecyclerView.Adapter<SelectPatientAdapter.ViewHolder>
    lateinit var rvPatients: RecyclerView

    //TODO NON SO SE BISOGNA RICHIAMARE ARRAY PAZIENTI PER VISUALIZZARE SUBITO LA LISTA--> in realtà basta importare in schermata 1

    // QUI X RECYCLER CHE SI AGGIORNA

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_patient_list)

        // GET USER

        var currentuser = FirebaseAuth.getInstance().getCurrentUser()?.getUid()
        var ID: String = String()
        if (currentuser != null) {
            ID = currentuser
        }

        // QUI X RECYCLER CHE SI AGGIORNA


        //listPatients=PatientsManager.importPatientList(this)
        val adapter = SelectPatientAdapter(this, patientsAllList)
        layoutManager = LinearLayoutManager(this)
        rvPatients = findViewById(R.id.rvPatients)
        rvPatients.layoutManager = layoutManager
        rvPatients.adapter = adapter

/*        adapter.setOnItemClickListener(object : SelectPatientAdapter.onItemClickListener {
            override fun onClick(position: Int) {
                Toast.makeText(
                    this@SelectPatientListActivity,
                    "you clicked on patient $position",
                    Toast.LENGTH_SHORT
                ).show()
                //TODO BOX CHECKED
                var pos = position
                PatientsManager.selectedPatient = pos

            }

        })*/

        adapter.setOnItemClickListener(object : PatientAdapter.onItemClickListener {
            override fun onClick(position: Int) {
                Toast.makeText(
                    this@SelectPatientListActivity,
                    "you clicked on patient $position",
                    Toast.LENGTH_SHORT
                ).show()

                var pos = position
                PatientsManager.selectedPatient = pos
            }

        })






        var intent= Intent(this, PatientListActivity::class.java)

        //AGGIUNGE ID
        val btnAddSelectedPatient = findViewById<Button>(R.id.addSelectedPatient)
        btnAddSelectedPatient.setOnClickListener {
            var pos:Int
            pos =0
            patientsAllList.forEach{ it->
                if(checkBox.isChecked){
                    it.cliniciansID.add(ID)
                    //PatientsManager.deletePatient(this,pos)
                    PatientsManager.addPatient(it, applicationContext)
                    pos=pos+1
                    //PatientsManager.patientsList.add(it)
                    patientsAllList.remove(it)
                    return@forEach
                }
                else {
                    pos = pos + 1
                }
            }
            startActivity(intent)
        }




 }





    }



//TODO c'è qualche errore che permette di scrivere dove non si dovrebbe quando si inseriscono
// i dati del pz: io sono riuscita a scrivere durante l'esecuzione dell'app nella scritta "Name"

