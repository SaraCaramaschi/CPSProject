package com.example.cpsproject.ble

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cpsproject.R
import com.example.cpsproject.managers.PenManager
import kotlinx.android.synthetic.main.activity_ble_operationsprova.*
import kotlinx.android.synthetic.main.activity_ble_operationsprova.tvBatteryProva
import kotlinx.android.synthetic.main.activity_real_time.*

import timber.log.Timber


class RealTimeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_real_time)


        //tvBatteryProva.text= PenManager.battery.toString()
        Timber.d("batteriaaaaa:  %s", PenManager.battery.toString())
        btnRealBattery.setOnClickListener {
            tvRealBatteryProva.text = PenManager.battery.toString()
        }

        btnRealData.setOnClickListener {
            //tvRealDataProva.text = PenManager.Acc_x.toString()
        }

    }

    override fun onResume() {
        super.onResume()
        tvBatteryProva.text = PenManager.battery.toString()
    }

}
