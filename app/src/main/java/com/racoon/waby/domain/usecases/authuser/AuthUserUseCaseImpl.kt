package com.racoon.waby.domain.usecases.authuser

import com.google.firebase.auth.FirebaseUser
import com.racoon.waby.common.Resource

class AuthUserUseCaseImpl(private val userRepository: UserRepository) : AuthUserUseCase{

    override fun firebaseDefaultAuthSignUp(email: String, passwd: String, repeatedPasswd: String) : Resource<FirebaseUser?>? {
        return userRepository.signUpDefault(email,passwd,repeatedPasswd)
    }

    override fun firebaseDefaultAuthSignIn(email: String, passwd: String) : Resource<FirebaseUser?>{
        return userRepository.signInDefault(email,passwd)
    }

    override fun firebaseDefaultGetCurrentUser() : com.racoon.waby.data.model.User{
        return userRepository.getCurrentUser()
    }

}