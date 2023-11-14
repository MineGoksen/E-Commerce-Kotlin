
package com.minegksn.capstone.ui.bag

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.minegksn.capstone.data.model.response.Product
import com.minegksn.capstone.data.model.response.ProductListUI
import com.minegksn.capstone.databinding.ItemCartBinding
import com.minegksn.capstone.databinding.ItemProductBinding
import com.minegksn.capstone.ui.home.ProductsAdapter

class CartAdapter(
    private val onProductClick: (Int) -> Unit,
    private val onProductDeleteClick: (Int) -> Unit
) : ListAdapter<ProductListUI, CartAdapter.ProductViewHolder>(ProductDiffUtilCallBack()) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
            return ProductViewHolder(
                ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                onProductClick ,
                onProductDeleteClick
            )
        }

        override fun onBindViewHolder(holder: ProductViewHolder, position: Int) = holder.bind(getItem(position))

        class ProductViewHolder(
            private val binding: ItemCartBinding,
            private val onProductClick: (Int) -> Unit,
            private val onProductDeleteClick: (Int) -> Unit
        ) : RecyclerView.ViewHolder(binding.root) {

            fun bind(product: ProductListUI) {
                with(binding) {
                    tvTitle.text = product.title
                    tvPrice.text = "${product.price} ₺"

                    Glide.with(ivProduct).load(product.imageOne).into(ivProduct)

                    root.setOnClickListener {
                        onProductClick(product.id)
                    }

                    ivDelete.setOnClickListener {
                        onProductDeleteClick(product.id)
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
