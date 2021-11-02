package com.example.cpsproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cpsproject.databinding.ActivityMainConnectionBinding
import com.example.cpsproject.databinding.ActivityPatientsList4Binding
import com.example.cpsproject.model.Patient

class PatientsListActivity : AppCompatActivity() {
    private lateinit var binding:ActivityPatientsList4Binding
    private lateinit var patientArrayList :ArrayList<Patient>
    //private lateinit var layoutMan: RecyclerView.LayoutManager
    //private lateinit var adapter: RecyclerView.Adapter<PatientAdapter.ViewHolder>
   // lateinit var rvPatients: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patients_list)
        binding= ActivityPatientsList4Binding.inflate(layoutInflater)
        setContentView(binding.root)

        //DA FARE SE VOGLIAMO LE FOTO
        // val imageId=intArray of
//TODO qui ci sarebbero da mettere tutte le variabili del pz
        val name =arrayOf(
            "CArlo",
        "giovanna"
        )
       val phase = arrayOf(
     "phase1",
 "phase2"
 )

patientArrayList=ArrayList()
        for ( i in name.indices) {
            val patient = Patient(name[i], phase[i])
            patientArrayList.add(patient)

        }

binding.patientslist.isClickable=true
binding.patientslist.adapter= PatientAdapter2
binding.patientslist.setOnItemClickListener { parent, view, position, id ->
    val name = name[position]
    val phase = phase[position]

    val i = Intent(this, AddPatientActivity::class.java)
    i.putExtra("name", name)
    i.putExtra("phase", phase)
    startActivity(i)

}

        //TODO per INSERIRE LE VARIABILI NON A MANO COME FARE?

        //layoutMan = LinearLayoutManager(this)
       // rvPatients = findViewById<RecyclerView>(R.id.rvPatients)
       // rvPatients.layoutManager = layoutMan
        //adapter = PatientAdapter()
        //rvPatients.adapter = adapter

        val btnNewPatient = findViewById<Button>(R.id.btnNewPatient)
        btnNewPatient.setOnClickListener {
            val intent = Intent(this, AddPatientActivity::class.java)
            startActivity(intent) }
        }
    }
