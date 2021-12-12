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
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.racoon.waby.R
import com.racoon.waby.data.model.Spot
import com.racoon.waby.data.repository.SpotRepository
import com.racoon.waby.databinding.FragmentSpotHomeBinding
import kotlinx.android.synthetic.main.star_dialog.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.roundToInt

@Singleton
class SpotHomeFragment: Fragment() {

    private val spotHomeViewModel by viewModels<SpotHomeViewModel>()
    private var _binding: FragmentSpotHomeBinding? = null
    private lateinit var adapter: MySpotAdapter
    private val userId = Firebase.auth.currentUser?.uid.toString()
    private lateinit var idSpot: String

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        idSpot = checkNotNull(activity?.intent?.getStringExtra("idSpot"))
        Toast.makeText(context,
            "Estoy en $idSpot",
            Toast.LENGTH_SHORT).show()

        GlobalScope.launch (Dispatchers.Main){
            var spotFinal = spotHomeViewModel.getThisSpot(idSpot)
            println("ESTE ES EL NOMBRE DEL SPOT ACTUAL")
            println(spotFinal.name)
            binding.rateNumber.text = spotFinal.rating?.average()?.roundToInt().toString()
            binding.spotName.text = spotFinal.name
            spotHomeViewModel.addAssistant(idSpot,userId)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
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
        binding.spotList.layoutManager = LinearLayoutManager(context,HORIZONTAL,false)
        binding.spotList.adapter = adapter

        observeData()
        binding.Rate.setOnClickListener {
            rateSpot()
        }


    }

    fun observeData(){
        spotHomeViewModel.getSpotList().observe(viewLifecycleOwner, Observer {
            adapter.setSpotList(it)
            adapter.notifyDataSetChanged()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun rateSpot(){
        val builder = AlertDialog.Builder(context)
        val view = layoutInflater.inflate(R.layout.star_dialog, null)
        builder.setView(view)
        val dialog = builder.create()
        dialog.show()


        val average : Float
        val ratingBar = dialog.rBar
        val button = dialog.button

        ratingBar.onRatingBarChangeListener = OnRatingBarChangeListener { ratingBar, rating, fromUser ->
            Toast.makeText(context, ratingMessage(rating), Toast.LENGTH_LONG).show()


        }

        button.setOnClickListener {
            GlobalScope.launch (Dispatchers.Main){
                spotHomeViewModel.addRatingToSpot(ratingBar.rating,idSpot)
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


}