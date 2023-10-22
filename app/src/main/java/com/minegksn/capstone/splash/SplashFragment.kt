package com.minegksn.capstone.splash

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.minegksn.capstone.R
import com.minegksn.capstone.common.viewBinding
import com.minegksn.capstone.databinding.FragmentLoginBinding
import com.minegksn.capstone.databinding.FragmentSplashBinding


class SplashFragment : Fragment(R.layout.fragment_splash) {

    private lateinit var binding: FragmentSplashBinding // Binding sınıfını doğru şekilde tanımlayın

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 3 saniye bekleyip sonra action ile LoginActivity'ye geçiş yap
        Handler().postDelayed({
            findNavController().navigate(R.id.splashToLogin)
        }, 3000)
    }
}
