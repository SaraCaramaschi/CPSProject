package com.example.cpsproject

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.example.cpsproject.managers.PatientsManager
import com.example.cpsproject.managers.PatientsManager.patientsList
import com.example.cpsproject.model.Gender
import com.example.cpsproject.model.Hand
import com.example.cpsproject.model.Patient
import kotlinx.android.synthetic.main.activity_add_patient.*
import java.text.SimpleDateFormat
import java.util.*


class AddPatientActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_patient)

        val name = intent.getStringExtra("name")
        val phase = intent.getStringExtra("phase")

        //aggiungere lo stesso per la foto se la vogliamo

        // TODO spinner
        // val gender: Gender
        //spinnerGender.adapter=ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,gender)
        //ArrayAdapter<CharSequence>() adapter=Arrayadapter.createfrom

        // Activity related to the button add patient, notifica OK !!!
        val btnAddPat = findViewById<Button>(R.id.btnAddPat)
        val gender = findViewById<Spinner>(R.id.spinnerGender)
        btnAddPat.setOnClickListener {
<<<<<<< Updated upstream
            // TODO togliere commenti vincoli
            if (etName.text.toString().trim().isEmpty()) {
               // Toast.makeText(this, "Name required", Toast.LENGTH_SHORT).show();
=======

            if (etName.text.toString().trim().isEmpty()) {
                //Toast.makeText(this, "Name required", Toast.LENGTH_SHORT).show();
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
//            } else if (etBirthDate.text.toString().trim().length != 10) {
//                etBirthDate.error = "Birth Date not correct"
//                return@setOnClickListener
//            }
=======
            } else if (etBirthDateAdd.text.toString().trim().length != 10) {
                etBirthDateAdd.error = "Birth Date not correct"
                return@setOnClickListener
            }
>>>>>>> Stashed changes

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
            // Variabile patient
            var patient = Patient(
                etName.text.toString(),
                etSurname.text.toString(),
                etNotes.text.toString(),
                etTax.text.toString(),
                etBirthDateAdd.text.toString(),
                handEnum,
                genderEnum
                //genderEnum
            ) // Local variable

            // Add patient to patientlist
            PatientsManager.addPatient(patient, applicationContext)

            // Go to patient page
            val intent = Intent(this, PatientPageActivity::class.java)
            val pos = patientsList.lastIndex
            intent.putExtra("position", pos)
            //intent.putExtra("keyPatient", patient)
            startActivity(intent)
        }

<<<<<<< Updated upstream
        /*btnReadPat.setOnClickListener {
            PatientsManager.readPatient(0, applicationContext)
            //PatientsManager.readPatient()
        }*/
=======
//        btnReadPat.setOnClickListener {
//            PatientsManager.readPatient(0, applicationContext)
//            //PatientsManager.readPatient()
//        }
>>>>>>> Stashed changes

        btnPatListBack.setOnClickListener {
            val intent = Intent(this, PatientListActivity::class.java)
            startActivity(intent)
        }

        val myCalendar= Calendar.getInstance()
        val iconCalendar=findViewById<ImageView>(R.id.calendar)

    val datePicker= DatePickerDialog.OnDateSetListener{view, year, month, dayOfMonth->
        myCalendar.set(Calendar.YEAR, year)
        myCalendar.set(Calendar.MONTH, month)
        myCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth)
//        updateLable(myCalendar)

    }
        iconCalendar.setOnClickListener{
            DatePickerDialog(this, datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show()

        }

}
/*
    private fun updateLable(myCalendar: Calendar) {
        val myFormat="dd-MM-yyy"
        val birthDateAdd=findViewById<EditText>(R.id.etBirthDateAdd)
        val sdf= SimpleDateFormat(myFormat, Locale.UK)
        birthDateAdd.setText(sdf.format(myCalendar.time))*/


    }
    }


// https://www.youtube.com/watch?v=y4npeX35B34 TOP VIDEOOOOOOOOOOOOOOOOOO, si ma per firebase
