package com.minegksn.capstone.ui.bag

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.minegksn.capstone.MainApplication
import com.minegksn.capstone.R
import com.minegksn.capstone.common.gone
import com.minegksn.capstone.common.viewBinding
import com.minegksn.capstone.common.visible
import com.minegksn.capstone.data.model.ClearCartRequest
import com.minegksn.capstone.data.model.response.ClearCartResponse
import com.minegksn.capstone.data.model.DeleteFromCartRequest
import com.minegksn.capstone.data.model.response.DeleteFromCartResponse
import com.minegksn.capstone.data.model.response.GetCartProductDetail
import com.minegksn.capstone.databinding.FragmentBagBinding
import com.minegksn.capstone.ui.detail.DetailState
import com.minegksn.capstone.ui.home.SaleState
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class BagFragment : Fragment(R.layout.fragment_bag) {

    private val binding by viewBinding(FragmentBagBinding::bind)
    private lateinit var auth: FirebaseAuth
    private val viewModel by viewModels<CartViewModel>()
    private val cartAdapter = CartAdapter(onProductClick = ::onProductClick, onProductDeleteClick = :: onProductDeleteClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        viewModel.getCartProducts()
        observeData()


        with(binding) {
            rvCart.adapter = cartAdapter

            icFavBack.setOnClickListener {
                findNavController().navigateUp()
            }

            btnSatNAl.setOnClickListener {
                findNavController().navigate(R.id.bagToPayment)
            }

            tvClear.setOnClickListener {
                clearAllCart()
            }

        }
    }

    private fun observeData() = with(binding) {
        val user = auth.currentUser
        if (user != null) {
            val uid = user.uid
            viewModel.cartState.observe(viewLifecycleOwner) { state ->
                when (state) {
                    //TODO: Add progress bar.
                    CartState.Loading -> {
                        progressBar.visible()
                        tvAmount.gone()
                    }

                    is CartState.SuccessState -> {
                        progressBar.gone()
                        tvAmount.visible()
                        ivEmptyShop.gone()
                        cartAdapter.submitList(state.products)
                    }

                    is CartState.EmptyScreen -> {
                        progressBar.gone()
                        tvAmount.gone()
                        rvCart.gone()
                        ivEmptyShop.visible()
                    }

                    is CartState.ShowPopUp -> {
                        progressBar.gone()
                        Snackbar.make(requireView(), state.errorMessage, 1000).show()
                    }

                    else -> {Snackbar.make(requireView(), "error occur in bag", 1000).show()}
                }
            }
        }

        viewModel.totalPriceAmount.observe(viewLifecycleOwner) { amount->
            tvAmount.text = String.format("₺ %.2f", amount)
        }
    }

    private fun clearAllCart() {
        val user = auth.currentUser
        if (user != null) {
            val uid = user.uid
            viewModel.clearAllCart(uid)
        }
    }

    private fun onProductClick(id: Int) {
        findNavController().navigate(BagFragmentDirections.bagToDetail(id))
    }

    private fun onProductDeleteClick(id: Int) {
        viewModel.deleteProductFromCart(id)
    }
}

