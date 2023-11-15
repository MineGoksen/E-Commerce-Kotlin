package com.minegksn.capstone.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
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
import com.minegksn.capstone.databinding.FragmentFavoritesBinding
import com.minegksn.capstone.databinding.FragmentSearchBinding
import com.minegksn.capstone.ui.favorites.FavoritesState
import com.minegksn.capstone.ui.favorites.FavoritesViewModel
import com.minegksn.capstone.ui.favorites.FavoritesFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private val binding by viewBinding (FragmentSearchBinding::bind)

    private val searchViewModel by viewModels<SearchViewModel>()

    private val searchAdapter = SearchAdapter(onProductClick = ::onProductClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()

        with(binding) {

            rvSearchProducts.adapter = searchAdapter

            with(searchViewModel) {
                searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {

                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        if (newText != null) {
                            if (newText.length >= 3) {
                                searchProduct(newText)
                            } else {
                                searchAdapter.submitList(emptyList())
                            }
                        }
                        return false
                    }
                })
            }
        }
    }

    private fun observeData() = with(binding) {
        searchViewModel.searchState.observe(viewLifecycleOwner) { state ->
            when (state) {
                SearchState.Loading -> progressBar.visible()

                is SearchState.SuccessState -> {
                    progressBar.gone()
                    searchAdapter.submitList(state.products)
                }

                is SearchState.EmptyScreen -> {
                    progressBar.gone()
                    rvSearchProducts.gone()
                }

                is SearchState.ShowPopUp -> {
                    progressBar.gone()
                    Snackbar.make(requireView(), state.errorMessage, 1000).show()
                }
            }
        }
    }

    private fun onProductClick(id: Int){
        findNavController().navigate(SearchFragmentDirections.searchToDetail(id))
    }
}