package com.example.cpsproject

import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.cpsproject.ble.MainConnection
import com.example.cpsproject.managers.ClinicianManager
import com.example.cpsproject.managers.ClinicianManager.clinicianToPass
import com.example.cpsproject.managers.ClinicianManager.findClinician
import com.example.cpsproject.managers.PatientsManager
import com.example.cpsproject.managers.PatientsManager.checkPatientLocal
import com.example.cpsproject.model.Clinician
import com.example.cpsproject.model.Patient
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.home.*
import timber.log.Timber

class Schermata1Activity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)


        val mainHandler = Handler(Looper.getMainLooper())
        mainHandler.post(object : Runnable {
            override fun run() {
                if (clinicianToPass != null) {
                    tvClinician.text =
                        ClinicianManager.clinicianToPass!!.name + " " + ClinicianManager.clinicianToPass!!.surname
                    return
                } else {
                    mainHandler.postDelayed(this, 1000)
                }
            }
        })


        //importo subito lista pazioni da firebase
        var listPatients: ArrayList<Patient> = ArrayList()
        var currentuser = FirebaseAuth.getInstance().getCurrentUser()?.getUid()
        var ID: String = String()
        if (currentuser != null) {
            ID = currentuser
        }

        PatientsManager.patientsList = PatientsManager.getDocumentsPatient(this, ID)
        PatientsManager.patientsAllList = PatientsManager.getDocumentsAllPatient(this, ID)


        checkPatientLocal(this)


        // Log out
        val btnLogOut = findViewById<Button>(R.id.btnLogOut)
        btnLogOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        // Patient list
        val btnPatients = findViewById<Button>(R.id.btnPatients)
        btnPatients.setOnClickListener {
            val intent = Intent(this, PatientListActivity::class.java)
            startActivity(intent)
        }

        // Pen Connection
        val btnPen = findViewById<Button>(R.id.btnPenConnection)
        btnPen.setOnClickListener {
            val intent = Intent(this, MainConnection::class.java)
            intent.putExtra("flag", 1)
            startActivity(intent)
        }
    }
}