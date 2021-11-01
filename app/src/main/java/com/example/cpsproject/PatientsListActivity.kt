package com.example.cpsproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
//import com.example.cpsproject.databinding.ActivityMainConnectionBinding
//import com.example.cpsproject.databinding.ActivityPatientsList4Binding

class PatientsListActivity : AppCompatActivity() {

    //private lateinit var binding:ActivityPatientsList4Binding         CON QUESTO VIENE ERRORE
    private lateinit var layoutMan: RecyclerView.LayoutManager
    private lateinit var adapter: RecyclerView.Adapter<PatientAdapter.ViewHolder>
    lateinit var rvPatients: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patients_list)
        //binding= ActivityPatientsList4Binding.inflate(layoutInflater) CON QUESTO VIENE ERRORE
        //setContentView(binding.root)                                  CON QUESTO VIENE ERRORE

        //DA FARE SE VOGLIAMO LE FOTO
        // val imageId=intArray of

        //TODO per INSERIRE LE VARIABILI NON A MANO COME FARE?

        layoutMan = LinearLayoutManager(this)
        rvPatients = findViewById<RecyclerView>(R.id.rvPatients)
        rvPatients.layoutManager = layoutMan
        adapter = PatientAdapter()
        rvPatients.adapter = adapter

        val btnNewPatient = findViewById<Button>(R.id.btnNewPatient)
        btnNewPatient.setOnClickListener {
            val intent = Intent(this, AddPatientActivity::class.java)
            startActivity(intent) }
        }
    }
