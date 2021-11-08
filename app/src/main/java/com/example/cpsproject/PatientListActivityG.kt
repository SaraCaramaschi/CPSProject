package com.example.cpsproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_patients_list.*

class PatientListActivityG: AppCompatActivity(){
    private var layoutManager: RecyclerView.LayoutManager?=null
    private var adapter: RecyclerView.Adapter<PatientAdapterG.ViewHolder>?=null

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patients_list)

        layoutManager=LinearLayoutManager(this)
        rvPatients.layoutManager= layoutManager

        adapter=PatientAdapterG()
        rvPatients.adapter=adapter


    }
}