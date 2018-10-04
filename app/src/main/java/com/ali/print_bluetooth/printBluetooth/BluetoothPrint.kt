package com.ali.print_bluetooth.printBluetooth

import android.app.Dialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.ali.print_bluetooth.MainActivity
import java.io.IOException
import java.nio.ByteBuffer
import java.util.*
import kotlin.collections.ArrayList
import com.ali.print_bluetooth.R.mipmap.ic_launcher
import android.widget.TextView
import android.widget.Toast
import com.ali.print_bluetooth.R
import android.content.IntentFilter
import android.content.BroadcastReceiver
import android.content.Context


class BluetoothPrint {

    companion object {
        const val CODE_CONNECT_DEVICE = 1001
        const val CODE_OPEN_BLUETOOTH = 1002
    }

    private val TAG = BluetoothPrint.javaClass.simpleName
    private lateinit var mBluetoothAdapter: BluetoothAdapter
    private lateinit var mBluetoothSocket: BluetoothSocket
    private val applicationUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    private lateinit var mBluetoothDevice: BluetoothDevice
    private lateinit var mActivity: MainActivity

    fun connectBluetooth(activity: MainActivity ) {
        mActivity = activity
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (!mBluetoothAdapter.isEnabled) {
            activityConnectBluetooth(activity)
        }
    }

    fun connectBluetoothDevice(deviceAddress: String) {
        mBluetoothDevice = mBluetoothAdapter.getRemoteDevice(deviceAddress)
    }

    //connection bluetooth
    fun activityConnectBluetooth(activity: MainActivity) {
        val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        startActivityForResult(activity, enableBtIntent, CODE_OPEN_BLUETOOTH, null)
    }

    // connection device bluetooth
    fun activityConnectDevice(activity: MainActivity) {
        val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        startActivityForResult(activity, enableBtIntent, CODE_CONNECT_DEVICE, null)
    }

    //get list bluetooth
    fun listBluetooth(): List<BluetoothDevice> {
        val list:ArrayList<BluetoothDevice> = ArrayList()
        val pairedDevices = mBluetoothAdapter.bondedDevices
        if (pairedDevices.size > 0) {
            Log.i(TAG, "Total device found : ${pairedDevices.size}")
            for (device: BluetoothDevice in pairedDevices) {
                list.add(device)
            }
        }
        return list
    }


    fun findDeviceBluetooth(){
        val findDevice = mBluetoothAdapter.startDiscovery()
        if (findDevice){
            val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
            mActivity.registerReceiver(mReceiver, filter)
        }
    }

    private val mReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND == action) {
                // Get the BluetoothDevice object from the Intent
                val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                // Add the name and address to an array adapter to show in a ListView
                Log.i(TAG, "Total device found : ${device.name}")
//                mArrayAdapter.add(device.name + "\n" + device.address)
            }
        }
    }
//    fun doPrint() {
//        val t = object : Thread() {
//            override fun run() {
//                try {
//                    val os = mBluetoothSocket.outputStream
//                    var BILL = ""
//
//                    BILL = ("                   XXXX MART    \n"
//                            + "                   XX.AA.BB.CC.     \n " +
//                            "                 NO 25 ABC ABCDE    \n" +
//                            "                  XXXXX YYYYYY      \n" +
//                            "                   MMM 590019091      \n")
//                    BILL = "$BILL-----------------------------------------------\n"
//
//
//                    BILL += String.format("%1$-10s %2$10s %3$13s %4$10s", "Item", "Qty", "Rate", "Totel")
//                    BILL += "\n"
//                    BILL = "$BILL-----------------------------------------------"
//                    BILL = BILL + "\n " + String.format("%1$-10s %2$10s %3$11s %4$10s", "item-001", "5", "10", "50.00")
//                    BILL = BILL + "\n " + String.format("%1$-10s %2$10s %3$11s %4$10s", "item-002", "10", "5", "50.00")
//                    BILL = BILL + "\n " + String.format("%1$-10s %2$10s %3$11s %4$10s", "item-003", "20", "10", "200.00")
//                    BILL = BILL + "\n " + String.format("%1$-10s %2$10s %3$11s %4$10s", "item-004", "50", "10", "500.00")
//
//                    BILL = "$BILL\n-----------------------------------------------"
//                    BILL = "$BILL\n\n "
//
//                    BILL = "$BILL                   Total Qty:      85\n"
//                    BILL = "$BILL                   Total Value:     700.00\n"
//
//                    BILL = "$BILL-----------------------------------------------\n"
//                    BILL = "$BILL\n\n "
//                    os.write(BILL.toByteArray())
//                    //This is printer specific code you can comment ==== > Start
//
//                    // Setting height
//                    val gs = 29
//                    os.write(intToByteArray(gs))
//                    val h = 104
//                    os.write(intToByteArray(h))
//                    val n = 162
//                    os.write(intToByteArray(n))
//
//                    // Setting Width
//                    val width = 29
//                    os.write(intToByteArray(width))
//                    val w = 119
//                    os.write(intToByteArray(w))
//                    val n_width = 2
//                    os.write(intToByteArray(n_width))
//
//
//                } catch (e: Exception) {
//                    Log.e("MainActivity", "Exe ", e)
//                }
//
//            }
//        }
//        t.start()
//    }

    fun run() {
        try {
            mBluetoothSocket = mBluetoothDevice.createRfcommSocketToServiceRecord(applicationUUID)
            mBluetoothAdapter.cancelDiscovery()
            mBluetoothSocket.connect()
//            mHandler.sendEmptyMessage(0)
        } catch (eConnectException: IOException) {
//            Log.d(TAG, "CouldNotConnectToSocket", eConnectException)
            closeSocket(mBluetoothSocket)
            return
        }
    }

    private fun closeSocket(nOpenSocket: BluetoothSocket) {
        try {
            nOpenSocket.close()
//            Log.d(TAG, "SocketClosed")
        } catch (ex: IOException) {
//            Log.d(TAG, "CouldNotCloseSocket")
        }

    }

    private fun intToByteArray(value: Int): Byte {
        val b = ByteBuffer.allocate(4).putInt(value).array()

        for (k in b.indices) {
            println("Selva  [" + k + "] = " + "0x"
                    + UnicodeFormatter().byteToHex(b[k]))
        }

        return b[3]
    }

    private fun disableBluetooth() {
        try {
            mBluetoothSocket.close()
        } catch (e: Exception) {
            Log.e("Tag", "Exe ", e)
        }

    }
}
