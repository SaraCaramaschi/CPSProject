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
import android.os.Handler
import android.os.Looper
import android.view.View
import com.example.cpsproject.ble.RealTimeActivity
import timber.log.Timber
import java.lang.ref.WeakReference
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentLinkedQueue

private const val GATT_MIN_MTU_SIZE = 23
/** Maximum BLE MTU size as defined in gatt_api.h. */
private const val GATT_MAX_MTU_SIZE = 517
//private const val UUID_BATTERIA = 00020000-0001-11E1-AC36-0002A5D5C51B

object ConnectionManager {

    private var listeners: MutableSet<WeakReference<ConnectionEventListener>> = mutableSetOf() // con listeners c'è sempre "invoke"
    private val deviceGattMap = ConcurrentHashMap<BluetoothDevice, BluetoothGatt>()
    private val operationQueue = ConcurrentLinkedQueue<BleOperationType>()
    private var pendingOperation: BleOperationType? = null
    private val serviceuuid = UUID.fromString("00000000-0001-11E1-9AB4-0002A5D5C51B")
    private val batteryuuid = UUID.fromString("00020000-0001-11E1-AC36-0002A5D5C51B")
    private val datauuid = UUID.fromString("00E00000-0001-11E1-AC36-0002A5D5C51B")

    // attenzione perchè quando riceviamo i dati controlliamo il service uuid!!!

    // TODO domanda a chiara: collegato a TODO (101,12)
    // A noi servono questi dati, possiamo fare le funzioni pubbliche?
    private fun readBattery(data: ByteArray) {
        var battery = data.copyOfRange(2,4).reversedArray().toHexString()
            .replace(" ","").substring(2).toInt(radix = 16).toDouble()
        battery = battery.div(10)

        //battery = battery.substring(2)
        //Timber.d("Sottostringa: " + battery)
        Timber.d("Valore batteria: %s",  battery)

        /*val soc = data.copyOfRange(2, 4).reversedArray().toHexString().toLong(radix = 16) / 10 //4 escluso
        val volts = data.copyOfRange(4, 6).reversedArray().toHexString().toLong(radix = 16)
            .toDouble() / 1000
        val amps =
            data.copyOfRange(6, 8).reversedArray().toHexString().toLong(radix = 16).toDouble()
        val status = data[8].toInt()
        Timber.d("la batteria è " + soc)*/
    }

    private fun readData(data: ByteArray) {
        val acc_x =
            data.copyOfRange(2, 4).reversedArray().toHexString()
                .replace(" ","").substring(2).toInt(radix = 16).toShort()
                .toDouble() / 100

        // DUBBIO: MA POSSIAMO TENERE UNA FUNZIONE SOLA OPPURE SPEZZETTARE IN TANTE DIVERSE?
        // teoricamente dovrebbero esserci uuid diversi

        Timber.d("Prova con acc_x per vedere se è tutto ok: %s", acc_x) // da aggiungere diviso 0
        /*val acc_y =
            data.copyOfRange(4, 6).reversedArray().toHexString()
                .replace(" ","").substring(4).toInt(radix = 16).toShort()
                .toDouble() / 100
        val acc_z =
            data.copyOfRange(6, 8).reversedArray().toHexString()
                .replace(" ","").substring(6).toInt(radix = 16).toShort()
                .toDouble() / 100
        val gyr_x =
            data.copyOfRange(8, 10).reversedArray().toHexString()
                .replace(" ","").substring(8).toInt(radix = 16).toShort()
                .toDouble() / 100
        val gyr_y =
            data.copyOfRange(10, 12).reversedArray().toHexString()
                .replace(" ","").substring(10).toInt(radix = 16).toShort()
                .toDouble() / 100
        val gyr_z =
            data.copyOfRange(12, 14).reversedArray().toHexString()
                .replace(" ","").substring(12).toInt(radix = 16).toShort()
                .toDouble() / 100*/

    }
        //funzione per inviare il dato dell'accelerazione (sbagliata)
    /*fun onSaveButonClick(view: View){
        val intent = Intent(this@ConnectionManager,RealTimeActivity::class.java)
        intent.putExtra(name: "acc_x", acc_x)
        }*/

    fun servicesOnDevice(device: BluetoothDevice): List<BluetoothGattService>? =
        deviceGattMap[device]?.services

    fun listenToBondStateChanges(context: Context) {
        context.applicationContext.registerReceiver(
            broadcastReceiver,
            IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED)
        )
    }

    fun registerListener(listener: ConnectionEventListener) { // riempire il listener
        if (listeners.map { it.get() }.contains(listener)) { return }
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

                    listeners.forEach { it.get()?.onConnectionSetupComplete?.invoke(this)
                    }

                    gatt.getService(serviceuuid).characteristics.forEach { //per ogni caratteristiche faccio cose
                        when(it.uuid){ // it è una BLUETOOTHGATTCHARACTERISTIC
                            batteryuuid -> {
                                Timber.e("Questa e' la batteria della penna")
                                /*
                                Le funzioni NATIVA una dopo l'altra non si possono fare per natura del BLE.
                                Invece le funzioni che ha creato il tizio si.
                                */

                                enableNotifications( gatt.device,it)  // funzioni create dal tizio
                                readCharacteristic(gatt.device, it)
                            } //se è batteria fai certe cose: ottieni il dato della batteria
                            datauuid -> {
                                Timber.e("Questi sono i dati della penna")
                                //onCharacteristicRead(gatt, it, status)
                                enableNotifications( gatt.device,it)
                                readCharacteristic(it)
                            }
                        } //when freccette grafe
                    }

                    //altro servizio impo: console  (debog service uuid)
                    // io la leggo ma non va nella funz leggi ma nel suo callback
                    // ONCHARACTERISTICREAD

                    // servizi: service e debug
                    // caratteristiche: data, calendar, battery, console, error

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

        override fun onCharacteristicRead( //aggiungiamo cose alla nativa
            gatt: BluetoothGatt, // la connessione
            characteristic: BluetoothGattCharacteristic, // input: una caratteristica specifica
            status: Int // stato di connessione (?)
        ) {
            with(characteristic) { //ASSOCIA L'INPUT
                when (status) {
                    BluetoothGatt.GATT_SUCCESS -> {
                        // qui ci da proprio il valore!
                        Timber.w("Read characteristic $uuid | value: ${value.toHexString()}")
                        when(characteristic.uuid){
                            batteryuuid -> {
                                Timber.w("Ora sono in onCharacteristicRead e vado a leggere la batteria tramite private fun readBattery")
                                readBattery(characteristic.value)
                            }
                            datauuid -> {
                                Timber.w("Ora sono in onCharacteristicRead e vado a leggere i dati tramite private fun readData ?? ")
                                readData(characteristic.value)
                            }
                        }
                        //sessionmanager che raccoglie tutte le variabili, companion object, singleton kotlin
                        // accessibili ovunque
                        //tutto ciò per caratteristiche READABLE
                        //la caratteristica console invia i messaggi
                        //descrittore attributo a cui diamo un valore per attivare le notifiche
                        // oncharacteristicchange leggi solo quando cambia
                        listeners.forEach { it.get()?.onCharacteristicRead?.invoke(gatt.device, this) }  // cosa fa questo ???
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
                        listeners.forEach { it.get()?.onCharacteristicWrite?.invoke(gatt.device, this) }
                    }
                    BluetoothGatt.GATT_WRITE_NOT_PERMITTED -> {
                        Timber.e("Write not permitted for $uuid!")
                    }
                    else -> {
                        Timber.e("Characteristic write failed for $uuid, error: $status")
                    }
                }
            }

            if (pendingOperation is CharacteristicWrite) {
                signalEndOfOperation()
            }
        }

        override fun onCharacteristicChanged(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic
        ) {
            with(characteristic) {
                Timber.i("Characteristic $uuid changed | value: ${value.toHexString()}")
                listeners.forEach { it.get()?.onCharacteristicChanged?.invoke(gatt.device, this) }
                when(characteristic.uuid){
                    datauuid ->{

                    }
                    batteryuuid->{
                        readBattery(characteristic.value)
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
                            listeners.forEach { it.get()?.onDescriptorWrite?.invoke(gatt.device, this) }
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
                    val previousBondState = getIntExtra(BluetoothDevice.EXTRA_PREVIOUS_BOND_STATE, -1)
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

    private fun BluetoothDevice.isConnected() = deviceGattMap.containsKey(this)
}
