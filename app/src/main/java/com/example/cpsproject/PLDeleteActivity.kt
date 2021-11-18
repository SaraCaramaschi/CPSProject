package com.example.cpsproject

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cpsproject.managers.PatientsManager
import com.example.cpsproject.model.Patient



    class PLDeleteActivity : AppCompatActivity() {

        private lateinit var layoutManager: RecyclerView.LayoutManager
        private lateinit var adapter: RecyclerView.Adapter<PatientAdapter.ViewHolder>
        lateinit var rvPatients: RecyclerView
        var listPatients: ArrayList<Patient> = ArrayList()


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_patients_list)
            listPatients = PatientsManager.importPatientList(this)
            var adapter = PatientAdapter(this, listPatients)

            layoutManager = LinearLayoutManager(this)
            rvPatients = findViewById(R.id.rvPatients)
            rvPatients.layoutManager = layoutManager
            rvPatients.adapter = adapter
            Toast.makeText(this, "Select the patient that you want to delete", Toast.LENGTH_SHORT).show()

            val intentDelete = Intent(this, DeleteMessageActivity::class.java)
            adapter.setOnItemClickListener(object : PatientAdapter.onItemClickListener {
                override fun onClick(position: Int) {
                    Toast.makeText(this@PLDeleteActivity, "You have selected the patient", Toast.LENGTH_SHORT).show()
                    var pos = position
                    intentDelete.putExtra("position", pos)
                    startActivity(intentDelete)
                }
            }
            )




    }
}