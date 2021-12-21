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
import kotlinx.android.synthetic.main.activity_real_time.*
import timber.log.Timber


class RealTimeActivity : AppCompatActivity() {

    lateinit var mainHandler:Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_real_time)

        mainHandler = Handler(Looper.getMainLooper())

        Timber.d("batteriaaaaa:  %s", PenManager.battery.toString())

        btnStartRealTime.setOnClickListener {
            ConnectionManager.enableNotifications(currDevice!!, dataChar!!)
            updateData.run()
            tvRealBattery.text = PenManager.battery.toString()
        }


    }

    private val updateData = object : Runnable{
        override fun run() {
            tvAccx.text = PenManager.penData!!.acc_x.toString()
            tvAccy.text = PenManager.penData!!.acc_y.toString()
            tvAccz.text = PenManager.penData!!.acc_z.toString()
            tvGyrx.text = PenManager.penData!!.gyr_x.toString()
            tvGyry.text = PenManager.penData!!.gyr_y.toString()
            tvGyrz.text = PenManager.penData!!.gyr_z.toString()
            tvPress.text = PenManager.penData!!.press.toString()
            mainHandler.postDelayed(this, 5)
        }
    }
}
