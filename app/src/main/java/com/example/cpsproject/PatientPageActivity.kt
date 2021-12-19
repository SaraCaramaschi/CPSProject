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
import com.example.cpsproject.managers.PatientsManager
import com.example.cpsproject.managers.PatientsManager.patientsList
import com.example.cpsproject.managers.PenManager
import com.example.cpsproject.managers.SessionManager
import com.example.cpsproject.model.Patient
import com.example.cpsproject.model.Session
import com.google.firebase.auth.FirebaseAuth
import com.punchthrough.blestarterappandroid.ble.ConnectionManager
import com.punchthrough.blestarterappandroid.ble.ConnectionManager.isConnected
import kotlinx.android.synthetic.main.activity_add_patient.*
import kotlinx.android.synthetic.main.activity_add_patient.btnPatListBack
import kotlinx.android.synthetic.main.activity_patient_page.*
import java.time.LocalDateTime

class PatientPageActivity : AppCompatActivity() {
    var session: Session = Session()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_page)

        val intent= getIntent()
        val pos= intent.getIntExtra("position", 0)
      /*  if (!ConnectionManager.currDevice!!.isConnected()) {
            Toast.makeText(this@PatientPageActivity, "The pen disconnected!", Toast.LENGTH_SHORT)
                .show()
        }*/
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
            if (ConnectionManager.currDevice!!.isConnected()){
                val intent = Intent(this, PenActivity::class.java)
                startActivity(intent)
            }else{
                val intent = Intent(this, MainConnection::class.java)
                startActivity(intent)
            }
        }

        val btnPhase1 = findViewById<Button>(R.id.btnPhase1)
        btnPhase1.setOnClickListener {
            //val intent1 = Intent(this, Phase1Activity::class.java)
            //startActivity(intent1)
            // bottone CONNESSIONE PENNA
            var phase = 1
            startSession(phase)

            val intent = Intent(this, MainConnection::class.java)
            startActivity(intent)
        }

        val btnPhase2 = findViewById<Button>(R.id.btnPhase2)
        btnPhase2.setOnClickListener {
            patient.phase = 2
            //TODO: editphase (in modo che si salvi anche online)
            editPhase()

            var phase = 2
            startSession(phase)
            val intent2 = Intent(this, Phase2Activity::class.java)
            intent2.putExtra("phase",2)
            startActivity(intent2)
        }
//commento
        val edit= findViewById<ImageView>(R.id.edit)
        edit.setOnClickListener{
            val intentNew= Intent(this, EditPatientActivity::class.java)
            intentNew.putExtra("position",pos)
            startActivity(intentNew)
        }

        val delete= findViewById<ImageView>(R.id.delete)
        delete.setOnClickListener{
            val intentDelete= Intent(this, DeleteMessageActivity::class.java)
            intentDelete.putExtra("position",pos)
            startActivity(intentDelete)
        }


    }

    private fun editPhase() {
        TODO("Not yet implemented")
    }

    private fun setupPatientPage(patient: Patient) {
        val tvNome = findViewById<TextView>(R.id.tvName)
        val tvCognome = findViewById<TextView>(R.id.tvSurname)
        val tvNote = findViewById<TextView>(R.id.tvNotes)
        val tvComple = findViewById<TextView>(R.id.tvBirthDate)
        val tvGenere = findViewById<TextView>(R.id.tvGender)
        val tvHand=findViewById<TextView>(R.id.tvDominantHand)
        // inserire anche phase (default a 1) ERRORE CON LA FASE !!!
        val tvTax = findViewById<TextView>(R.id.tvTax)
        val tvPhase=findViewById<TextView>(R.id.tvPhase)

        tvNome.setText("Name:"+ " "+(patient.name).toString())
        tvCognome.setText("Surname:"+ " "+(patient.surname).toString())
        tvNote.setText("Note:"+ " "+(patient.notes).toString())
        tvComple.setText("Birth Date:"+ " "+(patient.birthdate).toString())
        tvPhase.setText("Phase:"+ " "+patient.phase.toString())
        tvGenere.setText("Gender:"+ " "+patient.gender.toString())
        tvHand.setText("Dominant Hand:"+" "+patient.dominantHand.toString())
        tvTax.setText("Tax code:"+" "+patient.taxcode.toString())
    }

    @SuppressLint("NewApi")
    fun startSession(phase:Int) {
        session.device = PenManager.penName.toString()
        session.patientID = PatientsManager.selectedPatient.toString()
        session.phase = phase

        var mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser
        var clinicianID:String= String()

        if (currentUser != null) {
            clinicianID= currentUser.uid
        }
        session.clinicianID = clinicianID

        SessionManager.sessione = session
    }



}

