package com.example.meliexample.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.meliexample.R
import com.example.meliexample.data.Resource
import com.example.meliexample.databinding.FragmentProductListBinding
import com.example.meliexample.utils.hideKeyboard
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListFragment : Fragment() {

    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ProductListViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            searchView.run {
                onActionViewExpanded()
                Handler(Looper.getMainLooper()).postDelayed({
                    hideKeyboard()
                }, 500)
                setOnQueryTextListener(object : OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        query?.let {
                            hideKeyboard()
                            viewModel.searchProducts(it)
                        }
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        return true
                    }
                })
            }

            viewModel.searchResponse.observe(viewLifecycleOwner) { response ->
                when (response.status) {
                    Resource.Status.SUCCESS -> {
                        loading.isGone = true
                        tvStartSearchingHere.isGone = true
                        llResults.isVisible = true
                        tvResultsFor.text = String.format(
                            getString(R.string.showing_results_for),
                            response.data?.results?.size,
                            response.data?.query
                        )
                        productList.adapter = ProductItemAdapter {
                            findNavController().navigate(ProductListFragmentDirections.showProductDetail(it))
                        }.also {
                            it.differ.submitList(response.data?.results)
                        }
                    }
                    Resource.Status.ERROR -> {
                        loading.isGone = true
                        Snackbar.make(root, response.message.toString(), Snackbar.LENGTH_LONG).show()
                    }
                    Resource.Status.LOADING -> {
                        llResults.isGone = true
                        tvStartSearchingHere.isGone = true
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
