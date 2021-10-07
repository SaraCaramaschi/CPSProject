package com.example.cpsproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PatientsList : AppCompatActivity() {
    private var layoutMan: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<PatientAdapter.ViewHolder>? = null
    var rvPatients = findViewById<RecyclerView>(R.id.rvPatients)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patients_list)

        val btnAddPatient = findViewById<Button>(R.id.btnAddPatient)
        btnAddPatient.setOnClickListener {
            val intent = Intent(this, Patient::class.java)
            startActivity(intent)
        }

        layoutMan = LinearLayoutManager(this)
        rvPatients.layoutManager = layoutMan
        adapter = PatientAdapter()
        rvPatients.adapter = adapter

    }
}