package com.racoon.waby.ui.auth.registerdata.images

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.racoon.waby.R
import com.racoon.waby.databinding.FragmentRegisterUserImagesBinding
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class RegisterUserImagesFragment : Fragment() {

    private val IMAGE_CHOOSE = 1000

    private val SELECT_ACTIVITY = 50
    private var imageUri: Uri? = null

    private val database = Firebase.database
    private val myRef = database.getReference("/profileImages")
    private lateinit var storage: FirebaseStorage

    private var NAME = "name"
    private var SURNAME = "surname"
    private var USERNAME = "username"
    private var GENDER = "gender"
    private var DAY = 0
    private var MONTH = 0
    private var YEAR = 0
    private var TAGS = ArrayList<String>()
    private var IMAGES = ArrayList<String>()

    //ViewBiding
    private var _binding: FragmentRegisterUserImagesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val name = arguments?.getString("name")
        val surname = arguments?.getString("surname")
        val username = arguments?.getString("username")
        val gender = arguments?.getString("gender")
        val day = arguments?.getInt("day")
        val month = arguments?.getInt("month")
        val year = arguments?.getInt("year")
        val tags = arguments?.getStringArrayList("tags")

        storage = Firebase.storage

        println(name)
        println(surname)
        println(username)
        println(gender)
        println(day)
        println(month)
        println(year)
        println(tags)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterUserImagesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.nextButton.setOnClickListener {
            binding.progressBar.visibility = VISIBLE
            goNext()

        }

        binding.galleryButton.setOnClickListener {
            chooseImageGallery()
        }
    }

    private fun chooseImageGallery() {
        /*binding.imageView1.setOnClickListener {
            activity?.let { it1 ->
                ImageController.selectPhotoFromGallery(it1,SELECT_ACTIVITY)
            }
        }*/

        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_CHOOSE)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == IMAGE_CHOOSE && resultCode == Activity.RESULT_OK) {
            imageUri = data?.data
            binding.imageView1.setImageURI(imageUri)
        }
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
        val bitmap = (binding.imageView1.drawable as BitmapDrawable).bitmap
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


    private fun goNext() {
        fileUpload()


        findNavController().navigate(
            R.id.action_registerUserImagesFragment_to_registerUserDescriptionFragment)


    }


}