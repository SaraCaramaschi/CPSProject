package com.example.cpsproject

import android.annotation.SuppressLint
import android.content.Intent
//import android.graphics.drawable.Icon
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cpsproject.ble.MainConnection
import com.example.cpsproject.ble.PenActivity
import com.example.cpsproject.managers.LanguageManager
import com.example.cpsproject.managers.PatientsManager
import com.example.cpsproject.managers.PatientsManager.patientsList
import com.example.cpsproject.managers.PenManager
import com.example.cpsproject.managers.SessionManager
import com.example.cpsproject.model.Hand
import com.example.cpsproject.model.Patient
import com.example.cpsproject.model.Session
import com.google.firebase.auth.FirebaseAuth
import com.punchthrough.blestarterappandroid.ble.ConnectionManager
import com.punchthrough.blestarterappandroid.ble.ConnectionManager.isConnected
import kotlinx.android.synthetic.main.activity_add_patient.*
import kotlinx.android.synthetic.main.activity_add_patient.btnPatListBack
import kotlinx.android.synthetic.main.activity_patient_page.*
import timber.log.Timber
import java.time.LocalDateTime

class PatientPageActivity : AppCompatActivity() {
    var session: Session = Session()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_page)

        val intent = getIntent()
        val pos = intent.getIntExtra("position", 0)

        val patient = patientsList[pos]

        if (patient != null) {
            setupPatientPage(patient)
        }

        btnPatListBack.setOnClickListener {
            val intent = Intent(this, PatientListActivity::class.java)
            startActivity(intent)
        }

        btnPenConnectionPatient.setOnClickListener {
                val intent = Intent(this, MainConnection::class.java)
                startActivity(intent)
        }

        val btnPhase1 = findViewById<Button>(R.id.btnPhase1)
        btnPhase1.setOnClickListener {
            startSession(1)
            val intentConnection = Intent(this, ConnectionMessageActivity::class.java)
            intentConnection.putExtra("phase", 1)
            startActivity(intentConnection)

        }

        val btnPhase2 = findViewById<Button>(R.id.btnPhase2)
        btnPhase2.setOnClickListener {
            patient.phase = 2
            startSession(2)

            val intentConnection = Intent(this, ConnectionMessageActivity::class.java)
            intentConnection.putExtra("phase", 2)
            startActivity(intentConnection)
        }

        val edit = findViewById<ImageView>(R.id.edit)
        edit.setOnClickListener {
            val intentNew = Intent(this, EditPatientActivity::class.java)
            intentNew.putExtra("position", pos)
            startActivity(intentNew)
        }

        val delete = findViewById<ImageView>(R.id.delete)
        delete.setOnClickListener {
            val intentDelete = Intent(this, DeleteMessageActivity::class.java)
            intentDelete.putExtra("position", pos)
            startActivity(intentDelete)
        }


    }


    private fun setupPatientPage(patient: Patient) {
        val tvNome = findViewById<TextView>(R.id.tvName)
        val tvCognome = findViewById<TextView>(R.id.tvSurname)
        val tvNote = findViewById<TextView>(R.id.tvNotes)
        val tvComple = findViewById<TextView>(R.id.tvBirthDate)
        val tvGenere = findViewById<TextView>(R.id.tvGender)
        val tvHand = findViewById<TextView>(R.id.tvDominantHand)
        val tvTax = findViewById<TextView>(R.id.tvTax)
        val tvPhase = findViewById<TextView>(R.id.tvPhase)


        tvNome.setText(":" + " " + (patient.name).toString())
        tvCognome.setText(":" + " " + (patient.surname).toString())
        tvNote.setText(":" + " " + (patient.notes).toString())
        tvComple.setText(":" + " " + (patient.birthdate).toString())
        tvPhase.setText(":" + " " + patient.phase.toString())
        tvGenere.setText(":" + " " + patient.gender.toString())
        tvTax.setText(":" + " " + patient.taxcode.toString())

        if (patient.dominantHand==Hand.Left) {
            if (LanguageManager.language == 1) {
                tvHand.setText(":" + " " + "sinistra")
            } else {
                tvHand.setText(":" + " " + "left")
            }
        }
        else{
            if(LanguageManager.language==1){
                tvHand.setText(":" + " " + "destra")
            }else {
                tvHand.setText(":" + " " + "right")
            }
        }

    }

    @SuppressLint("NewApi")
    fun startSession(phase: Int) {
        session.device = PenManager.penName.toString()
        session.patientID = PatientsManager.selectedPatient.toString()
        session.phase = phase

        var mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser
        var clinicianID: String = String()

        if (currentUser != null) {
            clinicianID = currentUser.uid
        }
        session.clinicianID = clinicianID

        SessionManager.sessione = session
    }
}

