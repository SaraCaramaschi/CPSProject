package com.example.cpsproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.cpsproject.managers.PatientsManager
import com.example.cpsproject.model.Patient

class DeleleMessageActivity: AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_message)

        val intent= getIntent()
        val pos= intent.getIntExtra("position", 0)

        var listPatients: ArrayList<Patient> = ArrayList()
        listPatients = PatientsManager.importPatientList(this)

        val btnYes = findViewById<Button>(R.id.btnYes)
        val btnNo = findViewById<Button>(R.id.btnNo)
        btnNo.setOnClickListener{
            var intent= Intent(this,PatientListActivity::class.java)
            startActivity(intent)
        }
        btnYes.setOnClickListener{
                   listPatients.removeAt(pos)

        }


    }
}