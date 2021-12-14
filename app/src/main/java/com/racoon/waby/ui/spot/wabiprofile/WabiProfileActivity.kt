package com.racoon.waby.ui.spot.wabiprofile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.racoon.waby.R
import com.racoon.waby.data.repository.UserRepositoryImp
import com.racoon.waby.databinding.ActivityWabiProfileBinding
import com.racoon.waby.domain.usecases.authuser.AuthUserUseCaseImpl
import com.racoon.waby.ui.auth.login.MyProfileVMFactory
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class WabiProfileActivity : AppCompatActivity() {

    //ViewModel
    private val viewModel by viewModels<WabiProfileViewModel> {
        MyProfileVMFactory(AuthUserUseCaseImpl(UserRepositoryImp()))
    }

    //Tags de prueba
    var tags : List<String> = listOf(
        ""
    )

    //Variables del bundle
    private var NAME = ""
    private var USERNAME = ""
    private var SURNAME = ""
    private var DESCRIPTION = ""
    private var PHONENUMBER = ""
    private var EMAIL = ""
    private var IMAGE = ""
    private var TAGS = listOf("")
    private lateinit var uid : String

    private lateinit var binding: ActivityWabiProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle: Bundle? = intent.extras
        uid = bundle?.getString("idUser").toString()

        binding = ActivityWabiProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        GlobalScope.launch(Dispatchers.Main) {
            setCurrentUser()
        }
    }

    suspend fun setCurrentUser(){

        val user = WabiProfileViewModel().getUser(uid)

        binding.nameText.setText(user.name + " " + user.surname)
        binding.DescriptionText.setText(user.description)
        binding.emailText.setText(user.email)
        binding.usernameText.setText(user.userName)

        val media = user.images
        val storageReference = FirebaseStorage.getInstance()
        val gsReference = storageReference.getReferenceFromUrl(media!!)
        gsReference.downloadUrl.addOnSuccessListener {
            Glide.with(this).load(it).into(binding.ProfileImage)
        }
        val dateString = user.birthdate!!.date.toString() + "/" + (user.birthdate!!.month.toInt()+1) + "/" + user.birthdate!!.year

        binding.textBD.setText(dateString)

        val tags = user.tags
        setTags(tags)
        //Asigna valores al bundle
        NAME = user.name.toString()
        SURNAME = user.surname.toString()
        USERNAME = user.userName.toString()
        DESCRIPTION = user.description.toString()
        EMAIL = user.email.toString()
        IMAGE = media
        TAGS = tags!!

    }

    private fun setTags(tags: ArrayList<String>?) {
        var countNumber = 0
        if (tags != null) {
            for (tag in tags) {

                when (countNumber) {

                    0 -> {
                        binding.tag1.text = tag
                        binding.Tag1.visibility = View.VISIBLE
                    }
                    1 -> {
                        binding.tag2.text = tag
                        binding.Tag2.visibility = View.VISIBLE
                    }
                    2 -> {
                        binding.tag3.text = tag
                        binding.Tag3.visibility = View.VISIBLE
                    }
                    3 -> {
                        binding.tag4.text = tag
                        binding.Tag4.visibility = View.VISIBLE
                    }
                    4 -> {
                        binding.tag5.text = tag
                        binding.Tag5.visibility = View.VISIBLE
                    }
                }
                countNumber++
            }
        }
    }

}