package com.racoon.waby.ui.auth.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.racoon.waby.R
import com.racoon.waby.data.repository.UserRepositoryImp
import com.racoon.waby.databinding.FragmentSignUpBinding
import com.racoon.waby.domain.usecases.authuser.AuthUserUseCaseImpl
import com.racoon.waby.ui.auth.login.LoginVMFactory
import com.racoon.waby.ui.auth.login.LoginViewModel

class SignUpFragment : Fragment() {

    //private val viewModel by viewModels<SignUpViewModel>()
    private val  viewModel by viewModels<SignUpViewModel> {
        SignUpVMFactory(AuthUserUseCaseImpl(UserRepositoryImp()))
    }
    private val loginViewModel by viewModels<LoginViewModel> {
        LoginVMFactory(AuthUserUseCaseImpl(UserRepositoryImp()))
    }

    //ViewBiding
    private  var _binding: FragmentSignUpBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registerButton.setOnClickListener {
            setUp()
            setUpViewModel()

        }

        binding.goInButton.setOnClickListener {
            loginSetUp()
            viewModelLoginSetUp()
        }


    }

    private fun setUp() {
        val email = binding.emailEditText.text.toString()
        val passwd = binding.passwordEditText.text.toString()
        val passwdRepeat = binding.repeatPasswordEditText.text.toString()

        viewModel.create(email, passwd,passwdRepeat)

    }

    private fun setUpViewModel() {
        with(viewModel) {
            successLD.observe(viewLifecycleOwner) {
                activity?.also {
                    Toast.makeText(context,R.string.signup_success,Toast.LENGTH_SHORT).show()
                }

            }
            errorLD.observe(viewLifecycleOwner) {
                activity?.also {
                    Toast.makeText(context,R.string.signup_error,Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //para determinar si ir al registro o al home, de momento
    //pongo otro login en el registro que se activa si le damos a un boton

    private fun loginSetUp() {
        val email = binding.emailEditText.text.toString()
        val passwd = binding.passwordEditText.text.toString()

        loginViewModel.login(email, passwd)
    }

    private fun viewModelLoginSetUp() {
        with(loginViewModel) {
            successLD.observe(viewLifecycleOwner) {
                activity?.also {
                    Toast.makeText(context,R.string.login_success,Toast.LENGTH_SHORT).show()
                }
                openRegisterFragment()
            }
            errorLD.observe(viewLifecycleOwner) { msg->
                activity?.also {
                    Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun openRegisterFragment() {
        findNavController().navigate(R.id.action_signUpFragment_to_registerUserFragment)
        //findNavController().navigate(R.id.action_signUpFragment_to_registerUserImagesFragment)
    }
}