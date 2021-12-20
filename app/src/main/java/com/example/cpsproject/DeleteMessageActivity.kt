package com.example.cpsproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cpsproject.managers.PatientsManager
import com.example.cpsproject.model.Patient

class DeleteMessageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_message)

        val intent = getIntent()
        val pos = intent.getIntExtra("position", 0)
        val patient = PatientsManager.patientsList[pos]

        val tvMessage = findViewById<TextView>(R.id.tvPatient)
        tvMessage.setText("${patient.name} ${patient.surname}")

        val btnYes = findViewById<Button>(R.id.btnYes)
        val btnNo = findViewById<Button>(R.id.btnNo)
        btnNo.setOnClickListener {
            var intent = Intent(this, PatientListActivity::class.java)
            startActivity(intent)
        }
        btnYes.setOnClickListener {
            PatientsManager.deletePatient(this, pos)
            Toast.makeText(this, "Patient deleted", Toast.LENGTH_SHORT).show()
            var intent = Intent(this, PatientListActivity::class.java)
            startActivity(intent)

        }


    }
    

}
