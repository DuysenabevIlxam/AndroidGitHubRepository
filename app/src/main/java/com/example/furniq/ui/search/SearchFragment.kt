package com.example.furniq.ui.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.furniq.R
import com.example.furniq.adapters.CategoriesAdapter
import com.example.furniq.adapters.ItemAdapter
import com.example.furniq.adapters.PopularAdapter
import com.example.furniq.data.CategoriesData.CData
import com.example.furniq.data.CategoriesData.CategoriesData
import com.example.furniq.data.popular_data.PopularData
import com.example.furniq.databinding.FragmentPopularBinding
import com.example.furniq.databinding.FragmentSearchBinding
import com.example.furniq.sealedClass.SealedClass
import com.example.furniq.ui.power.all_products_clicked.AllProductsClickFragment
import com.example.furniq.ui.power.popular.PopularVM
import com.example.furniq.ui.product_by_id_categories.CategoriesByIdFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchFragment : Fragment(R.layout.fragment_search), CategoriesAdapter.OnItemClickListener {

    private lateinit var binding  :FragmentSearchBinding
    private val vm: CategoriesVM by viewModel()
    private  val adapter= CategoriesAdapter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.getCategories()

        binding.recyclerViewSearch.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewSearch.adapter = adapter


        viewLifecycleOwner.lifecycleScope.launch {
            vm.searchState.collect { state ->
                when (state) {
                    is SealedClass.SuccessData<*> -> {
                        // Hide loading indicator
                        binding.progressBarCategories.visibility = View.INVISIBLE
                        // Update the adapter with the new data
                        val categoriesData = state.data as? CategoriesData
                        Log.d("RRR", "Fragmetnke ppp:")
                        Log.d("RRR", "Fragmetnke State:${state.data} ")
                        if (categoriesData != null) {
                            adapter.models = categoriesData.data // Correctly accessing List<PData>

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
                    is SealedClass.Loading -> {
                        // Show loading indicator
                        binding.progressBarCategories.visibility = View.VISIBLE
                        //binding.errorTextView.visibility = View.GONE
                    }
                    else -> {}
                }
            }
        }


    }

    override fun onItemClick(position: CData) {
       // Toast.makeText(requireContext(),"basildi",Toast.LENGTH_SHORT).show()
       // findNavController().navigate(R.id.action_searchFragment_to_categoriesByIdFragment)

        val fragment = CategoriesByIdFragment()
        val bundle = Bundle()
        bundle.putInt("categoryId",position.id)
        Log.d("QQQ", "${position.id} ")

        fragment.arguments = bundle

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }


}