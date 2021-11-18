package com.example.cpsproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cpsproject.managers.PatientsManager
import com.example.cpsproject.managers.PatientsManager.deletePatient
import com.example.cpsproject.model.Patient

class DeleteMessageActivity: AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_message)

        //VOLEVO FARE UNA ACTIVITY PER FAR COMPARIRE UN MESSAGGIO PER CHIDERE CONFERMA DELL'ELIMINAZIONE
        // poi ho visto che si può fare un "alert dialog"

        val intent= getIntent()
        val pos= intent.getIntExtra("position", 0)

//        var listPatients: ArrayList<Patient> = ArrayList()
//        listPatients = PatientsManager.importPatientList(this)

        val btnYes = findViewById<Button>(R.id.btnYes)
        val btnNo = findViewById<Button>(R.id.btnNo)
        btnNo.setOnClickListener{
            var intent= Intent(this,PatientListActivity::class.java)
            startActivity(intent)
        }
        btnYes.setOnClickListener{
            deletePatient(this, pos)
            Toast.makeText(this,"Patient deleted", Toast.LENGTH_SHORT).show()
            var intent= Intent(this, PatientListActivity::class.java)
            startActivity(intent)
           // PatientsManager.deletePatient(this@DeleteMessageActivity, pos)
        }


    }
}