package com.example.cpsproject

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cpsproject.managers.PatientsManager
import com.example.cpsproject.model.Patient
import kotlinx.android.synthetic.main.activity_add_patient.*
import org.jetbrains.anko.toast
import timber.log.Timber


class AddPatientActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_patient)

        //TODO i text rossi qui sotto dovrebbero essere delle caselle di testo nella pagina del singolo pz
        chFemale.setOnCheckedChangeListener{buttonView, isChecked ->
            if(isChecked==true) {
                textview.setText("Female")
            }
            chMale.setOnCheckedChangeListener{buttonView, isChecked ->
                if(isChecked==true) {
                    textview.setText("Male")
                }
        }

        //TODO cambiare nome a uno dei due btnaddpat se no  --> dovrei averlo fatto ma verifica(ila)
        btnAddPat.setOnClickListener {
            PatientsManager.addPatient(
                Patient(etName.text.toString(), etSurname.text.toString()),
                etNotes.text.toString(), etTax.text.toString()
            )

        //TODO cambiare nome a uno dei due btnaddpat se no  --> dovrei averlo fatto ma verifica(ila). Mi sembra giusto(gine)
        btnAddPat.setOnClickListener{

            if (etName.text.toString().trim().isEmpty()) {
                Toast.makeText(this, "Name required", Toast.LENGTH_SHORT).show();
                //etName.error = "Name Required";
                return@setOnClickListener
            } else if(etSurname.text.toString().trim().isEmpty()) {
                etSurname.error = "Surname Required"
                return@setOnClickListener
            }else if (etTax.text.toString().trim().isEmpty()) {
                etTax.error = "Tax Code Required"
                return@setOnClickListener
            } else if (etTax.text.toString().trim().length!=16){
                etTax.error="Tax Code not correct"
                return@setOnClickListener
            }

        // TODO capire come mai non esce notifica


                PatientsManager.addPatient(
                    Patient(etName.text.toString(), etSurname.text.toString(),
                        etNotes.text.toString(), etTax.text.toString()))


            }





            Timber.d(PatientsManager.patientsList.elementAt(0).name)
            Timber.d(PatientsManager.patientsList.elementAt(0).surname)
            Timber.d(PatientsManager.patientsList.elementAt(0).notes)
            Timber.d(PatientsManager.patientsList.elementAt(0).taxcode)
            //TODO impostare un avviso se il taxcode non è composto da un tot di caratteri
            Timber.d(PatientsManager.patientsList.)

        }
    }



/*   // https://www.youtube.com/watch?v=y4npeX35B34 TOP VIDEOOOOOOOOOOOOOOOOOO, si ma per firebase
    private lateinit var binding: ActivityAddPatientBinding
    //private lateinit var aut: FirebaseAuth manca anche nelle dependencies
    private lateinit var databaseReference: DatabaseReference

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
        // https://www.youtube.com/watch?v=y4npeX35B34

        super.onCreate(savedInstanceState)

        binding = ActivityAddPatientBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //authentication * missing code
        databaseReference = FirebaseDatabase.getInstance().getReference("Patients")
        binding.btnAddPat.setOnClickListener {
            val name = binding.etName.text.toString()
            val surname = binding.etSurname.text.toString()
            val birthPlace = binding.etPlace.text.toString()
            val notes = binding.etNotes.text.toString()

            val patient = Patient(name,surname,birthPlace,notes)
            databaseReference.child().setValue(patient).addOnCompleteListener{
                if (it.isSuccessful){

                }else{
                    Toast.makeText(this@AddPatient, "Failed operation",Toast.LENGTH_SHORT).show()
                }
            }
        }




        // codice di prima:
        //setContentView(R.layout.activity_add_patient)
        //viewInitializations()
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
            val name = etName.text.toString()
            val surname = etSurname.text.toString()
            val taxCode = etTax.text.toString()
            val birthDate = etBirthDate.text.toString()
            val birthPlace = etBirthPlace.text.toString()
            val notes = etNotes.text.toString()
            Toast.makeText(this, "Patient added successfully ", Toast.LENGTH_SHORT).show()
            // Here you can call you API NON FUNZIONA QUESTO TOAST
        }
    }
*/
//}