package com.example.cpsproject

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.cpsproject.managers.PatientsManager
import com.example.cpsproject.managers.PatientsManager.patientsList
import com.example.cpsproject.model.Gender
import com.example.cpsproject.model.Hand
import com.example.cpsproject.model.Patient
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_add_patient.*
import java.text.SimpleDateFormat
import java.util.*
import com.punchthrough.blestarterappandroid.ble.ConnectionManager
import com.punchthrough.blestarterappandroid.ble.ConnectionManager.isConnected


class AddPatientActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_patient)

        val name = intent.getStringExtra("name")
        val phase = intent.getStringExtra("phase")


        // Activity related to the button add patient, notifica OK !!!
        val btnAddPat = findViewById<Button>(R.id.btnAddPat)
        val gender = findViewById<Spinner>(R.id.spinnerGender)
        btnAddPat.setOnClickListener {

            // TODO togliere commenti vincoli
            if (etName.text.toString().trim().isEmpty()) {
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
            } else if (etBirthDateAdd.text.toString().trim().length != 10) {
                etBirthDateAdd.error = "Birth Date not correct"
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

            var mAuth = FirebaseAuth.getInstance()
            val currentUser = mAuth.currentUser
            var clinicianID:String= String()

            if (currentUser != null) {
                clinicianID= currentUser.uid
            }


            // Variabile patient
            var patient = Patient(
                etName.text.toString(),
                etSurname.text.toString(),
                etNotes.text.toString(),
                etTax.text.toString(),
                etBirthDateAdd.text.toString(),
                handEnum,
                genderEnum,
            )
/*            if (!ConnectionManager.currDevice!!.isConnected()) {
            patient.cliniciansID?.add(clinicianID)
                Toast.makeText(this@AddPatientActivity, "The pen disconnected!", Toast.LENGTH_SHORT)
                    .show()
            }*/
            // Add patient to patientlist
            PatientsManager.addPatient(patient, applicationContext)

            // Go to patient page
            val intent = Intent(this, PatientPageActivity::class.java)
            val pos = patientsList.lastIndex
            PatientsManager.selectedPatient = pos

            intent.putExtra("position", pos)
            startActivity(intent)
        }

        


        btnPatListBack.setOnClickListener {
            val intent = Intent(this, PatientListActivity::class.java)
            startActivity(intent)
        }

        val myCalendar = Calendar.getInstance()
        val iconCalendar = findViewById<ImageView>(R.id.calendar)

        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLable(myCalendar)

        }
        iconCalendar.setOnClickListener {
            DatePickerDialog(
                this,
                datePicker,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()

        }

    }

    private fun updateLable(myCalendar: Calendar) {
        val myFormat = "dd-MM-yyy"
        val birthDateAdd = findViewById<EditText>(R.id.etBirthDateAdd)
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        birthDateAdd.setText(sdf.format(myCalendar.time))


    }
}




