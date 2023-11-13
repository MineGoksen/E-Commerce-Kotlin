package com.minegksn.capstone.ui.favorites

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
import com.minegksn.capstone.common.gone
import com.minegksn.capstone.common.viewBinding
import com.minegksn.capstone.data.model.response.GetProductsResponse
import com.minegksn.capstone.data.model.SearchProduct
import com.minegksn.capstone.data.model.response.ProductListUI
import com.minegksn.capstone.databinding.FragmentFavoritesBinding
import com.minegksn.capstone.databinding.FragmentHomeBinding
import com.minegksn.capstone.ui.favorites.FavoritesState
import com.minegksn.capstone.ui.favorites.FavoritesViewModel
import com.minegksn.capstone.ui.favorites.FavoritesFragmentDirections
import com.minegksn.capstone.ui.home.ProductsAdapter
import com.minegksn.capstone.ui.login.SignInState
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private val binding by viewBinding(FragmentFavoritesBinding::bind)
    private lateinit var auth: FirebaseAuth
    private val viewModel by viewModels<FavoritesViewModel>()

    private val favoritesAdapter = FavoritesAdapter(onProductClick = ::onProductClick, onDeleteClick = ::onDeleteClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        viewModel.getFavorites()

        with(binding) {
            rvFavProducts.adapter = favoritesAdapter
            icFavBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }

        observeData()
    }



    private fun observeData() = with(binding) {
        viewModel.favoritesState.observe(viewLifecycleOwner) { state ->
            when (state) {
                //TODO: Add progress bar.
                FavoritesState.Loading -> {
                    TimeUnit.SECONDS.sleep(1L)
                }

                is FavoritesState.SuccessState -> {
                    favoritesAdapter.submitList(state.products)
                }

                is FavoritesState.EmptyScreen -> {
                    rvFavProducts.gone()
                }

                is FavoritesState.ShowPopUp -> {
                    Snackbar.make(requireView(), state.errorMessage, 1000).show()
                }

                else -> {Snackbar.make(requireView(), "error occured", 1000).show()}
            }
        }
    }

    private fun onProductClick(id: Int) {
        findNavController().navigate(FavoritesFragmentDirections.favToDetail(id))
    }

    private fun onDeleteClick(product: ProductListUI) {
        viewModel.deleteFromFavorites(product)
    }
}