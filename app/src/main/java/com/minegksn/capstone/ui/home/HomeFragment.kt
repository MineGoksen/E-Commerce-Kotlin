package com.minegksn.capstone.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Response
import com.minegksn.capstone.MainApplication
import com.minegksn.capstone.R
import com.minegksn.capstone.common.viewBinding
import com.minegksn.capstone.data.model.GetProductsResponse
import com.minegksn.capstone.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback



class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)
    private lateinit var auth: FirebaseAuth

    private val productAdapter = ProductsAdapter(onProductClick = ::onProductClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        getProducts()

        with(binding) {
            rvProducts.adapter = productAdapter
            rvProducts.adapter = productAdapter
            rvProducts.adapter = productAdapter
            // Oturum Kapat düğmesinin tıklanma işlemi

            btnLogout.setOnClickListener {
                FirebaseAuth.getInstance().signOut() // Oturumu kapat
                findNavController().navigate(R.id.homeToLogin)
                // Oturum kapatıldıktan sonra yapılacak işlemleri burada ekleyebilirsiniz.
            }

            btnCart.setOnClickListener {
                findNavController().navigate(R.id.homeToCart)
            }

        }
    }

    private fun getProducts() {
        MainApplication.productService?.getProducts()?.enqueue(object : Callback<GetProductsResponse> {

            override fun onResponse(call: Call<GetProductsResponse>, response: Response<GetProductsResponse>) {
                val result = response.body()

                if (result?.status == 200) {
                    productAdapter.submitList(result.products.orEmpty())
                } else {
                    Toast.makeText(requireContext(), "Bir hata oluştu. Lütfen tekrar deneyin.", Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<GetProductsResponse>, t: Throwable) {
                Log.e("GetProducts", t.message.orEmpty())
            }
        })
    }

    private fun onProductClick(id: Int) {
        findNavController().navigate(HomeFragmentDirections.homeToDetail(id))
    }
}