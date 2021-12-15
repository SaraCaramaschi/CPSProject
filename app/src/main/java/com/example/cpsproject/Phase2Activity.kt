package com.example.cpsproject

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import com.example.cpsproject.managers.PatientsManager
import com.example.cpsproject.managers.PenManager
import com.example.cpsproject.managers.SessionManager
import com.example.cpsproject.model.Session
import com.google.firebase.auth.FirebaseAuth
import com.punchthrough.blestarterappandroid.ble.ConnectionManager
import com.punchthrough.blestarterappandroid.ble.ConnectionManager.isConnected

class Phase2Activity: AppCompatActivity() {
    var session: Session = Session()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phase_2)

        val tvDescription2 = findViewById<TextView>(R.id.tvDescription2)

        tvDescription2.setText("In this phase the patient has to do the 3 exercises once a month. \n"+
        "In the personal page of the patient there is the progression of the phase 2. \n"+"When an exercise is completed a MARK will appear.")

        var phase = 2
        startSession(phase)

        val btn1= findViewById<Button>(R.id.btnEx1Ph2)
        btn1.setOnClickListener {
            val intent = getIntent()
            val phase = intent.getIntExtra("phase",1)

            val intent1 = Intent(this, Exercise1Activity::class.java)
            intent1.putExtra("phase",2)
            startActivity(intent1)

        }
        if (!ConnectionManager.currDevice!!.isConnected()) {
            Toast.makeText(this@Phase2Activity, "The pen disconnected!", Toast.LENGTH_SHORT)
                .show()
        }
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
