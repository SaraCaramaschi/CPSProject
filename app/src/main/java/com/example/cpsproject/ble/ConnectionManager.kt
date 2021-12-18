/*
 * Copyright 2019 Punch Through Design LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.punchthrough.blestarterappandroid.ble

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import android.bluetooth.BluetoothGattService
import android.bluetooth.BluetoothProfile
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.cpsproject.ble.PenActivity
import com.example.cpsproject.managers.PenManager
import com.example.cpsproject.managers.SessionManager
import com.example.cpsproject.managers.saveDocument
import com.example.cpsproject.model.Acquisition
import com.example.cpsproject.model.PenData
import com.example.cpsproject.model.Session
import timber.log.Timber
import java.lang.ref.WeakReference
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentLinkedQueue

private const val GATT_MIN_MTU_SIZE = 23

/** Maximum BLE MTU size as defined in gatt_api.h. */
private const val GATT_MAX_MTU_SIZE = 517
//private const val UUID_BATTERIA = 00020000-0001-11E1-AC36-0002A5D5C51B

object ConnectionManager {

    private var tagFlag: Boolean = false
    private var listeners: MutableSet<WeakReference<ConnectionEventListener>> =
        mutableSetOf() // con listeners c'è sempre "invoke"
    private val deviceGattMap = ConcurrentHashMap<BluetoothDevice, BluetoothGatt>()
    private val operationQueue = ConcurrentLinkedQueue<BleOperationType>()
    private var pendingOperation: BleOperationType? = null
    private val serviceuuid = UUID.fromString("00000000-0001-11E1-9AB4-0002A5D5C51B")
    private val batteryuuid = UUID.fromString("00020000-0001-11E1-AC36-0002A5D5C51B")
    private val datauuid = UUID.fromString("00E00000-0001-11E1-AC36-0002A5D5C51B")
    private val consoleuuid = UUID.fromString("00000001-000E-11E1-AC36-0002A5D5C51B")
    private val debuguuid = UUID.fromString("00000000-000E-11E1-9AB4-0002A5D5C51B")
    private var down: Boolean = false

    private val format = "FFormat"
    private val download = "downloadu"
    private val onBoard = "OnBoard"

    private var acquisition: Acquisition = Acquisition()

    private var batteryChar: BluetoothGattCharacteristic? = null
    public var dataChar: BluetoothGattCharacteristic? = null
    private var consoleChar: BluetoothGattCharacteristic? = null
    public var currDevice: BluetoothDevice? = null
    private var connection: BluetoothGatt? = null

    private fun readBattery(data: ByteArray) {
        var battery = data.copyOfRange(2, 4).reversedArray().toHexString()
            .replace(" ", "").substring(2).toInt(radix = 16).toDouble()
        battery = battery.div(10)
        PenManager.battery = battery
        //Timber.d("Valore batteria: %s", battery)
    }

    fun readData(data: ByteArray) {
        val acc_x =
            data.copyOfRange(2, 4).reversedArray().toHexString()
                .replace(" ", "").substring(2).toInt(radix = 16).toShort()
                .toDouble() / 100

        //Timber.d("Prova con acc_x per vedere se è tutto ok: %s", acc_x)

        Timber.d("Data= " + data)
        val acc_y =
            data.copyOfRange(4, 6).reversedArray().toHexString()
                .toInt(radix = 16).toShort()
                .toDouble() / 100

        val acc_z =
            data.copyOfRange(6, 8).reversedArray().toHexString()
                .toInt(radix = 16).toShort()
                .toDouble() / 100
        val gyr_x =
            data.copyOfRange(8, 10).reversedArray().toHexString()
                .toInt(radix = 16).toShort()
                .toDouble() / 100
        val gyr_y =
            data.copyOfRange(10, 12).reversedArray().toHexString()
                .toInt(radix = 16).toShort()
                .toDouble() / 100
        val gyr_z =
            data.copyOfRange(12, 14).reversedArray().toHexString()
                .toInt(radix = 16).toShort()
                .toDouble() / 100

        var press: Double = if (data.count() == 16) {
            data.copyOfRange(14, 16).reversedArray().toHexString().toInt(radix = 16).toShort()
                .toDouble() / 100
        } else {
            data.copyOfRange(16, 18).reversedArray().toHexString().toInt(radix = 16).toShort()
                .toDouble() / 10
        }

        //down = true
        if (!down) {
            PenManager.penData.acc_x = acc_x
            PenManager.penData.acc_y = acc_y
            PenManager.penData.acc_z = acc_z
            PenManager.penData.gyr_x = gyr_x
            PenManager.penData.gyr_y = gyr_y
            PenManager.penData.gyr_z = gyr_z
            PenManager.penData.press = press
        } else {
            var sessData = PenData()

            sessData.acc_x = acc_x
            sessData.acc_y = acc_y
            sessData.acc_z = acc_z
            sessData.gyr_x = gyr_x
            sessData.gyr_y = gyr_y
            sessData.gyr_z = gyr_z
            sessData.press = press

            //SessionManager.sessione.sessionData!!.add(sessData)
            acquisition.Data.add(sessData)
            //SessionManager.sessione.acquisitions[acquisition.tag==tag]
            Timber.d("datiiii $acc_x $acc_y $acc_z $gyr_x $gyr_y $gyr_z $press")
            Timber.d("Riga di dati aggiunta")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun readConsole(data: ByteArray) {
        var consoleString = data.toHexString()
        consoleString = (hexToAscii(consoleString)).lowercase()

        Timber.d("Console string e' : %s", consoleString)

        if (consoleString.contains("start formatting")) {
            Timber.d("format iniziato")
        } else if (consoleString.contains("formatting done")) {
            disableNotifications(currDevice!!, consoleChar!!)
            Timber.d("format finito")
        }
        else if (consoleString.contains("onboard: start")) {
            Timber.d("Onboard iniziato")
        } else if (consoleString.contains("stored files")) {
            val colonIndex = consoleString.indexOf(":")
            var tmp = consoleString.substring(colonIndex + 1)
            tmp = tmp.substring(0, tmp.length - 2)
            val nFiles = tmp?.toInt() //?: 0
            SessionManager.sessione.nFile = nFiles

        } else if (consoleString.contains("::")) {
            if (tagFlag) {
                tagFlag = false
                var tag = consoleString.replace(" ", "")
                tag = tag.replace("\t", "")
                tag = tag.replace("\n", "")
                tag = tag.replace("\r", "")
                tag = tag.replace("::", "")

                Timber.d("tag: $tag")
            }
        } else if (consoleString.contains("\\") && consoleString.contains("-") && consoleString.contains(
                ":"
            ) && consoleString.count() == 20
        ) {
            tagFlag = true
            var formatter = DateTimeFormatter.ofPattern("dd\\mm\\yy-HH:mm:ss")
            var dateString = consoleString.replace(" ", "")
            dateString = dateString.replace("\t", "")
            dateString = dateString.replace("\n", "")
            dateString = dateString.replace("\r", "")
            dateString = dateString.replace(
                "00\\",
                "01\\"
            )  // fix date when day=0 and/or month=0
            dateString.substring(1)
            Timber.d("datestring: %s", dateString)
            var date = LocalDate.parse(dateString, formatter)
            var dateLong = date.atStartOfDay(ZoneId.systemDefault()).toInstant().epochSecond
            var interval = System.currentTimeMillis() - dateLong
            if (interval >= 60 * 60 * 24 * 365) {     // if older than 1 years use current date
                date = LocalDate.now()
            }
            if (data.size == 16) {
                down = true
                val dataInstance = readData(data)
                Timber.d(dataInstance.toString())
                down = false
            }
            SessionManager.sessione.datetime = date
        } else if (consoleString.contains("download finished")) {
            disableNotifications(currDevice!!, consoleChar!!)
            Timber.d("download finito")

            //  SessionManager.saveDocument(SessionManager.sessione, this)
            //  SessionManager.ereaseSessione(SessionManager.sessione)


        } else if (down && data.size == 16) {
            readData(data)
        }
    }

    // Functions related to the console:
    fun format() {
        enableNotifications(currDevice!!, consoleChar!!) // queste sono le funzioni del tizio
        writeCharacteristic(currDevice!!, consoleChar!!, format.toByteArray())
    }

    fun download() {
        down = true
        enableNotifications(currDevice!!, consoleChar!!)
        writeCharacteristic(currDevice!!, consoleChar!!, download.toByteArray())
    }

    fun StartOnBoard() {
        enableNotifications(currDevice!!, consoleChar!!)
        var tag = (System.currentTimeMillis() * 1000).toInt().toString() //timestamp momento in cui crei recording
        //recording sulla penna ha ID timestamp. poi lista di recording nell'app
        var message = (tag + onBoard).toByteArray()
        writeCharacteristic(currDevice!!, consoleChar!!, message)
        Timber.d("Onboard con tag OK iniziato")
        startAcquisition(tag)
    }

    private fun startAcquisition(tag: String) {
        acquisition.tag = tag
    }

    fun StopOnBoard() {
        disableNotifications(currDevice!!, consoleChar!!)
        SessionManager.sessione.acquisitions.add(acquisition)
        Timber.d("Onboard STOPPATO")
    }

    private fun hexToAscii(hexStr: String): String {
        val output = StringBuilder("")
        var i = 0
        while (i < hexStr.length) {
            val str = hexStr.substring(i, i + 2)
            output.append(str.toInt(16).toChar())
            i += 2
        }
        return output.toString()
    }

    fun ByteArray.toHexString(): String =
        joinToString(separator = "", prefix = "") { String.format("%02X", it) }

    fun servicesOnDevice(device: BluetoothDevice): List<BluetoothGattService>? =
        deviceGattMap[device]?.services

    fun listenToBondStateChanges(context: Context) {
        context.applicationContext.registerReceiver(
            broadcastReceiver,
            IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED)
        )
    }

    fun registerListener(listener: ConnectionEventListener) { // riempire il listener
        if (listeners.map { it.get() }.contains(listener)) {
            return
        }
        listeners.add(WeakReference(listener))
        listeners = listeners.filter { it.get() != null }.toMutableSet()
        Timber.d("Added listener $listener, ${listeners.size} listeners total")
    }

    fun unregisterListener(listener: ConnectionEventListener) {
        // Removing elements while in a loop results in a java.util.ConcurrentModificationException
        var toRemove: WeakReference<ConnectionEventListener>? = null
        listeners.forEach {
            if (it.get() == listener) {
                toRemove = it
            }
        }
        toRemove?.let {
            listeners.remove(it)
            Timber.d("Removed listener ${it.get()}, ${listeners.size} listeners total")
        }
    }

    fun connect(device: BluetoothDevice, context: Context) {
        if (device.isConnected()) {
            Timber.e("Already connected to ${device.address}!")
        } else {
            enqueueOperation(Connect(device, context.applicationContext))
        }
    }

    fun teardownConnection(device: BluetoothDevice) {
        if (device.isConnected()) {
            enqueueOperation(Disconnect(device))
        } else {
            Timber.e("Not connected to ${device.address}, cannot teardown connection!")
        }
    }

    fun readCharacteristic(device: BluetoothDevice, characteristic: BluetoothGattCharacteristic) {
        if (device.isConnected() && characteristic.isReadable()) {
            enqueueOperation(CharacteristicRead(device, characteristic.uuid))
        } else if (!characteristic.isReadable()) {
            Timber.e("Attempting to read ${characteristic.uuid} that isn't readable!")
        } else if (!device.isConnected()) {
            Timber.e("Not connected to ${device.address}, cannot perform characteristic read")
        }
    }

    fun writeCharacteristic(
        device: BluetoothDevice,
        characteristic: BluetoothGattCharacteristic,
        payload: ByteArray
    ) {
        val writeType = when {
            characteristic.isWritable() -> BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT
            characteristic.isWritableWithoutResponse() -> {
                BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE
            }
            else -> {
                Timber.e("Characteristic ${characteristic.uuid} cannot be written to")
                return
            }
        }
        if (device.isConnected()) {
            enqueueOperation(CharacteristicWrite(device, characteristic.uuid, writeType, payload))
        } else {
            Timber.e("Not connected to ${device.address}, cannot perform characteristic write")
        }
    }

    fun readDescriptor(device: BluetoothDevice, descriptor: BluetoothGattDescriptor) {
        if (device.isConnected() && descriptor.isReadable()) {
            enqueueOperation(DescriptorRead(device, descriptor.uuid))
        } else if (!descriptor.isReadable()) {
            Timber.e("Attempting to read ${descriptor.uuid} that isn't readable!")
        } else if (!device.isConnected()) {
            Timber.e("Not connected to ${device.address}, cannot perform descriptor read")
        }
    }

    fun writeDescriptor(
        device: BluetoothDevice,
        descriptor: BluetoothGattDescriptor,
        payload: ByteArray
    ) {
        if (device.isConnected() && (descriptor.isWritable() || descriptor.isCccd())) {
            enqueueOperation(DescriptorWrite(device, descriptor.uuid, payload))
        } else if (!device.isConnected()) {
            Timber.e("Not connected to ${device.address}, cannot perform descriptor write")
        } else if (!descriptor.isWritable() && !descriptor.isCccd()) {
            Timber.e("Descriptor ${descriptor.uuid} cannot be written to")
        }
    }

    fun enableNotifications(device: BluetoothDevice, characteristic: BluetoothGattCharacteristic) {
        if (device.isConnected() &&
            (characteristic.isIndicatable() || characteristic.isNotifiable())
        ) {
            enqueueOperation(EnableNotifications(device, characteristic.uuid))
        } else if (!device.isConnected()) {
            Timber.e("Not connected to ${device.address}, cannot enable notifications")
        } else if (!characteristic.isIndicatable() && !characteristic.isNotifiable()) {
            Timber.e("Characteristic ${characteristic.uuid} doesn't support notifications/indications")
        }
    }

    fun disableNotifications(device: BluetoothDevice, characteristic: BluetoothGattCharacteristic) {
        if (device.isConnected() &&
            (characteristic.isIndicatable() || characteristic.isNotifiable())
        ) {
            enqueueOperation(DisableNotifications(device, characteristic.uuid))
        } else if (!device.isConnected()) {
            Timber.e("Not connected to ${device.address}, cannot disable notifications")
        } else if (!characteristic.isIndicatable() && !characteristic.isNotifiable()) {
            Timber.e("Characteristic ${characteristic.uuid} doesn't support notifications/indications")
        }
    }

    fun requestMtu(device: BluetoothDevice, mtu: Int) { // funzione che: ottenere questo MTU codice
        if (device.isConnected()) {
            enqueueOperation(MtuRequest(device, mtu.coerceIn(GATT_MIN_MTU_SIZE, GATT_MAX_MTU_SIZE)))
        } else {
            Timber.e("Not connected to ${device.address}, cannot request MTU update!")
        }
    }

    //Beginning of PRIVATE functions

    @Synchronized
    private fun enqueueOperation(operation: BleOperationType) {
        operationQueue.add(operation)
        if (pendingOperation == null) {
            doNextOperation()
        }
    }

    @Synchronized
    private fun signalEndOfOperation() {
        Timber.d("End of $pendingOperation")
        pendingOperation = null
        if (operationQueue.isNotEmpty()) {
            doNextOperation()
        }
    }

    /**
     * Perform a given [BleOperationType]. All permission checks are performed before an operation
     * can be enqueued by [enqueueOperation].
     */

    @Synchronized
    private fun doNextOperation() {
        if (pendingOperation != null) {
            Timber.e("doNextOperation() called when an operation is pending! Aborting.")
            return
        }

        val operation = operationQueue.poll() ?: run {
            Timber.v("Operation queue empty, returning")
            return
        }
        pendingOperation = operation

        // Handle Connect separately from other operations that require device to be connected
        if (operation is Connect) {
            with(operation) {
                Timber.w("Connecting to ${device.address}")
                device.connectGatt(context, false, callback)
            }
            return
        }

        // Check BluetoothGatt availability for other operations
        val gatt = deviceGattMap[operation.device]
            ?: this@ConnectionManager.run {
                Timber.e("Not connected to ${operation.device.address}! Aborting $operation operation.")
                signalEndOfOperation()
                return
            }

        // TODO: Make sure each operation ultimately leads to signalEndOfOperation()
        // TODO: Refactor this into an BleOperationType abstract or extension function

        when (operation) {
            is Disconnect -> with(operation) {
                Timber.w("Disconnecting from ${device.address}")
                gatt.close()
                deviceGattMap.remove(device)
                listeners.forEach { it.get()?.onDisconnect?.invoke(device) }
                signalEndOfOperation()
            }
            is CharacteristicWrite -> with(operation) {
                gatt.findCharacteristic(characteristicUuid)?.let { characteristic ->
                    characteristic.writeType = writeType
                    characteristic.value = payload
                    gatt.writeCharacteristic(characteristic)
                } ?: this@ConnectionManager.run {
                    Timber.e("Cannot find $characteristicUuid to write to")
                    signalEndOfOperation()
                }
            }
            is CharacteristicRead -> with(operation) {
                gatt.findCharacteristic(characteristicUuid)?.let { characteristic ->
                    gatt.readCharacteristic(characteristic) // funzionr nativa che chiama dopo che ha controllato la coda
                } ?: this@ConnectionManager.run {
                    Timber.e("Cannot find $characteristicUuid to read from")
                    signalEndOfOperation()
                }
            }
            is DescriptorWrite -> with(operation) {
                gatt.findDescriptor(descriptorUuid)?.let { descriptor ->
                    descriptor.value = payload
                    gatt.writeDescriptor(descriptor)
                } ?: this@ConnectionManager.run {
                    Timber.e("Cannot find $descriptorUuid to write to")
                    signalEndOfOperation()
                }
            }
            is DescriptorRead -> with(operation) {
                gatt.findDescriptor(descriptorUuid)?.let { descriptor ->
                    gatt.readDescriptor(descriptor)
                } ?: this@ConnectionManager.run {
                    Timber.e("Cannot find $descriptorUuid to read from")
                    signalEndOfOperation()
                }
            }
            is EnableNotifications -> with(operation) {
                gatt.findCharacteristic(characteristicUuid)?.let { characteristic ->
                    val cccdUuid = UUID.fromString(CCC_DESCRIPTOR_UUID)
                    val payload = when {
                        characteristic.isIndicatable() ->
                            BluetoothGattDescriptor.ENABLE_INDICATION_VALUE
                        characteristic.isNotifiable() ->
                            BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
                        else ->
                            error("${characteristic.uuid} doesn't support notifications/indications")
                    }

                    characteristic.getDescriptor(cccdUuid)?.let { cccDescriptor ->
                        if (!gatt.setCharacteristicNotification(characteristic, true)) {
                            Timber.e("setCharacteristicNotification failed for ${characteristic.uuid}")
                            signalEndOfOperation()
                            return
                        }

                        cccDescriptor.value = payload
                        gatt.writeDescriptor(cccDescriptor)
                    } ?: this@ConnectionManager.run {
                        Timber.e("${characteristic.uuid} doesn't contain the CCC descriptor!")
                        signalEndOfOperation()
                    }
                } ?: this@ConnectionManager.run {
                    Timber.e("Cannot find $characteristicUuid! Failed to enable notifications.")
                    signalEndOfOperation()
                }
            }
            is DisableNotifications -> with(operation) {
                gatt.findCharacteristic(characteristicUuid)?.let { characteristic ->
                    val cccdUuid = UUID.fromString(CCC_DESCRIPTOR_UUID)
                    characteristic.getDescriptor(cccdUuid)?.let { cccDescriptor ->
                        if (!gatt.setCharacteristicNotification(characteristic, false)) {
                            Timber.e("setCharacteristicNotification failed for ${characteristic.uuid}")
                            signalEndOfOperation()
                            return
                        }
                        cccDescriptor.value = BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE
                        gatt.writeDescriptor(cccDescriptor)
                    } ?: this@ConnectionManager.run {
                        Timber.e("${characteristic.uuid} doesn't contain the CCC descriptor!")
                        signalEndOfOperation()
                    }
                } ?: this@ConnectionManager.run {
                    Timber.e("Cannot find $characteristicUuid! Failed to disable notifications.")
                    signalEndOfOperation()
                }
            }
            is MtuRequest -> with(operation) {
                gatt.requestMtu(mtu)
            }
        }
    }

    private val callback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
            val deviceAddress = gatt.device.address

            if (status == BluetoothGatt.GATT_SUCCESS) {
                if (newState == BluetoothProfile.STATE_CONNECTED) {
                    Timber.w("onConnectionStateChange: connected to $deviceAddress")
                    deviceGattMap[gatt.device] = gatt
                    Handler(Looper.getMainLooper()).post {
                        gatt.discoverServices()
                    }
                } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                    Timber.e("onConnectionStateChange: disconnected from $deviceAddress")
                    teardownConnection(gatt.device)
                }
            } else {
                Timber.e("onConnectionStateChange: status $status encountered for $deviceAddress!")
                if (pendingOperation is Connect) {
                    signalEndOfOperation()
                }
                teardownConnection(gatt.device)
            }
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
            with(gatt) {
                if (status == BluetoothGatt.GATT_SUCCESS) {
                    Timber.w("Discovered ${services.size} services for ${device.address}.")
                    printGattTable()
                    requestMtu(device, GATT_MAX_MTU_SIZE)

                    listeners.forEach {
                        it.get()?.onConnectionSetupComplete?.invoke(this)
                    }

                    connection = gatt
                    currDevice = gatt.device
                    PenManager.penName = gatt.device.name

                    gatt.getService(serviceuuid).characteristics.forEach {
                        when (it.uuid) {
                            batteryuuid -> {
                                batteryChar = it
                                enableNotifications(gatt.device, it)  // funzioni create dal tizio
                                readCharacteristic(gatt.device, it)
                            }
                            datauuid -> {
                                dataChar = it
                            }
                        }
                    }

                    gatt.getService(debuguuid).characteristics.forEach {
                        when (it.uuid) {
                            consoleuuid -> {
                                Timber.d("Abbiamo trovato il servizio della console OK")
                                consoleChar = it
                            }
                        }
                    }

                } else {
                    Timber.e("Service discovery failed due to status $status")
                    teardownConnection(gatt.device)
                }
            }

            if (pendingOperation is Connect) {
                signalEndOfOperation()
            }
        }

        override fun onMtuChanged(gatt: BluetoothGatt, mtu: Int, status: Int) {
            Timber.w("ATT MTU changed to $mtu, success: ${status == BluetoothGatt.GATT_SUCCESS}")
            listeners.forEach { it.get()?.onMtuChanged?.invoke(gatt.device, mtu) }

            if (pendingOperation is MtuRequest) {
                signalEndOfOperation()
            }
        }

        override fun onCharacteristicRead(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            status: Int
        ) {
            with(characteristic) {
                when (status) {
                    BluetoothGatt.GATT_SUCCESS -> {
                        Timber.w("Read characteristic $uuid | value: ${value.toHexString()}")
                        when (characteristic.uuid) {
                            batteryuuid -> {
                                Timber.w("Ora sono in onCharacteristicRead e vado a leggere la batteria tramite private fun readBattery")
                                readBattery(characteristic.value)
                            }
                            datauuid -> {
                                Timber.w("Ora sono in onCharacteristicRead e vado a leggere i dati tramite private fun readData ?? ")
                            }
                        }

                        listeners.forEach {
                            it.get()?.onCharacteristicRead?.invoke(
                                gatt.device,
                                this
                            )
                        }
                    }
                    BluetoothGatt.GATT_READ_NOT_PERMITTED -> {
                        Timber.e("Read not permitted for $uuid!")
                    }
                    else -> {
                        Timber.e("Characteristic read failed for $uuid, error: $status")
                    }
                }
            }

            if (pendingOperation is CharacteristicRead) {
                signalEndOfOperation()
            }
        }

        override fun onCharacteristicWrite(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            status: Int
        ) {
            with(characteristic) {
                when (status) {
                    BluetoothGatt.GATT_SUCCESS -> {
                        Timber.i("Wrote to characteristic $uuid | value: ${value.toHexString()}")
                        listeners.forEach {
                            it.get()?.onCharacteristicWrite?.invoke(
                                gatt.device,
                                this
                            )
                        }

                        when (characteristic.uuid) {
                            consoleuuid -> {
                                Timber.d("ho scritto nella console")
                            }
                        }

                    }
                    BluetoothGatt.GATT_WRITE_NOT_PERMITTED -> {
                        Timber.e("Write not permitted for $uuid!")
                    }
                    else -> {
                        Timber.e("Characteristic write failed for $uuid, error: $status")
                    }
                }

                if (pendingOperation is CharacteristicWrite) {
                    signalEndOfOperation()
                }
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        override fun onCharacteristicChanged(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic
        ) {
            with(characteristic) {

                listeners.forEach { it.get()?.onCharacteristicChanged?.invoke(gatt.device, this) }
                when (characteristic.uuid) {
                    datauuid -> {
                        Timber.i("Characteristic DATA changed | value: ${value.toHexString()}")
                        readData(characteristic.value)
                    }
                    batteryuuid -> {
                        readBattery(characteristic.value)
                    }
                    consoleuuid -> {
                        Timber.i("Characteristic CONSOLE changed | value: ${value.toHexString()}")
                        readConsole(characteristic.value)
                    }
                }
            }
        }

        override fun onDescriptorRead(
            gatt: BluetoothGatt,
            descriptor: BluetoothGattDescriptor,
            status: Int
        ) {
            with(descriptor) {
                when (status) {
                    BluetoothGatt.GATT_SUCCESS -> {
                        Timber.i("Read descriptor $uuid | value: ${value.toHexString()}")
                        listeners.forEach { it.get()?.onDescriptorRead?.invoke(gatt.device, this) }
                    }
                    BluetoothGatt.GATT_READ_NOT_PERMITTED -> {
                        Timber.e("Read not permitted for $uuid!")
                    }
                    else -> {
                        Timber.e("Descriptor read failed for $uuid, error: $status")
                    }
                }
            }

            if (pendingOperation is DescriptorRead) {
                signalEndOfOperation()
            }
        }

        override fun onDescriptorWrite(
            gatt: BluetoothGatt,
            descriptor: BluetoothGattDescriptor,
            status: Int
        ) {
            with(descriptor) {
                when (status) {
                    BluetoothGatt.GATT_SUCCESS -> {
                        Timber.i("Wrote to descriptor $uuid | value: ${value.toHexString()}")

                        if (isCccd()) {
                            onCccdWrite(gatt, value, characteristic)
                        } else {
                            listeners.forEach {
                                it.get()?.onDescriptorWrite?.invoke(
                                    gatt.device,
                                    this
                                )
                            }
                        }
                    }
                    BluetoothGatt.GATT_WRITE_NOT_PERMITTED -> {
                        Timber.e("Write not permitted for $uuid!")
                    }
                    else -> {
                        Timber.e("Descriptor write failed for $uuid, error: $status")
                    }
                }
            }

            if (descriptor.isCccd() &&
                (pendingOperation is EnableNotifications || pendingOperation is DisableNotifications)
            ) {
                signalEndOfOperation()
            } else if (!descriptor.isCccd() && pendingOperation is DescriptorWrite) {
                signalEndOfOperation()
            }
        }

        private fun onCccdWrite(
            gatt: BluetoothGatt,
            value: ByteArray,
            characteristic: BluetoothGattCharacteristic
        ) {
            val charUuid = characteristic.uuid
            val notificationsEnabled =
                value.contentEquals(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE) ||
                        value.contentEquals(BluetoothGattDescriptor.ENABLE_INDICATION_VALUE)
            val notificationsDisabled =
                value.contentEquals(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE)

            when {
                notificationsEnabled -> {
                    Timber.w("Notifications or indications ENABLED on $charUuid")
                    listeners.forEach {
                        it.get()?.onNotificationsEnabled?.invoke(
                            gatt.device,
                            characteristic
                        )
                    }
                }
                notificationsDisabled -> {
                    Timber.w("Notifications or indications DISABLED on $charUuid")
                    listeners.forEach {
                        it.get()?.onNotificationsDisabled?.invoke(
                            gatt.device,
                            characteristic
                        )
                    }
                }
                else -> {
                    Timber.e("Unexpected value ${value.toHexString()} on CCCD of $charUuid")
                }
            }
        }
    }

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            with(intent) {
                if (action == BluetoothDevice.ACTION_BOND_STATE_CHANGED) {
                    val device = getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                    val previousBondState =
                        getIntExtra(BluetoothDevice.EXTRA_PREVIOUS_BOND_STATE, -1)
                    val bondState = getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, -1)
                    val bondTransition = "${previousBondState.toBondStateDescription()} to " +
                            bondState.toBondStateDescription()
                    Timber.w("${device?.address} bond state changed | $bondTransition")
                }
            }
        }

        private fun Int.toBondStateDescription() = when (this) {
            BluetoothDevice.BOND_BONDED -> "BONDED"
            BluetoothDevice.BOND_BONDING -> "BONDING"
            BluetoothDevice.BOND_NONE -> "NOT BONDED"
            else -> "ERROR: $this"
        }
    }

    public fun BluetoothDevice.isConnected() = deviceGattMap.containsKey(this)
}
