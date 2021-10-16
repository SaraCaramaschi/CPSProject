package com.example.cpsproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast

class AddPatient : AppCompatActivity() {

    lateinit var etName: EditText
    lateinit var etSurname:EditText
    lateinit var etBirthDate: EditText
    lateinit var etBirthPlace:EditText
    lateinit var etNotes:EditText
    lateinit var etTax:EditText
    init {
        var etFemaleGender:Boolean = false
        var etLeftHanded:Boolean = false
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_patient)
        viewInitializations()
    }

    fun viewInitializations() {
        etName = findViewById(R.id.etName)
        etSurname= findViewById(R.id.etSurname)
        etBirthPlace = findViewById(R.id.etPlace)
        etBirthDate = findViewById(R.id.etBirthDate)
        etNotes= findViewById(R.id.etNotes)
        etTax = findViewById(R.id.etTax)
    }

    // Checking if the input in form is valid
    fun validateInput(): Boolean {
        if (etName.text.toString().equals("")) {
            etName.setError("Please Enter First Name")
            return false
        }
        if (etSurname.text.toString().equals("")) {
            etSurname.setError("Please Enter Surname")
            return false
        }
        if (etBirthDate.text.toString().equals("")) {
            etBirthDate.setError("Please Enter Birth Date in correct form")
            return false
        }
        if (etBirthPlace.text.toString().equals("")) {
            etBirthPlace.setError("Please Enter Birth Place")
            return false
        }
        if (etTax.text.toString().equals("")) {
            etTax.setError("Please Enter Tax Code")
            return false
        }
        return true // ritorna 1 se l'input va bene altrimenti 0
    }

    fun performAddPatient(view: View) {
        if (validateInput()) {  // Input is valid, here send data to your server
            val Name = etName.text.toString()
            val Surname = etSurname.text.toString()
            val TaxCode = etTax.text.toString()
            val BirthDate = etBirthDate.text.toString()
            val BirthPlace = etBirthPlace.text.toString()
            val extraNotes = etNotes.text.toString()
            Toast.makeText(this, "Patient added successfully ", Toast.LENGTH_SHORT).show()
            // Here you can call you API NON FUNZIONA QUESTO TOAST
        }
    }

}