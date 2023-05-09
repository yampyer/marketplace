package com.example.meliexample.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.meliexample.R
import com.example.meliexample.data.models.Product
import com.example.meliexample.databinding.ProductItemLayoutBinding

typealias ProductItemClickListener = (productId: String) -> Unit

class ProductItemAdapter(private val listener: ProductItemClickListener) : RecyclerView.Adapter<ProductItemAdapter.ViewHolder>() {

    private lateinit var binding: ProductItemLayoutBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductItemAdapter.ViewHolder {
        binding = ProductItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ProductItemAdapter.ViewHolder, position: Int) {
        holder.setData(differ.currentList[position])
    }

    override fun getItemCount() = differ.currentList.size

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
        fun setData(item: Product) {
            with(binding) {
                root.setOnClickListener { listener.invoke(item.id) }
                ivPicture.load(item.thumbnail.replace("http:", "https:"))
                tvName.text = item.title
                tvPrice.text = String.format(this@ViewHolder.itemView.context.getString(R.string.currency_format), (item.salePrice ?: item.price))
                item.discounts?.let {
                    tvDiscount.text = String.format(this@ViewHolder.itemView.context.getString(R.string.discount_format), it)
                }
                tvShipping.isVisible = item.shipping?.freeShipping == true
            }
        }

    }

    private val differCallback = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.catalogProductId == newItem.catalogProductId
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
}
