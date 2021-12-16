package com.racoon.waby.ui.spot.spothome

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout.HORIZONTAL
import android.widget.RatingBar.OnRatingBarChangeListener
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.racoon.waby.R
import com.racoon.waby.common.MyApplication
import com.racoon.waby.data.model.Spot
import com.racoon.waby.data.repository.SpotRepository
import com.racoon.waby.databinding.FragmentSpotHomeBinding
import com.racoon.waby.ui.spot.wabis.WabisViewModel
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.models.Channel
import kotlinx.android.synthetic.main.fragment_spot_home.*
import kotlinx.android.synthetic.main.fragment_spot_home.view.*
import kotlinx.android.synthetic.main.star_dialog.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import javax.annotation.meta.When
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.roundToInt

@Singleton
class SpotHomeFragment : Fragment() {

    private val spotHomeViewModel by viewModels<SpotHomeViewModel>()
    private val wabisViewModel by viewModels<WabisViewModel>()
    private var _binding: FragmentSpotHomeBinding? = null
    private lateinit var adapter: MySpotAdapter
    private val userId = Firebase.auth.currentUser?.uid.toString()
    private lateinit var idSpot: String
    private val client = ChatClient.instance()
    private lateinit var spotFinal: Spot
    private var imageNumber = 0
    var IMAGE = ""

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadImage()
        idSpot = checkNotNull(activity?.intent?.getStringExtra("idSpot"))

        GlobalScope.launch(Dispatchers.Main) {
            spotFinal = spotHomeViewModel.getThisSpot(idSpot)
            println("ESTE ES EL NOMBRE DEL SPOT ACTUAL")
            println(spotFinal.name)

            val media = spotFinal.images?.get(0) as String
            Glide.with(requireContext()).load(media).into(binding.imageSpot)
            binding.rateNumber.text = String.format("%.1f", spotFinal.rating?.average())
            binding.spotName.text = spotFinal.name
            binding.assistants.text = spotFinal.assistants?.size.toString()
            binding.description.text = spotFinal.description
            binding.website.text = spotFinal.website
            spotHomeViewModel.addAssistant(idSpot, userId)
            setTags(spotFinal.badges)
        }


    }

    private fun setTags(badges: ArrayList<String>?) {
        var countNumber = 0
        if (badges != null) {
            for (badge in badges){

                when(countNumber){

                    0 -> {binding.tag1.text = badge
                        binding.Tag1.visibility = View.VISIBLE
                    }
                    1 -> {binding.tag2.text = badge
                        binding.Tag2.visibility = View.VISIBLE
                    }
                    2 ->{binding.tag3.text = badge
                        binding.Tag3.visibility = View.VISIBLE
                    }
                    3 -> {binding.tag4.text = badge
                        binding.Tag4.visibility = View.VISIBLE
                    }
                    4 -> {binding.tag5.text = badge
                        binding.Tag5.visibility = View.VISIBLE
                    }
                }
                countNumber++
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {


        _binding = FragmentSpotHomeBinding.inflate(inflater, container, false)
        /*val root: View = binding.root


        return root*/
        return binding.root
    }

    @SuppressLint("WrongConstant")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MySpotAdapter(requireContext())
        binding.spotList.layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
        binding.spotList.adapter = adapter

        observeData()
        binding.Rate.setOnClickListener {
            rateSpot()
        }

        binding.nextImg.setOnClickListener {
            nextImage()
        }
        binding.imagePr.setOnClickListener{
            goToMyProfile()
        }


    }

    private fun goToMyProfile() {
        val bundle = bundleOf(
            "image" to IMAGE
        )
        findNavController().navigate(R.id.action_navigation_home_to_myProfileSpotFragment, bundle)
    }

    fun observeData() {
        spotHomeViewModel.getSpotList(idSpot).observe(viewLifecycleOwner, Observer {
            adapter.setSpotList(it)
            adapter.notifyDataSetChanged()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun rateSpot() {
        val builder = AlertDialog.Builder(context)
        val view = layoutInflater.inflate(R.layout.star_dialog, null)
        builder.setView(view)
        val dialog = builder.create()
        dialog.show()


        val average: Float
        val ratingBar = dialog.rBar
        val button = dialog.button

        ratingBar.onRatingBarChangeListener =
            OnRatingBarChangeListener { ratingBar, rating, fromUser ->
                Toast.makeText(context, ratingMessage(rating), Toast.LENGTH_LONG).show()


            }

        button.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                spotHomeViewModel.addRatingToSpot(ratingBar.rating, idSpot)
                dialog.hide()
            }
        }
    }

    fun ratingMessage(float: Float): String {

        var message = "not selected"
        val int = float.toInt()

        when (int) {
            1 -> {
                message = "Sorry to hear that! :("
            }
            2 -> {
                message = "We can listen to you"
            }
            3 -> {
                message = "Not bad :)"
            }
            4 -> {
                message = "Awesome!!"
            }
            else -> {
                message = "We expect you to come back"
            }
        }

        return message
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
                    Glide.with(requireContext()).load(it).into(binding.imagePr)
                }

                IMAGE = media
            }
        }
    }


    fun nextImage(){
        imageNumber++
        if (imageNumber == spotFinal.images?.size ){
            imageNumber = 0
        }
        val media = spotFinal.images?.get(imageNumber) as String
        Glide.with(requireContext()).load(media).into(binding.imageSpot)
    }

}