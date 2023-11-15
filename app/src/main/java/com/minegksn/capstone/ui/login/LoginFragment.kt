package com.minegksn.capstone.ui.login

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.minegksn.capstone.R
import com.minegksn.capstone.common.gone
import com.minegksn.capstone.common.viewBinding
import com.minegksn.capstone.common.visible
import com.minegksn.capstone.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private val binding by viewBinding(FragmentLoginBinding::bind)

    private val viewModel by viewModels<LoginViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            btnSignIn.setOnClickListener {
                viewModel.signIn(
                    etEmail.text.toString(),
                    etPassword.text.toString()
                )
            }

            btnSignUp.setOnClickListener {
                findNavController().navigate(R.id.loginToSignUp)
            }
        }

        checkIfUserIsAuthenticated()

        observeData()
    }

    private fun checkIfUserIsAuthenticated() {
        val firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser

        if (currentUser != null) {
            // User is already authenticated, navigate to the home screen
            findNavController().navigate(R.id.loginToHome)
        }
    }

    private fun observeData() = with(binding) {
        viewModel.signInState.observe(viewLifecycleOwner) { state ->
            when (state) {
                SignInState.Loading -> progressBar.visible()

                is SignInState.GoToHome -> {
                    progressBar.gone()
                    findNavController().navigate(R.id.loginToHome)
                }

                is SignInState.ShowPopUp -> {
                    progressBar.gone()
                    Snackbar.make(requireView(), state.errorMessage, 1000).show()
                }

                else -> {Snackbar.make(requireView(), "ALO error occured", 1000).show()}
            }
        }
    }
}