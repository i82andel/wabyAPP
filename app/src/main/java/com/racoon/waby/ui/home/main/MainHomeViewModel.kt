package com.racoon.waby.ui.home.main

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.Intent.getIntent
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.IsoDep
import android.nfc.tech.NfcA
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.racoon.waby.data.repository.SpotRepository

class MainHomeViewModel : ViewModel() {

    private val spotRepository = SpotRepository()
    private var nfcAdapter: NfcAdapter? = null
    lateinit var context : Context

    fun readNfc(){

        /*val nfcAdapter = NfcAdapter.getDefaultAdapter(context)
        if (nfcAdapter == null){
            Toast.makeText(context,"This device not support NFC", Toast.LENGTH_LONG)
        }

        val action = intent.action
            if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action) || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)){

                val parcelables = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
                with(parcelables) {
                    val inNdefMessage = this[0] as NdefMessage
                    val inNdefRecords = inNdefMessage.records
                    val ndefRecord_0 = inNdefRecords[0]

                    val inMessage = String(ndefRecord_0.payload)
                    bind.text = inMessage
                }*/

    }


}


