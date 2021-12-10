package com.racoon.waby.ui.home.myprofile

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.racoon.waby.R
import com.racoon.waby.data.model.Tag
import com.racoon.waby.data.repository.UserRepositoryImp
import com.racoon.waby.databinding.FragmentProfileBinding
import com.racoon.waby.domain.usecases.authuser.AuthUserUseCaseImpl
import com.racoon.waby.ui.auth.login.LoginVMFactory
import com.racoon.waby.ui.auth.login.LoginViewModel
import com.racoon.waby.ui.auth.login.MyProfileVMFactory
import com.racoon.waby.ui.auth.signup.SignUpVMFactory
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.item.view.*
import kotlinx.android.synthetic.main.spot_item.view.*
import kotlinx.android.synthetic.main.wabi_item.view.*
import java.net.URL
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class MyProfileFragment : Fragment() {

    //ViewBiding
    private  var _binding: FragmentProfileBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    //ViewModel
    private val viewModel by viewModels<MyProfileViewModel> {
        MyProfileVMFactory(AuthUserUseCaseImpl(UserRepositoryImp()))
    }

    //Tags de prueba
    var tags : List<Tag> = listOf(
        //Tag("01", "Futbol"),
        //Tag("02", "Musica"),
        //Tag("03", "Pintar")
    )

    //Variables del bundle
    private var NAME = ""
    private var USERNAME = ""
    private var SURNAME = ""
    private var DESCRIPTION = ""
    private var PHONENUMBER = ""
    private var EMAIL = ""



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater,container,false)
        setCurrentUser()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()
        binding.settingsButton.setOnClickListener {
            gotoSettings()
        }
    }

    //Obtiene info sobre el usuario actual
    private fun setCurrentUser(){
        //user = viewModel.getCurrentUser()
            //USUARIO DE PRUEBA
        val db = Firebase.firestore
        val uid = Firebase.auth.currentUser?.uid as String
        val userList = db.collection("User")

        userList.document(uid).get().addOnSuccessListener { document ->
            if (document.exists()) {
                binding.nameText.setText(document.get("name") as String? + " " + document.get("surname") as String?)
                binding.DescriptionText.setText(document.get("description") as String?)
                binding.emailText.setText(document.get("email") as String?)
                binding.usernameText.setText(document.get("username") as String?)
                val media = document.getString("images")
                val storageReference = FirebaseStorage.getInstance()
                val gsReference = storageReference.getReferenceFromUrl(media!!)
                gsReference.downloadUrl.addOnSuccessListener {
                    Glide.with(requireContext()).load(it).into(binding.ProfileImage)
                }
                Log.d("creation", "$media")
                binding.textBD.setText(document.data?.get("birthdate").toString())
                binding.textPhone.setText(document.get("phoneNumber") as String?)
                tags = document.get("tags") as List<Tag>

                //Asigna valores al bundle
                NAME = binding.nameText.text as String
                USERNAME = binding.usernameText.text as String
                PHONENUMBER = binding.textPhone.text as String
                DESCRIPTION = binding.DescriptionText.text as String
                EMAIL = binding.emailText.text as String
            }
        }
    }


    //Para ir a Profile Setting. Pasa bundle para recuperar datos
    private fun gotoSettings() {
        val bundle = bundleOf(
            "phoneNumber" to PHONENUMBER,
            "name" to NAME,
            "surname" to SURNAME,
            "username" to USERNAME,
            "description" to DESCRIPTION,
            "email" to EMAIL
        )
        findNavController().navigate(R.id.action_profileFragment_to_settingsFragment, bundle)
    }

    //Inicia el RecyclerView para mostrar Tags
    private fun initRecycler(){
        TagsList.layoutManager = LinearLayoutManager(context)
        //TagsList.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        val adapter = TagAdapter(tags)
        TagsList.adapter = adapter
    }
}