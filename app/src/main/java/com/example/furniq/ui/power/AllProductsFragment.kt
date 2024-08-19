package com.example.furniq.ui.power

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.furniq.R
import com.example.furniq.adapters.ItemAdapter
import com.example.furniq.ui.auth.sign_in.PData
import com.example.furniq.databinding.FragmentAllProductsBinding
import com.example.furniq.data.get_all_products_data.ProductsData
import com.example.furniq.sealedClass.SealedClass
import com.example.furniq.ui.auth.sign_in.Demo
import com.example.furniq.ui.power.all_products_clicked.AllProductsClickFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class AllProductsFragment : Fragment(R.layout.fragment_all_products), ItemAdapter.OnItemClickListener {

    private lateinit var adapter: ItemAdapter
    private lateinit var binding: FragmentAllProductsBinding
    private val vm: PowerVM by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAllProductsBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.getAllProducts()

        adapter = ItemAdapter(requireContext(),this)
        binding.recyclerViewHome.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewHome.adapter = adapter
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

                        if (productsData != null) {
                            adapter.models = productsData.data // Correctly accessing List<PData>
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
        viewLifecycleOwner.lifecycleScope.launch {
            vm.postState.collect { state ->
                when (state) {
                    is SealedClass.Loading -> {

                    }
                    is SealedClass.SuccessData<*> -> {

                    }
                    is SealedClass.ErrorMessage<*> -> {

                    }
                    is SealedClass.NetworkError -> {

                    }
                    else -> {}
                }


            }
        }


        viewLifecycleOwner.lifecycleScope.launch {
            vm.deleteState.collect { state ->
                when (state) {
                    is SealedClass.Loading -> {

                    }
                    is SealedClass.SuccessData<*> -> {

                    }
                    is SealedClass.ErrorMessage<*> -> {

                    }
                    is SealedClass.NetworkError -> {

                    }
                    else -> {}
                }


            }
        }


    }


    override fun onItemClick(position: PData) {

        val fragment = AllProductsClickFragment()
        val bundle = Bundle()
        bundle.putInt("productId",position.id)
        fragment.arguments = bundle

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()

    }

    override fun btnSaveClick(position: PData) {
    }

    override fun onAddFavorite(position: Demo) {
        val productId = (position as PData).id
        vm.postFavourites(productId)
    }

    override fun onRemoveFavorite(position: Demo) {
        val productId = (position as PData).id
        vm.deleteFavourites(productId)
    }



}