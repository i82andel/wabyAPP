package com.racoon.waby.ui.auth.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.racoon.waby.R
import com.racoon.waby.databinding.FragmentLoginBinding
import com.racoon.waby.domain.usecases.authuser.AuthUserUseCaseImpl
import java.util.*


class LoginFragment : Fragment() {

    private val GOOGLE_SIGN_IN = 10
    private val YEAR = 1
    private val MONTH = 1
    private val DAY = 1
    private val TAGS = arrayListOf<String>()

    //private val viewModel by viewModels<LoginViewModel>()
    private val viewModel by viewModels<LoginViewModel> {
        LoginVMFactory(AuthUserUseCaseImpl(UserRepositoryImp()))
    }

    //ViewBiding
    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registerButton.setOnClickListener {
            openSignUp()
        }
        binding.signInButton.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            setUp()
            viewModelSetup(view)
            binding.progressBar.visibility = View.GONE
        }

        binding.googleButton.setOnClickListener {
            setUpGoogle()
        }
    }



    /*private fun uploadDataFirestore() {
        val birthdate = Calendar.getInstance()

        birthdate.clear()
        birthdate.set(Calendar.YEAR,YEAR)
        birthdate.set(Calendar.MONTH,MONTH)
        birthdate.set(Calendar.DAY_OF_MONTH,DAY)

        val userId = Firebase.auth.currentUser?.uid.toString()
        val url = "gs://racoonapps-cd246.appspot.com/profiles/placeholder.png"
        val db = Firebase.firestore
        val email = Firebase.auth.currentUser?.email.toString()

        val data = hashMapOf(
            "name" to "NAME",
            "surname" to "SURNAME",
            "gender" to "GENDER",
            "birthdate" to Timestamp(birthdate.timeInMillis/1000,0),
            "email" to email,
            "username" to "USERNAME",
            "tags" to TAGS,
            "images" to url
        )

        db.collection("User")
            .document(userId)
            .set(data)
            .addOnSuccessListener {
                Toast.makeText(context, R.string.firestore_upload_success, Toast.LENGTH_SHORT)
                    .show()

            }.addOnFailureListener {

                Toast.makeText(context, R.string.firestore_upload_failure, Toast.LENGTH_SHORT)
                    .show()

            }
    }*/

    private fun setUpGoogle() {
        val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.google_token))
            .requestEmail()
            .build()

        val googleClient = GoogleSignIn.getClient(requireActivity(),googleConf)
        googleClient.signOut()

        startActivityForResult(googleClient.signInIntent,GOOGLE_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                println("try")
                val account = task.getResult(ApiException::class.java)

                if (account != null) {

                    val credential = GoogleAuthProvider.getCredential(account.idToken,null)
                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {

                        if (it.isSuccessful) {
                            Toast.makeText(context, R.string.login_success, Toast.LENGTH_SHORT)
                                .show()
                            firstTimeGoogleSignUp()
                            viewModel.gotoHome(requireView())
                        }else {
                            Toast.makeText(context, R.string.login_error, Toast.LENGTH_SHORT).show()
                        }

                    }
                }
            }catch (e: ApiException) {
                println("Google exception")
                Toast.makeText(context, R.string.google_login_error, Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun firstTimeGoogleSignUp() {
        val userId = Firebase.auth.currentUser?.uid.toString()
        val db = Firebase.firestore
        val docRef = db.collection("User").document(userId)

        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null && !document.exists()) {
                    findNavController().navigate(R.id.action_loginFragment_to_phoneNumberFragment)
                }
            }
            .addOnFailureListener {
            }
    }


    private fun setUp() {

        val email = binding.emailEditText2.text.toString()
        val passwd = binding.passwordEditText2.text.toString()

        viewModel.login(email, passwd)

    }

    private fun viewModelSetup(view: View) {
        with(viewModel) {
            signUpLD.observe(viewLifecycleOwner) {
                openSignUp()
            }
            successLD.observe(viewLifecycleOwner) {
                activity?.also {
                    Toast.makeText(context, R.string.login_success, Toast.LENGTH_SHORT).show()
                }
                openHome(view)
            }
            errorLD.observe(viewLifecycleOwner) { msg ->
                activity?.also {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun openHome(view: View) {
        viewModel.gotoHome(view)
    }

    private fun openSignUp() {
        findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)

    }
}