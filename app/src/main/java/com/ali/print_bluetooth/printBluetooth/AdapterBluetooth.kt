package com.ali.print_bluetooth.printBluetooth

import android.bluetooth.BluetoothDevice
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ali.print_bluetooth.R
import kotlinx.android.synthetic.main.item_bluetooth.view.*

class AdapterBluetooth(val listBluetoothDevice:List<BluetoothDevice>,
                       val callback:(BluetoothDevice)->Unit) :
        RecyclerView.Adapter<AdapterBluetooth.ViewHolderBluetooth>() {


    override fun onBindViewHolder(holder: ViewHolderBluetooth, position: Int) {
        holder.mTvBluetoothDevice.text = listBluetoothDevice[position].name
        holder.itemView.setOnClickListener {
            callback(listBluetoothDevice[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderBluetooth {
        return ViewHolderBluetooth(LayoutInflater.from(parent.context).inflate(R.layout.item_bluetooth, parent, false))
    }

    override fun getItemCount(): Int {
        return listBluetoothDevice.size
    }

    class ViewHolderBluetooth(item:View): RecyclerView.ViewHolder(item) {
        val mTvBluetoothDevice = item.textView_name_bluetooth!!
//        val mTvBluetoothDevice = item.findViewById<TextView>(R.id.textView_name_bluetooth)
    }
}