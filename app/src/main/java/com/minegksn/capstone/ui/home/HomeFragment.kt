package com.minegksn.capstone.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.addCallback
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.minegksn.capstone.R
import com.minegksn.capstone.common.gone
import com.minegksn.capstone.common.viewBinding
import com.minegksn.capstone.common.visible
import com.minegksn.capstone.data.model.response.ProductListUI
import com.minegksn.capstone.data.model.response.ProductUI
import com.minegksn.capstone.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit



@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)
    private lateinit var auth: FirebaseAuth

    private val productAdapter = ProductsAdapter(onProductClick = ::onProductClick, onFavClick = ::onFavClick)
    private val viewModel by viewModels<HomeViewModel>()

    private val saleAdapter = SaleAdapter(onProductClick = ::onProductClick, onFavClick = ::onFavClick)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        viewModel.getProducts()
        viewModel.getSaleProducts()


        with(binding) {
            rvProducts.adapter = productAdapter
            rvSaleProducts.adapter = saleAdapter

            ivLogout.setOnClickListener {
                FirebaseAuth.getInstance().signOut()
                findNavController().navigate(R.id.homeToLogin)
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
                HomeState.Loading -> progressBar.visible()

                is HomeState.SuccessState -> {
                    progressBar.gone()
                    productAdapter.submitList(state.products)
                }

                is HomeState.EmptyScreen -> {
                    progressBar.gone()

                }

                is HomeState.ShowPopUp -> {
                    progressBar.gone()
                    Snackbar.make(requireView(), state.errorMessage, 1000).show()
                }

                else -> {Snackbar.make(requireView(), "error occured", 1000).show()}
            }
        }

        viewModel.saleState.observe(viewLifecycleOwner) { it ->
            when (it) {
                SaleState.Loading -> progressBar.visible()

                is SaleState.SuccessState -> {
                    progressBar.gone()
                    saleAdapter.submitList(it.products)
                }

                is SaleState.EmptyScreen -> {
                    progressBar.gone()
                }

                is SaleState.ShowPopUp -> {
                    progressBar.gone()
                    Snackbar.make(requireView(), it.errorMessage, 1000).show()
                }
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