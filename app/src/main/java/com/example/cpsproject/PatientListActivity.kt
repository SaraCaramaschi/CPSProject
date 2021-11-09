package com.example.cpsproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_patients_list.*

class PatientListActivity: AppCompatActivity(){
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var adapter: RecyclerView.Adapter<PatientAdapter.ViewHolder>
    lateinit var rvPatients: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patients_list)

        layoutManager=LinearLayoutManager(this)
        rvPatients=findViewById<RecyclerView>(R.id.rvPatients)
        rvPatients.layoutManager= layoutManager

        adapter=PatientAdapter()
        rvPatients.adapter=adapter

        val btnAddPatient = findViewById<Button>(R.id.btnNewPatient)
        btnAddPatient.setOnClickListener {
            val intent = Intent(this, AddPatientActivity::class.java)
            startActivity(intent) }




    }
}