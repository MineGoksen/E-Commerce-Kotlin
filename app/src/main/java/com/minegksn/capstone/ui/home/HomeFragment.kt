package com.minegksn.capstone.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Response
import com.minegksn.capstone.MainApplication
import com.minegksn.capstone.R
import com.minegksn.capstone.common.viewBinding
import com.minegksn.capstone.data.model.response.GetProductsResponse
import com.minegksn.capstone.data.model.SearchProduct
import com.minegksn.capstone.data.model.response.ProductListUI
import com.minegksn.capstone.databinding.FragmentHomeBinding
import com.minegksn.capstone.ui.login.SignInState
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)
    private lateinit var auth: FirebaseAuth

    private val productAdapter = ProductsAdapter(onProductClick = ::onProductClick, onFavClick = ::onFavClick)
    private val viewModel by viewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        viewModel.getProducts()

        with(binding) {
            rvProducts.adapter = productAdapter

            // Oturum Kapat düğmesinin tıklanma işlemi

            bottomNav.setOnItemSelectedListener{
                when (it.itemId) {
                    R.id.cart -> {
                        findNavController().navigate(R.id.homeToCart)
                        true
                    }
                    R.id.fav -> {
                        findNavController().navigate(R.id.homeToFav)
                        true
                    }
                    R.id.logout -> {
                        FirebaseAuth.getInstance().signOut() // Oturumu kapat
                        findNavController().navigate(R.id.homeToLogin)
                        true
                    }

                    else -> { false }
                }
            }

        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().finish()
        }
        observeData()
    }



    private fun observeData() = with(binding) {
        viewModel.homeState.observe(viewLifecycleOwner) { state ->
            when (state) {
                //TODO: Add progress bar.
                HomeState.Loading -> {
                    TimeUnit.SECONDS.sleep(1L)
                }

                is HomeState.SuccessState -> {
                    productAdapter.submitList(state.products)
                }

                is HomeState.EmptyScreen -> {

                }

                is HomeState.ShowPopUp -> {
                    Snackbar.make(requireView(), state.errorMessage, 1000).show()
                }

                else -> {Snackbar.make(requireView(), "error occured", 1000).show()}
            }
        }
    }

    private fun onProductClick(id: Int) {
        findNavController().navigate(HomeFragmentDirections.homeToDetail(id))
    }

    private fun onFavClick(product: ProductListUI) {
        viewModel.addToFavorites(product)
    }
}