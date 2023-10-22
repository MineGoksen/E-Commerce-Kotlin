package com.minegksn.capstone.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.minegksn.capstone.R
import com.minegksn.capstone.common.viewBinding
import com.minegksn.capstone.databinding.FragmentLoginBinding
import com.minegksn.capstone.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private val binding by viewBinding(FragmentSignUpBinding::bind)

    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        auth.currentUser?.let {
            findNavController().navigate(R.id.signUpToLogin)
        }

        with(binding) {
            btnSignUp.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()

                if (checkFields(email, password)) {
                    signUp(email, password)
                }
            }

            btntoLogin.setOnClickListener {
                findNavController().navigate(R.id.signUpToLogin)
            }

        }
    }

    private fun checkFields(email: String, password: String): Boolean {
        return when {
            email.isEmpty() -> false
            password.isEmpty() -> false
            password.length < 6 -> false
            else -> true
        }
    }


    private fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            findNavController().navigate(R.id.loginToHome)
        }.addOnFailureListener {
            Snackbar.make(requireView(), it.message.orEmpty(), 1000).show()
        }
    }
}