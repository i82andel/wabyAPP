package com.racoon.waby.ui.auth.registerdata.name

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.racoon.waby.R
import com.racoon.waby.common.SingleLiveEvent

class RegisterUserViewModel: ViewModel() {

    private val errorSLE = SingleLiveEvent<Int>()
    private val successSLE = SingleLiveEvent<Int>()
    val errorLD: LiveData<Int> = errorSLE
    val successLD: LiveData<Int> = successSLE

    fun setValues(name: String, surname: String, username: String) {
        if (name.isEmpty()) {
            errorSLE.value = R.string.register_name_error
            return
        }
        else if (surname.isEmpty()) {
            errorSLE.value = R.string.register_surname_error
            return
        }
        else if (username.isBlank()) {
            errorSLE.value = R.string.register_username_error
            return
        }
        else {
            successSLE.value = R.string.register_success
        }
    }

}