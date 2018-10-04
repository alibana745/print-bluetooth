package com.ali.print_bluetooth.printBluetooth

import kotlin.experimental.and

class UnicodeFormatter {

    fun byteToHex(b: Byte): String {
        // Returns hex String representation of byte b
        val hexDigit = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f')
        val array = charArrayOf(hexDigit[(b.toInt() shr 4) and 0x0f], hexDigit[(b and 0x0f).toInt()])
        return String(array)
    }

    fun charToHex(c: Char): String {
        // Returns hex String representation of char c
        val hi = c.toInt().ushr(8).toByte()
        val lo = (c.toInt() and 0xff).toByte()
        return byteToHex(hi) + byteToHex(lo)
    }

}