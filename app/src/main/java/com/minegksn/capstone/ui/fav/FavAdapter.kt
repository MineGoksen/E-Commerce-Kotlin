
package com.minegksn.capstone.ui.fav

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.minegksn.capstone.data.model.Product
import com.minegksn.capstone.data.roomdb.FavoriteProduct
import com.minegksn.capstone.databinding.ItemCartBinding


class FavAdapter (
    val onProductClick: (Int) -> Unit,
    val onProductDeleteClick: (Int) -> Unit
) : ListAdapter<FavoriteProduct, FavAdapter.ProductViewHolder>(ProductDiffUtilCallBack()) {

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

        fun bind(product: FavoriteProduct) {
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

    class ProductDiffUtilCallBack : DiffUtil.ItemCallback<FavoriteProduct>() {
        override fun areItemsTheSame(oldItem: FavoriteProduct, newItem: FavoriteProduct): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FavoriteProduct, newItem: FavoriteProduct): Boolean {
            return oldItem == newItem
        }
    }

}
