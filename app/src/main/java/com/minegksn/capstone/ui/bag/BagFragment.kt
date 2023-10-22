package com.minegksn.capstone.ui.bag

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.google.firebase.auth.FirebaseAuth
import com.minegksn.capstone.MainApplication
import com.minegksn.capstone.R
import com.minegksn.capstone.common.viewBinding
import com.minegksn.capstone.data.model.GetCartProductDetail
import com.minegksn.capstone.databinding.FragmentBagBinding
import com.minegksn.capstone.ui.home.HomeFragmentDirections
import com.minegksn.capstone.ui.home.ProductsAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BagFragment : Fragment(R.layout.fragment_bag) {

    private val binding by viewBinding(FragmentBagBinding::bind)
    private lateinit var auth: FirebaseAuth
    private val cartsAdapter = CartAdapter(onProductClick = ::onProductClick, onProductDeleteClick= :: onProductDeleteClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        getProducts()

        with(binding) {
            rvCart.adapter = cartsAdapter
            rvCart.adapter = cartsAdapter
            rvCart.adapter = cartsAdapter
        }
    }

    private fun getProducts() {
        val user = auth.currentUser
        if (user != null) {
            val uid = user.uid
            MainApplication.productService?.getCartProduct(uid)?.enqueue(object :
                Callback<GetCartProductDetail> {

                override fun onResponse(
                    call: Call<GetCartProductDetail>,
                    response: Response<GetCartProductDetail>
                ) {
                    val result = response.body()

                    if (result?.status == 200) {
                        cartsAdapter.submitList(result.products.orEmpty())
                    } else {
                        Toast.makeText(requireContext(), "Bir hata OLDU.", Toast.LENGTH_SHORT)
                            .show()

                    }
                }

                override fun onFailure(call: Call<GetCartProductDetail>, t: Throwable) {
                    Log.e("GetProducts", t.message.orEmpty())
                }
            })
        }
    }

    private fun onProductClick(id: Int) {
        findNavController().navigate(BagFragmentDirections.bagToDetail(id))
    }

    private fun onProductDeleteClick(id: Int) {

    }

}

