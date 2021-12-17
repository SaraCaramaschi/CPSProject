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
import kotlinx.android.synthetic.main.activity_schermata1.*
import timber.log.Timber

class Schermata1Activity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schermata1)

        //val userId=intent.getStringExtra("user_id")
        /*   val email=intent.getStringExtra("email_id")
           val name=intent.getStringExtra("name")
           val surname=intent.getStringExtra("surname")
   */
        // TOP QUESTO MI DA LA MAIL
        /* Timber.d("LA MAIL e': %s", ClinicianManager.email)

         tvClinician.text = ClinicianManager.email*/

        val mainHandler = Handler(Looper.getMainLooper())
        mainHandler.post(object: Runnable {
            override fun run() {
                if (clinicianToPass != null) {
                    tvClinician.text =
                        ClinicianManager.clinicianToPass!!.name + " " + ClinicianManager.clinicianToPass!!.surname
                    return
                }
                else {
                    mainHandler.postDelayed(this, 1000)
                }
            }
        })




        //TODO NON FUNZIONA
        //importo subito lista pazioni da firebase
        var listPatients: ArrayList<Patient> = ArrayList()
        var currentuser = FirebaseAuth.getInstance().getCurrentUser()?.getUid()
        var ID: String = String()
        if (currentuser != null) {
            ID = currentuser
        }

        //listPatients = PatientsManager.getDocumentsPatient(this, ID)
        PatientsManager.patientsList = PatientsManager.getDocumentsPatient(this, ID)

        //TODO CONTROLLO LOCALE + CARICO SU DATABSE
        checkPatientLocal(this)


//PROVA PER CLINICO ILA MA I PROSSIMI DUE COMMENTI POSSONO ESSERE CANCELLATI
        //  val tvClinician=findViewById<TextView>(R.id.tvClinician)
        // tvClinician.setText("Doctor  ${etEmailLog} ")

        // bottone LOG OUT
        val btnLogOut = findViewById<Button>(R.id.btnLogOut)
        btnLogOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        // bottone LISTA PAZIENTI
        intent.putExtra("listPatients", listPatients)
        val btnPatients = findViewById<Button>(R.id.btnPatients)
        btnPatients.setOnClickListener {
            val intent = Intent(this, PatientListActivity::class.java)
            startActivity(intent)
        }

        val btnPen = findViewById<Button>(R.id.btnPenConnection)
        btnPen.setOnClickListener {
            val intent = Intent(this, MainConnection::class.java)
            startActivity(intent)
        }
    }
}