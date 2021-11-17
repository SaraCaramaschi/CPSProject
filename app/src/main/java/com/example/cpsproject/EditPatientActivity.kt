package com.example.cpsproject


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
            val etComple = findViewById<TextView>(R.id.etBirthDate)
            val spinGenere = findViewById<Spinner>(R.id.spinnerGender)
            val spinHand=findViewById<Spinner>(R.id.spinnerDominantHand)
            val etTax= findViewById<EditText>(R.id.etTax)

            etNome.setTextColor(Color.parseColor("#9999A1"))
            etCognome.setTextColor(Color.parseColor("#9999A1"))
            etNote.setTextColor(Color.parseColor("#9999A1"))
            etComple.setTextColor(Color.parseColor("#9999A1"))
            etTax.setTextColor(Color.parseColor("#9999A1"))

            etNome.setText(patient.name.toString())
            etCognome.setText(patient.surname.toString())
            etNote.setText(patient.notes.toString())
            etComple.setText(patient.birthdate.toString())
            etTax.setText(patient.taxcode.toString())


            //spinGenere
            //spinHand

            //intanto utente può cambiare tutte le varie cose



        }



        val btnPatListBack= findViewById<Button>(R.id.btnPatListBack)
        btnPatListBack.setOnClickListener {
            val intent = Intent(this, PatientListActivity::class.java)
            startActivity(intent)
        }
        val btnEditPat= findViewById<Button>(R.id.btnEdit)
        btnEditPat.setOnClickListener{
            // TODO togliere commenti vincoli
//            if (etName.text.toString().trim().isEmpty()) {
//                //Toast.makeText(this, "Name required", Toast.LENGTH_SHORT).show();
//                etName.error = "Name Required";
//                return@setOnClickListener
//            } else if (etSurname.text.toString().trim().isEmpty()) {
//                etSurname.error = "Surname Required"
//                return@setOnClickListener
//            } else if (etTax.text.toString().trim().isEmpty()) {
//                etTax.error = "Tax Code Required"
//                return@setOnClickListener
//            } else if (etTax.text.toString().trim().length != 16) {
//                etTax.error = "Tax Code not correct"
//                return@setOnClickListener
//            } else if (etBirthDate.text.toString().trim().length != 10) {
//                etBirthDate.error = "Birth Date not correct"
//                return@setOnClickListener
//            }

            val handEnum: Hand = when (spinnerDominantHand.selectedItem) {
                "Left" -> {
                    Hand.Left
                }
                else -> {
                    Hand.Right
                }

            }

            val genderEnum: Gender = when (spinnerGender.selectedItem) {
                "Male" -> {
                    Gender.Male
                }
                "Female" -> {
                    Gender.Female
                }
                else -> {
                    Gender.Other
                }
            }

            // Riprendo tutte le variabili del patient
            var patient = Patient(
                etName.text.toString(), etSurname.text.toString(),
                etNotes.text.toString(), etTax.text.toString(), etBirthDate.text.toString(), handEnum, genderEnum
                //genderEnum
            )

            //cancello vecchio paziente per creare nuovo file (domanda: lista mostre stesso ordine con cui salvato arrey della lista?)
           // PatientsManager.deletePatient(this, pos)
            PatientsManager.deletePatient(this,pos)
            // Add patient to patientlist
            PatientsManager.addPatient(patient, applicationContext)



            // Go to patient page
            val intent = Intent(this, PatientPageActivity::class.java)
            val pos= PatientsManager.patientsList.lastIndex
            intent.putExtra("position",pos)
            //intent.putExtra("keyPatient", patient)
            startActivity(intent)
        }
        }




    }


