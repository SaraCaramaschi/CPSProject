package com.example.cpsproject

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.cpsproject.ble.MainConnection
import com.example.cpsproject.managers.ClinicianManager
import com.example.cpsproject.managers.ClinicianManager.findClinician
import com.example.cpsproject.managers.PatientsManager
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
        val email=intent.getStringExtra("email_id")
        var clinicianLog=Clinician()

        if (email!=null){
       clinicianLog=findClinician(email, this)
        }


        tvClinician.setText((clinicianLog.name).toString()+(clinicianLog.surname).toString())
        Timber.d("FUNZIONAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA:%s",clinicianLog.name)

        //TODO NON FUNZIONA
        //importo subito lista pazioni da firebase
        var listPatients: ArrayList<Patient> = ArrayList()
        var currentuser = FirebaseAuth.getInstance().getCurrentUser()?.getUid()
        var ID:String=String()
        if (currentuser != null) {
            ID = currentuser
        }

        listPatients = PatientsManager.getDocumentsPatient(this, ID)



//PROVA PER CLINICO ILA MA I PROSSIMI DUE COMMENTI POSSONO ESSERE CANCELLATI
      //  val tvClinician=findViewById<TextView>(R.id.tvClinician)
       // tvClinician.setText("Doctor  ${etEmailLog} ")

        // bottone LOG OUT
        val btnLogOut= findViewById<Button>(R.id.btnLogOut)
        btnLogOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        // bottone LISTA PAZIENTI
        intent.putExtra("listPatients",listPatients)
        val btnPatients = findViewById<Button>(R.id.btnPatients)
        btnPatients.setOnClickListener {
            val intent = Intent(this, PatientListActivity::class.java)
            startActivity(intent)
        }

        // bottone CONNESSIONE PENNA
        val btnConnection = findViewById<Button>(R.id.btnPenConnection)
        btnConnection.setOnClickListener {
            val intent = Intent(this, MainConnection::class.java)
            startActivity(intent)
        }
    }
}