package com.example.cpsproject

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cpsproject.managers.ClinicianManager
import com.example.cpsproject.managers.PatientsManager
import com.example.cpsproject.managers.PatientsManager.getDocumentsAllPatient
import com.example.cpsproject.model.Patient
import com.google.firebase.auth.FirebaseAuth
import com.punchthrough.blestarterappandroid.ble.ConnectionManager
import com.punchthrough.blestarterappandroid.ble.ConnectionManager.isConnected
import timber.log.Timber
import android.os.AsyncTask




class PatientListActivity : AppCompatActivity() {
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var adapterGlobal: RecyclerView.Adapter<PatientAdapter.ViewHolder>
    lateinit var rvPatients: RecyclerView

    //TODO NON SO SE BISOGNA RICHIAMARE ARRAY PAZIENTI PER VISUALIZZARE SUBITO LA LISTA--> in realtà basta importare in schermata 1

    // QUI X RECYCLER CHE SI AGGIORNA
    var listPatients: ArrayList<Patient> = ArrayList()
    var listAllPatients: ArrayList<Patient> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patients_list)


        // GET USER

        var currentuser = FirebaseAuth.getInstance().getCurrentUser()?.getUid()
        var ID: String = String()
        if (currentuser != null) {
            ID = currentuser
        }

        // QUI X RECYCLER CHE SI AGGIORNA
        //Importare pazienti da firebase

        //listPatients=PatientsManager.importPatientList(this)
        val adapter = PatientAdapter(this, PatientsManager.patientsList)
        layoutManager = LinearLayoutManager(this)
        rvPatients = findViewById(R.id.rvPatients)
        rvPatients.layoutManager = layoutManager
        rvPatients.adapter = adapter



        //PASSA AD PAGINA PAZIENTE
        val intentPage = Intent(this, PatientPageActivity::class.java)

        adapter.setOnItemClickListener(object : PatientAdapter.onItemClickListener {
            override fun onClick(position: Int) {
                /*Toast.makeText(
                    this@PatientListActivity,
                    "you clicked on patient $position",
                    Toast.LENGTH_SHORT
                ).show()*/

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

        //Bottone per vedere tutti i pazienti
        /*val btnAllPatient = findViewById<Button>(R.id.btnAllPatients)
        btnAllPatient.setOnClickListener {

           /* val mainHandler = Handler(Looper.getMainLooper())
            mainHandler.post(object: Runnable {
                override fun run() {
                    if (listPatients != null) {*/

                        listAllPatients = getDocumentsAllPatient(this@PatientListActivity,ID)

                        //update(listAllPatients)
                        var adapterAll = PatientAdapter(this@PatientListActivity, listAllPatients)
                        layoutManager = LinearLayoutManager(this@PatientListActivity)
                        rvPatients = findViewById(R.id.rvPatients)
                        rvPatients.layoutManager = layoutManager
                        rvPatients.adapter = adapterAll

     /*                   adapterAll.setOnItemClickListener(object :
                            PatientAdapter.onItemClickListener {
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

                        })*/
//                       return
//                    } else {
//                        mainHandler.postDelayed(this, 1000)
//                    }
//                }
  //          })

        }*/



           /* listPatients = getDocumentsAllPatient(this)
//update(listAllPatients)
            var adapterAll = PatientAdapter(this, listPatients)
            layoutManager = LinearLayoutManager(this)
            rvPatients = findViewById(R.id.rvPatients)
            rvPatients.layoutManager = layoutManager
            rvPatients.adapter = adapterAll

            adapterAll.setOnItemClickListener(object : PatientAdapter.onItemClickListener {
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

            })*/





        //Bottone per vedere tutti i pazienti e selezionare i nuovi del clinico
        val btnAllPatientToSelect = findViewById<Button>(R.id.btnAllPatientsToSelect)
        val intentSelectPatent=Intent(this, SelectPatientListActivity::class.java)
        btnAllPatientToSelect.setOnClickListener {
            startActivity(intentSelectPatent)
        }
        }





    }


//TODO c'è qualche errore che permette di scrivere dove non si dovrebbe quando si inseriscono
// i dati del pz: io sono riuscita a scrivere durante l'esecuzione dell'app nella scritta "Name"
