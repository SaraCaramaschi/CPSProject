package com.example.cpsproject


import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.cpsproject.managers.PatientsManager
import com.example.cpsproject.model.Gender
import com.example.cpsproject.model.Hand
import com.example.cpsproject.model.Patient
import kotlinx.android.synthetic.main.activity_add_patient.*
import kotlinx.android.synthetic.main.activity_add_patient.etName
import kotlinx.android.synthetic.main.activity_add_patient.etNotes
import kotlinx.android.synthetic.main.activity_add_patient.etSurname
import kotlinx.android.synthetic.main.activity_add_patient.etTax
import kotlinx.android.synthetic.main.activity_add_patient.spinnerDominantHand
import kotlinx.android.synthetic.main.activity_add_patient.spinnerGender
import kotlinx.android.synthetic.main.activity_edit_patient.*
import java.text.SimpleDateFormat
import java.util.*

class EditPatientActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_patient)
        val intent= getIntent()
        val pos= intent.getIntExtra("position", 0)

        val patient = PatientsManager.patientsList[pos]
        if (patient != null) {
            //Show patient data
            val etNome = findViewById<EditText>(R.id.etName)
            val etCognome = findViewById<EditText>(R.id.etSurname)
            val etNote = findViewById<EditText>(R.id.etNotes)
            val etComple = findViewById<TextView>(R.id.etBirthDateEdit)
            var spinGenere = findViewById<Spinner>(R.id.spinnerGender)
            var spinHand=findViewById<Spinner>(R.id.spinnerDominantHand)
            val etTax= findViewById<EditText>(R.id.etTax)

            etNome.setTextColor(Color.parseColor("#9999A1"))
            etCognome.setTextColor(Color.parseColor("#9999A1"))
            etNote.setTextColor(Color.parseColor("#9999A1"))
            etComple.setTextColor(Color.parseColor("#9999A1"))
            etTax.setTextColor(Color.parseColor("#9999A1"))

            var g: Int
            if (patient.gender.toString()=="Male"){
                g=0}
            else if (patient.gender.toString()=="Female"){
                g=1}
            else{
                g=2

            }
            var h: Int
            if(patient.dominantHand.toString()=="Right"){
                h=0
            }
            else{
                h=1
            }


            etNome.setText(patient.name.toString())
            etCognome.setText(patient.surname.toString())
            etNote.setText(patient.notes.toString())
            etComple.setText(patient.birthdate.toString())
            etTax.setText(patient.taxcode.toString())



            spinGenere.setSelection(g)
            spinHand.setSelection(h)


            //intanto utente può cambiare tutte le varie cose



        }



        val btnPatListBack= findViewById<Button>(R.id.btnPatListBack)
        btnPatListBack.setOnClickListener {
            val intent = Intent(this, PatientListActivity::class.java)
            startActivity(intent)
        }
        val btnEditPat= findViewById<Button>(R.id.btnEdit)
        btnEditPat.setOnClickListener{
            if (etName.text.toString().trim().isEmpty()) {
                //Toast.makeText(this, "Name required", Toast.LENGTH_SHORT).show();
                etName.error = "Name Required";
                return@setOnClickListener
            } else if (etSurname.text.toString().trim().isEmpty()) {
                etSurname.error = "Surname Required"
                return@setOnClickListener
            } else if (etTax.text.toString().trim().isEmpty()) {
                etTax.error = "Tax Code Required"
                return@setOnClickListener
            } else if (etTax.text.toString().trim().length != 16) {
                etTax.error = "Tax Code not correct"
                return@setOnClickListener
            } else if (etBirthDateEdit.text.toString().trim().length != 10) {
                etBirthDateEdit.error = "Birth Date not correct"
                return@setOnClickListener
           }

            val handEnum: Hand = when (spinnerDominantHand.selectedItem) {
                "left" -> {
                    Hand.Left
                }
                else -> {
                    Hand.Right
                }

            }

            val genderEnum: Gender = when (spinnerGender.selectedItem) {
                "male" -> {
                    Gender.Male
                }
                "female" -> {
                    Gender.Female
                }
                else -> {
                    Gender.Other
                }
            }

            // Riprendo tutte le variabili del patient
            var patient = Patient(
                etName.text.toString(), etSurname.text.toString(),
                etNotes.text.toString(), etTax.text.toString(), etBirthDateEdit.text.toString(), handEnum, genderEnum
            )

            //cancello vecchio paziente per creare nuovo file (domanda: lista mostre stesso ordine con cui salvato arrey della lista?)
           // PatientsManager.deletePatient(this, pos)
            PatientsManager.deletePatient(this,pos)
            // Add patient to patientlist
            //PatientsManager.patientsList[pos]==patient
            PatientsManager.addPatient(patient, applicationContext)

            //Aggiorna lista dei pazienti
            PatientsManager.patientsList = PatientsManager.importPatientList(this)


            // Go to patient page
            val intent = Intent(this, PatientPageActivity::class.java)
            val pos= PatientsManager.patientsList.lastIndex
            intent.putExtra("position",pos)
            //intent.putExtra("keyPatient", patient)
            startActivity(intent)
        }
        val myCalendar= Calendar.getInstance()
        val iconCalendar=findViewById<ImageView>(R.id.calendarEdit)

        val datePicker= DatePickerDialog.OnDateSetListener{ view, year, month, dayOfMonth->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth)
            updateLable(myCalendar)

        }
        iconCalendar.setOnClickListener{
            DatePickerDialog(this, datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(
                Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show()

        }

    }

    private fun updateLable(myCalendar: Calendar) {
        val myFormat="dd-MM-yyy"
        val etBirthDateEdit=findViewById<EditText>(R.id.etBirthDateEdit)
        val sdf= SimpleDateFormat(myFormat, Locale.UK)
        etBirthDateEdit.setText(sdf.format(myCalendar.time))
    }
}
//TODO PROBLEMA NON SI PUO' MODIFICARE DUE VOLTE LO STESSO PAZIENTE


