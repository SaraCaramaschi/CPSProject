package com.example.cpsproject

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cpsproject.model.Patient

class PatientPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_page)

        val patient = intent.getParcelableExtra<Patient>("keyPatient")
        if (patient != null) {
            setupPatientPage(patient)
        }

    }

    private fun setupPatientPage(patient: Patient) {
        val tvNome = findViewById<TextView>(R.id.tvName)
        val tvCognome = findViewById<TextView>(R.id.tvSurname)
        val tvNote = findViewById<TextView>(R.id.tvNotes)
        val tvComple = findViewById<TextView>(R.id.tvBirthDate)

        // inserire anche phase (default a 1) ERRORE CON LA FASE !!!
        //val tvFase = findViewById<TextView>(R.id.tvTax)


        tvNome.setText(patient.name).toString()
        tvCognome.setText(patient.surname).toString()
        tvNote.setText(patient.notes).toString()
        tvComple.setText(patient.birthdate).toString()
        //tvFase.setText(patient.phase.toString())
    }
}

// TODO Dare possibilità al clinico di modificare note?