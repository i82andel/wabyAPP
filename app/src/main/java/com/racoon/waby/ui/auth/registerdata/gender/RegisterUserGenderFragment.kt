package com.racoon.waby.ui.auth.registerdata.gender

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.racoon.waby.R

import com.racoon.waby.databinding.FragmentRegisterUserGenderBinding


class RegisterUserGenderFragment : Fragment(), AdapterView.OnItemClickListener {

    //ViewBiding
    private  var _binding: FragmentRegisterUserGenderBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterUserGenderBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUp()
    }

    fun setUp() {
        val genders = resources.getStringArray(R.array.genderType)
        val adapter = context?.let {
            ArrayAdapter(
                it,
                R.layout.list_item,
                genders
            )
        }

        with(binding.genderTypeTextView) {
            setAdapter(adapter)
            onItemClickListener = this@RegisterUserGenderFragment
        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val item = parent?.getItemAtPosition(position).toString()
        Toast.makeText(context,item,Toast.LENGTH_SHORT).show()
    }
}