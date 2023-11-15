package com.minegksn.capstone.ui.login

import android.os.Bundle
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
import com.minegksn.capstone.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private val binding by viewBinding(FragmentSignUpBinding::bind)

    private val viewModel by viewModels<SignUpViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            btnSignUp.setOnClickListener {
                viewModel.signUp(
                    etEmail.text.toString(),
                    etPassword.text.toString()
                )
            }

            btntoLogin.setOnClickListener {
                findNavController().navigate(R.id.signUpToLogin)
            }
        }

        observeData()
    }

    private fun observeData() = with(binding) {
        viewModel.signUpState.observe(viewLifecycleOwner) { state ->
            when (state) {

                SignUpState.Loading -> progressBar.visible()
                is SignUpState.GoToHome -> {
                    progressBar.gone()
                    findNavController().navigate(R.id.signupToHome)
                }

                is SignUpState.ShowPopUp -> {
                    progressBar.gone()
                    Snackbar.make(requireView(), state.errorMessage, 1000).show()
                }

                else -> {Snackbar.make(requireView(), "Kullanıcı ekleme başarılı.", 1000).show()}
            }
        }
    }
}