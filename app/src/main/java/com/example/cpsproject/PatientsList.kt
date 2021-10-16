package com.example.cpsproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PatientsList : AppCompatActivity() {

    private lateinit var layoutMan: RecyclerView.LayoutManager
    private lateinit var adapter: RecyclerView.Adapter<PatientAdapter.ViewHolder>
    lateinit var rvPatients: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patients_list)

        layoutMan = LinearLayoutManager(this)
        rvPatients = findViewById<RecyclerView>(R.id.rvPatients)
        rvPatients.layoutManager = layoutMan
        adapter = PatientAdapter()
        rvPatients.adapter = adapter

        val btnAddPatient = findViewById<Button>(R.id.btnAddPatient)
        btnAddPatient.setOnClickListener {
            val intent = Intent(this, AddPatient::class.java)
            startActivity(intent) }
        }
    }
