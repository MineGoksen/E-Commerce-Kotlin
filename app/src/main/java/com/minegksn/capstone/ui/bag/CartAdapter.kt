
package com.minegksn.capstone.ui.bag

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.minegksn.capstone.data.model.Product
import com.minegksn.capstone.databinding.ItemCartBinding

class CartAdapter(
    private val onProductClick: (Int) -> Unit,
    private val onProductDeleteClick: (Int) -> Unit
) : ListAdapter<Product, CartAdapter.ProductViewHolder>(ProductDiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onProductClick,
            onProductDeleteClick
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) = holder.bind(getItem(position))

    class ProductViewHolder(
        private val binding: ItemCartBinding,
        private val onProductClick: (Int) -> Unit,
        private val onProductDeleteClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            with(binding) {
                tvTitle.text = product.title
                tvPrice.text = "${product.price} ₺"
                Glide.with(ivProduct).load(product.imageOne).into(ivProduct)
                root.setOnClickListener {
                    onProductClick(product.id ?: 1)
                }
                // Çöp kutusu ikonuna tıklamayı ele al
                ivDelete.setOnClickListener {
                    onProductDeleteClick(product.id ?: 1)
                }
            }
        }
    }

    class ProductDiffUtilCallBack : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
}
