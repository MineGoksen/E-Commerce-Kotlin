package com.minegksn.capstone.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.minegksn.capstone.MainApplication
import com.minegksn.capstone.R
import com.minegksn.capstone.common.viewBinding
import com.minegksn.capstone.data.model.AddToCartRequest
import com.minegksn.capstone.data.model.AddToChartResponse
import com.minegksn.capstone.data.model.GetProductDetailResponse
import com.minegksn.capstone.data.roomdb.FavoriteProduct
import com.minegksn.capstone.databinding.FragmentDetailBinding
import com.minegksn.capstone.ui.fav.FavViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val binding by viewBinding(FragmentDetailBinding::bind)
    private val favViewModel by lazy { FavViewModel(requireContext()) }
    private lateinit var auth: FirebaseAuth

    private val args by navArgs<DetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        getProductDetail(args.id)

        with(binding){
            icBack.setOnClickListener {
                findNavController().navigateUp()
            }

        }
    }

    private fun getProductDetail(id: Int) {
        MainApplication.productService?.getProductDetail(id)?.enqueue(object : Callback<GetProductDetailResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<GetProductDetailResponse>,
                response: Response<GetProductDetailResponse>
            ) {
                val result = response.body()

                if (result?.status == 200 && result.product != null) {
                    with(binding) {
                        result.product.let {
                            Glide.with(ivProduct).load(it.imageOne).into(ivProduct)
                            tvTitle.text = it.title
                            tvPrice.text = "${it.price} ₺"
                            tvDescription.text = it.description
                            ratingBar.rating = it.rate?.toFloat()!!
                        }

                        btnAddFav.setOnClickListener {
                            val favoriteProduct = FavoriteProduct(
                                id,
                                result.product.title,
                                result.product.price,
                                result.product.description,
                                result.product.category,
                                result.product.imageOne,
                                result.product.imageTwo,
                                result.product.imageThree,
                                result.product.rate,
                                result.product.count,
                                result.product.saleState)


                            favViewModel.insertFavoriteProduct(favoriteProduct)
                        }

                        btnAddToChart.setOnClickListener {

                            val user = auth.currentUser
                            if (user != null) {
                                // Kullanıcı oturum açmışsa, userId'yi al
                                val uid = user.uid
                                val request = AddToCartRequest(userId = uid, productId = id)

                                // Sepete ürün eklemek için userId'yi kullan
                                val addChartCall = MainApplication.productService?.addChart(
                                    request
                                )

                                addChartCall?.enqueue(object : Callback<AddToChartResponse> {
                                    override fun onResponse(
                                        call: Call<AddToChartResponse>,
                                        response: Response<AddToChartResponse>
                                    ) {
                                        val result = response.body()

                                        if (result?.status == 200) {
                                            val message = "Ürün sepete eklendi." // Göstermek istediğiniz mesaj
                                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                        } else {
                                            println("userId: $uid")
                                            println("productId: $id")
                                            Toast.makeText(requireContext(), result?.message, Toast.LENGTH_SHORT).show()
                                        }
                                    }

                                    override fun onFailure(call: Call<AddToChartResponse>, t: Throwable) {
                                        println("userId: $uid")
                                        println("productId: $id")
                                        Log.e("AddToChartResponse", t.message.orEmpty())
                                    }
                                })
                            } else {
                                Toast.makeText(requireContext(), "Oturum açmanız gerekmektedir.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), result?.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GetProductDetailResponse>, t: Throwable) {
                Log.e("GetProductDetail", t.message.orEmpty())
            }
        })

    }
}