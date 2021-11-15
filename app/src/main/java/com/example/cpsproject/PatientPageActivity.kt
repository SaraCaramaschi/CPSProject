package com.example.cpsproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cpsproject.ble.MainConnection
import com.example.cpsproject.managers.PatientsManager.patientsList
import com.example.cpsproject.model.Patient
import kotlinx.android.synthetic.main.activity_add_patient.*
import kotlinx.android.synthetic.main.activity_add_patient.btnPatListBack
import kotlinx.android.synthetic.main.activity_patient_page.*

class PatientPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_page)
        val intent= getIntent()
        val pos= intent.getIntExtra("position", 0)

        //val patient = intent.getParcelableExtra<Patient>("keyPatient")
        val patient = patientsList[pos]
        if (patient != null) {
            setupPatientPage(patient)
        }

        btnPatListBack.setOnClickListener {
            val intent = Intent(this, PatientListActivity::class.java)
            startActivity(intent)
        }

        btnPenConnectionPatient.setOnClickListener {
            /*
            // MANDIAMO ALLA PAGINA:
                // SE C'è LA CONNESSIONE CON PENACITIVTY (PAGINA DELLA PENNA)
                // SE NON C'è LA CONNESSIONE CON MAINCONNECTION (BLE)

            if (PenIsConnected()){
                val intent = Intent(this, PenActivity::class.java)
            }else{
                val intent = Intent(this, MainConnection::class.java)
                }

            startActivity(intent)
            */
        }
        //stavo creando il collegamento con le activity

        val btnPhase1 = findViewById<Button>(R.id.btnPhase1)
        btnPhase1.setOnClickListener {
            val intent = Intent(this, Phase1Activity::class.java)
            startActivity(intent)
        }

        val btnPhase2 = findViewById<Button>(R.id.btnPhase2)
        btnPhase2.setOnClickListener {
            val intent = Intent(this, Phase2Activity::class.java)
            startActivity(intent)


        }

    }

    private fun setupPatientPage(patient: Patient) {
        val tvNome = findViewById<TextView>(R.id.tvName)
        val tvCognome = findViewById<TextView>(R.id.tvSurname)
        val tvNote = findViewById<TextView>(R.id.tvNotes)
        val tvComple = findViewById<TextView>(R.id.tvBirthDate)
        val tvGenere = findViewById<TextView>(R.id.tvGender)
        // inserire anche phase (default a 1) ERRORE CON LA FASE !!!
        //val tvFase = findViewById<TextView>(R.id.tvTax)

        tvNome.setText("Name:"+ " "+(patient.name).toString())
        tvCognome.setText("Surname:"+ " "+(patient.surname).toString())
        tvNote.setText("Note:"+ " "+(patient.notes).toString())
        tvComple.setText("Birth Date:"+ " "+(patient.birthdate).toString())
        tvPhase.setText("Phase:"+ " "+patient.phase.toString())
        tvGenere.setText("Gender:"+ " "+patient.gender.toString())
    }




        }


// TODO Dare possibilità al clinico di modificare note?