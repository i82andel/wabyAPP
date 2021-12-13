package com.racoon.waby.ui.home.main

import android.app.AlertDialog
import android.app.Application
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import android.os.Parcelable
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.zxing.integration.android.IntentIntegrator
import com.racoon.waby.R
import com.racoon.waby.common.MyApplication
import com.racoon.waby.data.model.User
import com.racoon.waby.databinding.FragmentMainHomeBinding
import com.racoon.waby.ui.home.map.MapActivity
import com.racoon.waby.ui.spot.SpotActivity
import com.racoon.waby.ui.spot.chat.ChatActivity
import com.racoon.waby.ui.spot.wabis.WabisViewModel
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.models.image
import io.getstream.chat.android.client.models.name
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import kotlin.experimental.and


class MainHomeFragment : Fragment() {

    //ViewBiding
    private var _binding: FragmentMainHomeBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private val client = ChatClient.instance()

    //nfc
    private val nfcAdapter: NfcAdapter? by lazy {
        NfcAdapter.getDefaultAdapter(context)
    }

    private var pendingIntent: PendingIntent? = null

    private var myTag: Tag? = null
    var IMAGE = ""


    //viewModel
    private val viewModel by viewModels<MainHomeViewModel>()
    private val wabisViewModel by viewModels<WabisViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMainHomeBinding.inflate(inflater, container, false)
        loadImage()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GlobalScope.launch(Dispatchers.Main) {
            val firebaseUser = wabisViewModel.getUser()
            createUserGetStream(firebaseUser)
        }


        binding.myProfileButton.setOnClickListener {
            gotoMyProfile()
        }

        binding.mapButton.setOnClickListener {
            startActivity(Intent(context, MapActivity::class.java))
        }
        binding.ola.setOnClickListener {
            startActivity(Intent(context, ChatActivity::class.java))
        }
        binding.buttonRandom.setOnClickListener {
            startActivity(Intent(context, SpotActivity::class.java))
        }

        binding.nfcButton.setOnClickListener {
            showDefaultDialog()
        }

        binding.imageButton4.setOnClickListener{
            goToWabis()
        }
    }

    private fun createUserGetStream(firebaseUser: User) {


        println("ola- > ${firebaseUser.userName}")
        val user = io.getstream.chat.android.client.models.User(id = firebaseUser.userName!!).apply {
            name = firebaseUser.userName
            image = firebaseUser.images
        }
        val token = client.devToken(user.id)
        println("id = ${firebaseUser.name}\n token = $token")

        client.connectUser(
            user = user,
            token = token
        ).enqueue() { result ->
            if (result.isSuccess) {
                val user: io.getstream.chat.android.client.models.User = result.data().user
                val connectionId: String = result.data().connectionId
            } else {
                println("NO bro")
            }
        }
    }


    private fun gotoMyProfile() {
        val bundle = bundleOf(
            "image" to IMAGE
        )
        findNavController().navigate(R.id.action_mainHomeFragment_to_profileFragment, bundle)
    }

    private fun goToWabis(){
        findNavController().navigate(R.id.action_mainHomeFragment_to_channelHomeFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        readFromIntent(requireActivity().intent)
        pendingIntent = PendingIntent.getActivity(
            context, 0, Intent(context, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0
        )

        val tagDetected = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED)

        tagDetected.addCategory(Intent.CATEGORY_DEFAULT)

    }

    private fun readFromIntent(intent: Intent) {
        var action: String? = intent.action
        if (NfcAdapter.ACTION_TAG_DISCOVERED == action
            || NfcAdapter.ACTION_TECH_DISCOVERED == action
            || NfcAdapter.ACTION_NDEF_DISCOVERED == action
        ) {
            var rawMsgs: Array<Parcelable>? =
                intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
            var msgs: Array<NdefMessage?>? = null

            if (rawMsgs != null) {

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

    private fun initScanner() {
        val integrator = IntentIntegrator.forSupportFragment(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        integrator.setPrompt("Escanea el código QR")
        integrator.setCameraId(0)
        integrator.setBeepEnabled(false)
        integrator.setBarcodeImageEnabled(false)
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        println(result)

        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(context, "Cancelado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context,
                    "El valor escaneado es ${result.contents.toString()}",
                    Toast.LENGTH_SHORT).show()
                    val idSpot = result.contents.toString()
                startActivity(Intent(context, SpotActivity::class.java).putExtra("idSpot", idSpot))
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun showDefaultDialog() {
        var alertDialog = AlertDialog.Builder(context)

        alertDialog.apply {
            setTitle("Unirse al spot")
            setMessage("Seleccione el método con el que se quiere unir al spot en el que se encuentra")
            setPositiveButton("QR") { _, _ ->
                initScanner()
            }
            setNegativeButton("NFC") { _, _ ->
                if (nfcAdapter == null) {
                    Toast.makeText(context, R.string.not_nfc_supported, Toast.LENGTH_SHORT).show()
                } else if (!nfcAdapter!!.isEnabled) {
                    Toast.makeText(context, R.string.nfc_disabled, Toast.LENGTH_SHORT).show()
                    startActivity(Intent(Settings.ACTION_NFC_SETTINGS))
                } else {
                    Toast.makeText(context, R.string.success_nfc, Toast.LENGTH_SHORT).show()
                }
            }
        }.create().show()
    }


    private fun loadImage() {
        val db = Firebase.firestore
        val uid = Firebase.auth.currentUser?.uid as String
        val userList = db.collection("User")

        userList.document(uid).get().addOnSuccessListener { document ->
            if (document.exists()) {
                val media = document.getString("images")
                val storageReference = FirebaseStorage.getInstance()
                val gsReference = storageReference.getReferenceFromUrl(media!!)
                gsReference.downloadUrl.addOnSuccessListener {
                    Glide.with(requireContext()).load(it).into(binding.ImageProfile)
                }

                IMAGE = media

            }
        }
    }

}