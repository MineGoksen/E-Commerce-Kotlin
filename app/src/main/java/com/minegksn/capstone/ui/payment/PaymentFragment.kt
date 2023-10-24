package com.minegksn.capstone.ui.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.minegksn.capstone.R
import com.minegksn.capstone.common.viewBinding
import com.minegksn.capstone.databinding.FragmentLoginBinding
import com.minegksn.capstone.databinding.FragmentPaymentBinding

class PaymentFragment : Fragment(R.layout.fragment_payment) {

    private val binding by viewBinding(FragmentPaymentBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            btnOnayla.setOnClickListener {
                val name = editTextCardholderName.text.toString()
                val card_no = editTextCardNumber.text.toString()
                val ay = editTextCardExpiryMonth.text.toString()
                val yil = editTextCardExpiryYear.text.toString()
                val cvc = editTextCvc.text.toString()
                val adres = editTextAddress.text.toString()

                if (checkFields(name,card_no,ay,yil,cvc,adres)) {
                    findNavController().navigate(R.id.paymentToSuccess)
                }else{
                    Snackbar.make(requireView(), "Alanları yeniden kontrol ediniz", 1000).show()
                }
            }

            icOdemeBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    fun checkFields(name: String, card_no:String, ay:String, yil:String, cvc:String, adres: String): Boolean{
        return when {
            name.isEmpty() -> false
            card_no.isEmpty() -> false
            ay.isEmpty() -> false
            yil.isEmpty() -> false
            cvc.isEmpty() -> false
            adres.isEmpty() -> false
            card_no.length < 16 -> false
            else -> true
        }

    }
}