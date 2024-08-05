package com.example.furniq.ui.power

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.furniq.R
import com.example.furniq.adapters.ItemAdapter
import com.example.furniq.databinding.FragmentAllProductsBinding
import com.example.furniq.data.get_all_products_data.ProductsData
import com.example.furniq.sealedClass.SealedClass
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class AllProductsFragment : Fragment(R.layout.fragment_all_products), ItemAdapter.OnItemClickListener {

    private lateinit var itemAdapter: ItemAdapter
    private lateinit var binding: FragmentAllProductsBinding
    private val vm: PowerVM by viewModel()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAllProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.getAllProducts()
        // Initialize the adapter
        itemAdapter = ItemAdapter()

        // Set up RecyclerView
        binding.recyclerViewHome.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewHome.adapter = itemAdapter

        // Collect ViewModel data
        viewLifecycleOwner.lifecycleScope.launch {
            vm.powerState.collect { state ->
                when (state) {
                    is SealedClass.Loading -> {
                        // Show loading indicator
                         binding.progressAllProducts.visibility = View.VISIBLE
                        //binding.errorTextView.visibility = View.GONE
                    }
                    is SealedClass.SuccessData<*> -> {
                        // Hide loading indicator
                         binding.progressAllProducts.visibility = View.INVISIBLE
                        // Update the adapter with the new data
                        val productsData = state.data as? ProductsData
                        Log.d("EEE", "productData--->${productsData}: ")
                        Log.d("EEE", "Statedata--->${state.data}: ")
                        if (productsData != null) {
                            itemAdapter.models = productsData.data // Correctly accessing List<PData>
                        }
                    }
                    is SealedClass.ErrorMessage<*> -> {
                        // Hide loading indicator
                        // binding.progressBar.visibility = View.GONE
                        // Show error message
                        // binding.errorTextView.text = state.message
                        // binding.errorTextView.visibility = View.VISIBLE
                    }
                    is SealedClass.NetworkError -> {
                        // Hide loading indicator
                        // binding.progressBar.visibility = View.GONE
                        // Show network error message
                        // binding.errorTextView.text = state.message
                        // binding.errorTextView.visibility = View.VISIBLE
                    }
                    else -> {}
                }
            }
        }

        // Load data

    }

    override fun onItemClick(position: Int) {
        // Handle item click
        val clickedItem = itemAdapter.models[position]
        // Do something with the clicked item
    }


}