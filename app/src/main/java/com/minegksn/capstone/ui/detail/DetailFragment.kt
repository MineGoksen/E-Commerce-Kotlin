package com.minegksn.capstone.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.minegksn.capstone.R
import com.minegksn.capstone.common.gone
import com.minegksn.capstone.common.viewBinding
import com.minegksn.capstone.common.visible
import com.minegksn.capstone.databinding.FragmentDetailBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.minegksn.capstone.MainApplication
import com.minegksn.capstone.data.model.AddToCartRequest
import com.minegksn.capstone.data.model.response.AddToChartResponse
import com.minegksn.capstone.ui.home.HomeState
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val binding by viewBinding(FragmentDetailBinding::bind)
    private lateinit var auth: FirebaseAuth

    private val args by navArgs<DetailFragmentArgs>()
    private val viewModel by viewModels<DetailViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        viewModel.getProductDetail(args.id)
        observeData()

        with(binding){
            icBack.setOnClickListener {
                findNavController().navigateUp()
            }

            btnAddToChart.setOnClickListener {
                val user = auth.currentUser
                if (user != null) {
                    // Kullanıcı oturum açmışsa, userId'yi al
                    val uid = user.uid
                    viewModel.addCart(uid, args.id)}
                else {
                    Toast.makeText(requireContext(), "Oturum açmanız gerekmektedir.", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    private fun observeData() = with(binding) {
        viewModel.detailState.observe(viewLifecycleOwner) { state ->
            when (state) {
                //TODO: Add progress bar.
                DetailState.Loading -> {
                    TimeUnit.SECONDS.sleep(1L)
                }

                is DetailState.SuccessState -> {
                    Glide.with(ivProduct).load(state.product.imageOne).into(ivProduct)
                    tvTitle.text = state.product.title
                    tvPrice.text = "${state.product.price} ₺"
                    tvDescription.text = state.product.description
                    ratingBar.rating = state.product.rate.toFloat()
                }
                is DetailState.ShowPopUp -> {
                    Snackbar.make(requireView(), state.errorMessage, 1000).show()
                }
                else -> {Snackbar.make(requireView(), "Sepete eklendi. ", 1000).show()}
            }
        }
    }
}