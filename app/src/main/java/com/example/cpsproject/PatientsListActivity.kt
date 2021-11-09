package com.example.cpsproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
//<<<<<<< HEAD
import com.example.cpsproject.databinding.ActivityPatientsListBinding
//=======
import androidx.appcompat.app.AppCompatActivity
//>>>>>>> master
>>>>>>> master
import com.example.cpsproject.model.Patient

class PatientsListActivity : AppCompatActivity() {
//<<<<<<< HEAD
    private lateinit var binding:ActivityPatientsListBinding
    private lateinit var patientArrayList :ArrayList<Patient>
//=======
   // private lateinit var binding: ActivityPatientsListBinding
    //private lateinit var patientArrayList: ArrayList<Patient>
//>>>>>>> master
    //private lateinit var layoutMan: RecyclerView.LayoutManager
    //private lateinit var adapter: RecyclerView.Adapter<PatientAdapter.ViewHolder>
    // lateinit var rvPatients: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patients_list)
<<<<<<< HEAD
        binding= ActivityPatientsListBinding.inflate(layoutInflater)
//=======
       // binding = ActivityPatientsListBinding.inflate(layoutInflater)
>>>>>>> master
        setContentView(binding.root)

        //DA FARE SE VOGLIAMO LE FOTO
        // val imageId=intArray of
//TODO qui ci sarebbero da mettere tutte le variabili del pz
//<<<<<<< HEAD

        val name = arrayOf(
            "CArlo",
            "giovanna"
        )

        val phase = arrayOf(
            "phase1",
            "phase2"
        )

        patientArrayList = ArrayList()
        for (i in name.indices) {
            val patient = Patient(name[i], phase[i])
            patientArrayList.add(patient)

        }

        binding.rvPatients.isClickable = true
        binding.rvPatients.adapter = PatientAdapter(this, patientArrayList)
        //PatientAdapter(this, patientArrayList).also { binding.rvPatients.adapter = it } --> mi viene dato come suggerimento questa riga
        binding.rvPatients.setOnItemClickListener { parent, view, position, id ->
            val name = name[position]
            val phase = phase[position]
//=======
//        val name =arrayOf(
//            "CArlo",
//        "giovanna"
//        )
//       val phase = arrayOf(
//     "phase1",
// "phase2"
// )
//
//patientArrayList=ArrayList()
//        for ( i in name.indices) {
//            val patient = Patient(name[i], phase[i])
//            patientArrayList.add(patient)

//>>>>>>> master

//
//binding.patientslist.isClickable=true
//binding.patientslist.adapter= PatientAdapter2
//binding.patientslist.setOnItemClickListener { parent, view, position, id ->
//    val name = name[position]
//    val phase = phase[position]
//
//    val i = Intent(this, AddPatientActivity::class.java)
//    i.putExtra("name", name)
//    i.putExtra("phase", phase)
            //  startActivity(i)


            //TODO per INSERIRE LE VARIABILI NON A MANO COME FARE?

            //layoutMan = LinearLayoutManager(this)
            // rvPatients = findViewById<RecyclerView>(R.id.rvPatients)
            // rvPatients.layoutManager = layoutMan
            //adapter = PatientAdapter()
            //rvPatients.adapter = adapter

            val btnNewPatient = findViewById<Button>(R.id.btnNewPatient)
            btnNewPatient.setOnClickListener {
                val intent = Intent(this, AddPatientActivity::class.java)
                startActivity(intent)
            }

            //TODO setOnClickListener per entrare nella pagina del paziente
        }
    }
}
