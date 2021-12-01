package com.racoon.waby.ui.home.main

import android.app.PendingIntent
import android.content.Intent
import android.content.Intent.getIntent
import android.content.Intent.parseUri
import android.content.IntentFilter
import android.nfc.NdefMessage
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.racoon.waby.R
import com.racoon.waby.databinding.FragmentMainHomeBinding
import com.racoon.waby.ui.home.map.MapActivity
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Parcelable
import android.provider.Settings
import android.util.Log
import com.racoon.waby.ui.auxiliarActivity
import com.racoon.waby.ui.spot.SpotActivity
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import kotlin.experimental.and
import android.app.Activity


class MainHomeFragment : Fragment() {

    //ViewBiding
    private  var _binding: FragmentMainHomeBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    //nfc
    private val nfcAdapter: NfcAdapter? by lazy {
        NfcAdapter.getDefaultAdapter(context)
    }

    private var pendingIntent:PendingIntent? = null

    private var myTag:Tag? = null


    //viewModel
    private val viewModel by viewModels<MainHomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMainHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.myProfileButton.setOnClickListener {
            gotoMyProfile()
        }

        binding.mapButton.setOnClickListener{
            startActivity(Intent(context, MapActivity::class.java))
        }
        binding.ola.setOnClickListener {
            startActivity(Intent(context, auxiliarActivity::class.java))
        }
        binding.buttonRandom.setOnClickListener{
            startActivity(Intent(context, SpotActivity::class.java))
        }

        binding.nfcButton.setOnClickListener{
            if (nfcAdapter == null){
                Toast.makeText(context,R.string.not_nfc_supported,Toast.LENGTH_SHORT).show()
            }else if (!nfcAdapter!!.isEnabled){
                Toast.makeText(context,R.string.nfc_disabled,Toast.LENGTH_SHORT).show()
                startActivity(Intent(Settings.ACTION_NFC_SETTINGS))
            }
            else{
                Toast.makeText(context,R.string.success_nfc,Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun gotoMyProfile() {
        findNavController().navigate(R.id.action_mainHomeFragment_to_profileFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        readFromIntent(requireActivity().intent)
        pendingIntent = PendingIntent.getActivity(
            context,0,Intent(context, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),0
        )

        val tagDetected = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED)

        tagDetected.addCategory(Intent.CATEGORY_DEFAULT)

    }

    private fun readFromIntent(intent: Intent){
        var action:String? = intent.action
        if (NfcAdapter.ACTION_TAG_DISCOVERED == action
            || NfcAdapter.ACTION_TECH_DISCOVERED == action
            || NfcAdapter.ACTION_NDEF_DISCOVERED == action
        ){
            var rawMsgs:Array<Parcelable>? = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
            var msgs: Array<NdefMessage?>? = null

            if (rawMsgs != null){

                msgs = arrayOfNulls(rawMsgs.size)

                for (i in rawMsgs.indices) {
                    msgs[i] = rawMsgs[i] as NdefMessage
                }
            }
            buildTagViews(msgs)
        }
    }

    private fun buildTagViews(msgs: Array<NdefMessage?>?) {
        if (msgs == null || msgs.isEmpty()) return
        var text = ""
        val payload = msgs[0]!!.records[0].payload
        val UTF_16: Charset = Charset.forName("UTF-16")
        val languageCodeLength: Byte = payload[0] and 51
        try {
            text = String(
                payload,
                languageCodeLength + 1,
                payload.size - languageCodeLength - 1,
                UTF_16
            )
        } catch (e: UnsupportedEncodingException) {
            Log.e("Unsupported", e.toString())
        }

        binding.textView6.setText("Contenido NFC:" + text)
    }

}