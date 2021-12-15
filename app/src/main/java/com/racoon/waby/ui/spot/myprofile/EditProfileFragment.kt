package com.racoon.waby.ui.spot.myprofile

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.racoon.waby.R
import com.racoon.waby.common.SingleLiveEvent
import com.racoon.waby.data.repository.UserRepositoryImp
import com.racoon.waby.databinding.FragmentEditProfile2Binding
import com.racoon.waby.databinding.FragmentEditProfileBinding
import com.racoon.waby.databinding.FragmentProfileBinding
import com.racoon.waby.domain.usecases.authuser.AuthUserUseCaseImpl
import com.racoon.waby.ui.auth.login.MyProfileVMFactory
import com.racoon.waby.ui.home.editprofile.EditProfileViewModel
import com.racoon.waby.ui.home.myprofile.MyProfileViewModel
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class EditProfileFragment : Fragment() {

    //ViewBiding
    private  var _binding: FragmentEditProfile2Binding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private val errorSLE = SingleLiveEvent<Int>()
    //variables del binding
    private var NAME = ""
    private var SURNAME = ""
    private var IMAGE = ""
    private var USERNAME = ""
    private var TAGS = listOf<String>("")
    private var DESCRIPTION = ""

    private val IMAGE_CHOOSE = 1000
    private var imageUri: Uri? = null

    private lateinit var storage: FirebaseStorage


    //ViewModel
    private val viewModel by viewModels<EditProfileViewModel> {
        MyProfileVMFactory(AuthUserUseCaseImpl(UserRepositoryImp()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditProfile2Binding.inflate(inflater,container,false)
        storage = Firebase.storage
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        NAME = arguments?.get("name") as String
        SURNAME = arguments?.get("surname") as String
        IMAGE = arguments?.get("image") as String
        USERNAME = arguments?.get("username") as String
        DESCRIPTION = arguments?.get("description") as String
        TAGS = arguments?.getStringArrayList("tags")!!
        loadUser()

        binding.buttonGallery.setOnClickListener {
            chooseImageGallery()

        }

        binding.buttonSave.setOnClickListener {
            if (binding.textName.text.isEmpty()) {
                Toast.makeText(context, R.string.edit_name, Toast.LENGTH_SHORT)
                    .show()

            }
            else if (binding.textSurname.text.isEmpty()) {
                Toast.makeText(context, R.string.edit_surname, Toast.LENGTH_SHORT)
                    .show()
            }

            else if (binding.textUsername.text.isEmpty()) {
                Toast.makeText(context, R.string.edit_username, Toast.LENGTH_SHORT)
                    .show()
            }

            else if (binding.textDescription.text.isEmpty()) {
                Toast.makeText(context, R.string.edit_description, Toast.LENGTH_SHORT)
                    .show()
            }

            else{
                binding.progressBar2.visibility = View.VISIBLE
                editProfile()

                findNavController().navigate(R.id.action_editProfileFragment2_to_navigation_home)
            }


        }

    }

    private fun loadImage(){
        val media = arguments?.getString("image")
        val storageReference = FirebaseStorage.getInstance()
        val gsReference = storageReference.getReferenceFromUrl(media!!)
        gsReference.downloadUrl.addOnSuccessListener {
            Glide.with(requireContext()).load(it).into(binding.imageProfile)
        }
    }

    private fun loadUser(){
        binding.textName.setText(NAME)
        binding.textSurname.setText(SURNAME)
        binding.textDescription.setText(DESCRIPTION)
        binding.textUsername.setText(USERNAME)
        loadTags()
        loadImage()
    }

    private fun loadTags(){

        for(item in TAGS){
            if (item == binding.chip1.text){ binding.chip1.isChecked = true}
            if (item == binding.chip2.text){ binding.chip2.isChecked = true}
            if (item == binding.chip3.text){ binding.chip3.isChecked = true}
            if (item == binding.chip4.text){ binding.chip4.isChecked = true}
            if (item == binding.chip5.text){ binding.chip5.isChecked = true}
            if (item == binding.chip6.text){ binding.chip6.isChecked = true}
            if (item == binding.chip7.text){ binding.chip7.isChecked = true}
            if (item == binding.chip8.text){ binding.chip8.isChecked = true}
        }
    }

    private fun editProfile(){

        val userId = Firebase.auth.currentUser?.uid.toString()

        val db = Firebase.firestore
        db.collection("User")
            .document(userId)
            .update(
                hashMapOf(
                    "name" to binding.textName.text.toString(),
                    "surname" to binding.textSurname.text.toString(),
                    "username" to binding.textUsername.text.toString(),
                    "description" to binding.textDescription.text.toString(),
                    "tags" to (getTags()) as ArrayList<String>)

                        as Map<String, Any>
            )
            .addOnSuccessListener {
                Toast.makeText(context, "Datos actualizados", Toast.LENGTH_SHORT)
                    .show()

            }.addOnFailureListener {

                Toast.makeText(context, R.string.firestore_upload_failure, Toast.LENGTH_SHORT)
                    .show()

            }

        fileUpload()
    }

    private fun getTags() : List<String>{
        var tags = listOf<String>()

        if ( binding.chip1.isChecked == true){ tags += binding.chip1.text.toString()}
        if ( binding.chip2.isChecked == true){ tags += binding.chip2.text.toString()}
        if ( binding.chip3.isChecked == true){ tags += binding.chip3.text.toString()}
        if ( binding.chip4.isChecked == true){ tags += binding.chip4.text.toString()}
        if ( binding.chip5.isChecked == true){ tags += binding.chip5.text.toString()}
        if ( binding.chip6.isChecked == true){ tags += binding.chip6.text.toString()}
        if ( binding.chip7.isChecked == true){ tags += binding.chip7.text.toString()}
        if ( binding.chip8.isChecked == true){ tags += binding.chip8.text.toString()}


        return tags
    }

    private fun fileUpload() {

        val storageRef = storage.reference
        val name = Firebase.auth.currentUser?.uid.toString()
        val sdf = SimpleDateFormat("dd/M/yyyy_hh:mm:ss")
        val currentDate = sdf.format(Date()).toString()
        val pathImage = storageRef.child("profiles/$name/$currentDate.png")

        // Get the data from an ImageView as bytes
        //binding.imageView1.isDrawingCacheEnabled = true
        //binding.imageView1.buildDrawingCache()
        val bitmap = (binding.imageProfile.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 20, baos)
        val data = baos.toByteArray()

        var uploadTask = pathImage.putBytes(data)
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
            Toast.makeText(context, R.string.register_images_error, Toast.LENGTH_SHORT).show()
        }.addOnSuccessListener { taskSnapshot ->
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            Toast.makeText(context, R.string.register_images_success, Toast.LENGTH_SHORT).show()
        }


        val url = pathImage.downloadUrl.toString()
        uploadDataFirestore(currentDate)

    }

    private fun uploadDataFirestore(currentDate: String ) {
        val userId = Firebase.auth.currentUser?.uid.toString()
        val ola = "gs://racoonapps-cd246.appspot.com/profiles/3sD45jdH8zVcugo04umnOvNUqw63/10/12/2021_06:29:39.png"
        val url = "gs://racoonapps-cd246.appspot.com/profiles/$userId/$currentDate.png"
        val db = Firebase.firestore
        db.collection("User")
            .document(userId)
            .update("images", url)
            .addOnSuccessListener {
                Toast.makeText(context, R.string.firestore_upload_success, Toast.LENGTH_SHORT)
                    .show()

            }.addOnFailureListener {

                Toast.makeText(context, R.string.firestore_upload_failure, Toast.LENGTH_SHORT)
                    .show()

            }
    }

    private fun chooseImageGallery() {

        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_CHOOSE)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == IMAGE_CHOOSE && resultCode == Activity.RESULT_OK) {
            imageUri = data?.data
            binding.imageProfile.setImageURI(imageUri)
        }
    }

}