package com.example.cpsproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil.*
import com.example.cpsproject.databinding.ActivityLanguageBinding
import com.example.cpsproject.managers.LanguageManager
import kotlinx.android.synthetic.main.activity_language.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.btnLanguage
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

class LanguageActivity : AppCompatActivity() {

    lateinit var databinding: ActivityLanguageBinding
    lateinit var locale : Locale

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databinding = ActivityLanguageBinding.inflate(layoutInflater)
        setContentView(databinding.root)

        btnBack.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        var languageList = ArrayList<String>()
        languageList.add("Select")
        languageList.add("Italian")
        languageList.add("English")

        var languageAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, languageList)
        databinding.spinnerLanguage.adapter = languageAdapter
        databinding.spinnerLanguage.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when(position){
                    0 -> { }
                    1 -> { setLocale("it") }
                    2 -> { setLocale("en")
                    Timber.d("You selected italian")}
                }
                LanguageManager.language=position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }
    fun setLocale(languageName: String){
        locale = Locale(languageName)
        var res = resources
        var dm = res.displayMetrics
        var conf = res.configuration
        conf.locale = locale
        res.updateConfiguration(conf, dm)

        var refresh = Intent(LanguageActivity@this, LanguageActivity::class.java)
        startActivity(refresh)
    }
}