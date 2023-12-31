package com.minegksn.capstone.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.minegksn.capstone.data.model.response.Product
import com.minegksn.capstone.data.model.response.ProductListUI
import com.minegksn.capstone.databinding.ItemProductBinding

//import com.minegksn.capstone.databinding.ItemProductBinding

class ProductsAdapter(
    private val onProductClick: (Int) -> Unit,
    private val onFavClick: (ProductListUI) -> Unit
) : ListAdapter<ProductListUI, ProductsAdapter.ProductViewHolder>(ProductDiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onProductClick,
            onFavClick
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) = holder.bind(getItem(position))

    class ProductViewHolder(
        private val binding: ItemProductBinding,
        private val onProductClick: (Int) -> Unit,
        private val onFavClick: (ProductListUI) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductListUI) {
            with(binding) {
                tvTitle.text = product.title
                if(!product.saleState) {
                    tvPrice.text = "${product.price} ₺"
                } else {
                    tvPrice.text = "${product.salePrice} ₺"
                }

                Glide.with(ivProduct).load(product.imageOne).into(ivProduct)

                root.setOnClickListener {
                    onProductClick(product.id)
                }

                ivFavorite.setOnClickListener {
                    onFavClick(product)
                }
            }
        }
    }

    class ProductDiffUtilCallBack : DiffUtil.ItemCallback<ProductListUI>() {
        override fun areItemsTheSame(oldItem: ProductListUI, newItem: ProductListUI): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductListUI, newItem: ProductListUI): Boolean {
            return oldItem == newItem
        }
    }
}