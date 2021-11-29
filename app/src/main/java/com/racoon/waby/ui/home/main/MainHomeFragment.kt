package com.racoon.waby.ui.home.main

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
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
import android.provider.Settings
import com.racoon.waby.ui.spot.SpotActivity


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

        pendingIntent = PendingIntent.getActivity(
            context,0,Intent(context, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),0
        )

        
        val ndef = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED)
    }
}