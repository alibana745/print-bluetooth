package com.ali.print_bluetooth

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import com.ali.print_bluetooth.printBluetooth.AdapterBluetooth
import com.ali.print_bluetooth.printBluetooth.BluetoothPrint


class MainActivity : AppCompatActivity() {

    private fun init(){
        mBluetoothPrint = BluetoothPrint()
        initListener()
    }

    private fun initListener(){
        findViewById<Button>(R.id.button_connect_printer).setOnClickListener {
            mBluetoothPrint.connectBluetooth(this)
        }

        findViewById<Button>(R.id.button_change_printer).setOnClickListener {
//            mBluetoothPrint.listBluetooth()
//            val recyclerView = findViewById<RecyclerView>(R.id.rcv)
//            val adapterBluetooth = AdapterBluetooth(mBluetoothPrint.listBluetooth()){
//                bluetoothDevice ->
//                Toast.makeText(this, bluetoothDevice.name, Toast.LENGTH_SHORT).show()
//            }
//            recyclerView.adapter = adapterBluetooth
//            recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
////
            mBluetoothPrint.findDeviceBluetooth()
        }
    }

    private lateinit var mBluetoothPrint:BluetoothPrint

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK){
            when(requestCode){
                BluetoothPrint.CODE_CONNECT_DEVICE -> {
                    mBluetoothPrint.connectBluetoothDevice(intent.getStringExtra("DeviceAddress"))
                }
                BluetoothPrint.CODE_OPEN_BLUETOOTH -> {

                }
            }
        }
    }

}
