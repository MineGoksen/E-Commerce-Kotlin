package com.minegksn.capstone.ui.fav

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.minegksn.capstone.R
import com.minegksn.capstone.common.viewBinding
import com.minegksn.capstone.data.roomdb.FavoriteProduct
import com.minegksn.capstone.databinding.FragmentFavoriteBinding
import com.minegksn.capstone.ui.bag.BagFragmentDirections
import com.minegksn.capstone.ui.bag.CartAdapter


class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private val binding by viewBinding(FragmentFavoriteBinding::bind)
    private val favViewModel by lazy { FavViewModel(requireContext()) }
    private val FavAdapter = FavAdapter(onProductClick = ::onProductClick, onProductDeleteClick= :: onProductDeleteClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            icFavBack.setOnClickListener {
                findNavController().navigateUp()
            }

            getFav()
        }

    }

    fun getFav() {
        favViewModel._productBasket.observe(viewLifecycleOwner) { favoriteProducts ->
            FavAdapter.submitList(favoriteProducts)
            if (favoriteProducts.isNotEmpty()) {
                binding.rvCart.visibility = View.VISIBLE
                //binding.emptyBasketText.visibility = View.GONE
            } else {
                binding.rvCart.visibility = View.GONE
                //binding.emptyBasketText.visibility = View.VISIBLE
            }
        }
    }

    private fun onProductClick(id: Int) {
        findNavController().navigate(FavoriteFragmentDirections.favToDetail(id))
    }

    private fun onProductDeleteClick(id: Int) {
        favViewModel.deleteProductFromBasket(id)
    }
}
