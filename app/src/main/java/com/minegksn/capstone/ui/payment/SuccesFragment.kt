package com.minegksn.capstone.ui.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.minegksn.capstone.R
import com.minegksn.capstone.common.viewBinding
import com.minegksn.capstone.databinding.FragmentPaymentBinding
import com.minegksn.capstone.databinding.FragmentSuccesBinding

class SuccesFragment : Fragment(R.layout.fragment_succes) {

    private val binding by viewBinding(FragmentSuccesBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            btnHome.setOnClickListener {
                findNavController().navigate(R.id.successToHome)
            }
        }
    }


}