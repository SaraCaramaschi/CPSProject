package com.example.cpsproject.ble

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.cpsproject.R
import com.example.cpsproject.managers.PenManager
import com.punchthrough.blestarterappandroid.ble.ConnectionManager
import com.punchthrough.blestarterappandroid.ble.ConnectionManager.currDevice
import com.punchthrough.blestarterappandroid.ble.ConnectionManager.dataChar
import kotlinx.android.synthetic.main.activity_ble_operationsprova.*
import kotlinx.android.synthetic.main.activity_ble_operationsprova.tvBatteryProva
import kotlinx.android.synthetic.main.activity_pen.*
import kotlinx.android.synthetic.main.activity_real_time.*

import timber.log.Timber
import kotlinx.android.synthetic.main.activity_real_time.tvAccx as tvAccx1


class RealTimeActivity : AppCompatActivity() {

    lateinit var mainHandler:Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_real_time)

        mainHandler = Handler(Looper.getMainLooper())

        //tvBatteryProva.text= PenManager.battery.toString()
        Timber.d("batteriaaaaa:  %s", PenManager.battery.toString())
        btnRealBattery.setOnClickListener {
            tvRealBatteryProva.text = PenManager.battery.toString()
        }

        btnStartRealTime.setOnClickListener {
            ConnectionManager.enableNotifications(currDevice!!, dataChar!!)
            updateData.run()
        }


    }

    private val updateData = object : Runnable{
        override fun run() {
            //tvAccx.text = PenManager.penData!!.acc_x.toString()
            tvAccx.text = PenManager.penData!!.acc_x.toString()
            mainHandler.postDelayed(this, 5)
        }
    }

    /*override fun onResume() {
        super.onResume()
        tvBatteryProva.text = PenManager.battery.toString()
    }*/

}
