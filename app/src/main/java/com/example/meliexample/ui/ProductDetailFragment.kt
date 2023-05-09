package com.example.meliexample.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.meliexample.R
import com.example.meliexample.data.Resource
import com.example.meliexample.databinding.FragmentProductDetailBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ProductDetailViewModel>()
    private val args: ProductDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getProduct(args.itemId)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        setupUI()
        return binding.root
    }

    private fun setupUI() {
        with(binding) {
            viewModel.product.observe(viewLifecycleOwner) { response ->
                when (response.status) {
                    Resource.Status.SUCCESS -> {
                        loading.isGone = true
                        toolbarImage.load(response.data?.pictures?.firstOrNull()?.secureUrl)
                        toolbarLayout.title = response.data?.title
                        productName.text = response.data?.title
                        productPrice.text = String.format(getString(R.string.currency_format), (response.data?.salePrice ?: response.data?.price))
                        response.data?.discounts?.let {
                            productDiscount.text = String.format(getString(R.string.discount_format), it)
                        }
                        response.data?.availableQuantity?.let {
                            productQuantityAvailable.run {
                                    if (it == 0) setTextColor(ContextCompat.getColor(context, R.color.red))
                                    text = resources.getQuantityString(R.plurals.available_quantity, it, it)
                                }
                        }
                        response.data?.soldQuantity?.let {
                            productQuantitySold.run {
                                isVisible = it > 0
                                text = resources.getQuantityString(R.plurals.sold_quantity, it, it)
                            }
                        }
                        productShipping.isVisible = response.data?.shipping?.freeShipping == true
                        fabBuyNow.setOnClickListener {
                            val intent = Intent(Intent.ACTION_VIEW).apply {
                                data = Uri.parse(response.data?.permalink)
                            }
                            startActivity(intent)
                        }
                        if (response.data?.attributes?.isNotEmpty() == true) {
                            val stringBuilder = StringBuilder()
                            response.data.attributes.forEach { attr ->
                                stringBuilder.append(attr.name).append(": ").appendLine(attr.value)
                            }
                            productFeatures.text = stringBuilder.toString()
                        } else {
                            productFeaturesLbl.isGone = true
                        }
                    }
                    Resource.Status.ERROR -> {
                        loading.isGone = true
                        Snackbar.make(root, response.message.toString(), Snackbar.LENGTH_LONG).show()
                    }
                    Resource.Status.LOADING -> {
                        loading.isVisible = true
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
