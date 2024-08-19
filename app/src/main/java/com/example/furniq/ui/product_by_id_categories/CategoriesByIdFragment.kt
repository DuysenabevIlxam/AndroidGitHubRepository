package com.example.furniq.ui.product_by_id_categories

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.furniq.R
import com.example.furniq.adapters.CategoryByIdAdapter
import com.example.furniq.ui.auth.sign_in.PData
import com.example.furniq.data.get_all_products_data.ProductsData
import com.example.furniq.databinding.FragmentCategoriesByIdBinding
import com.example.furniq.sealedClass.SealedClass
import com.example.furniq.ui.power.all_products_clicked.AllProductsClickFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoriesByIdFragment : Fragment(R.layout.fragment_categories_by_id), CategoryByIdAdapter.OnItemClickListener {

    private  val adapter= CategoryByIdAdapter(this)
    private lateinit var binding: FragmentCategoriesByIdBinding
    private val vm: CategoriesByIdVM by viewModel()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCategoriesByIdBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getInt("categoryId")?.let ( vm::getCategories)




        // Set up RecyclerView
        binding.recyclerViewCategoriesById.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewCategoriesById.adapter = adapter

        // Collect ViewModel data
        viewLifecycleOwner.lifecycleScope.launch {
            vm.categoryState.collect { state ->
                when (state) {
                    is SealedClass.Loading -> {
                        binding.progressAllProducts.visibility = View.VISIBLE
                    }
                    is SealedClass.SuccessData<*> -> {
                        binding.progressAllProducts.visibility = View.INVISIBLE
                        val productsData = state.data as? ProductsData
                        Log.d("EEE", "productData--->${productsData?.data}: ")
                        Log.d("EEE", "Statedata--->${state.data}: ")
                        if (productsData != null) {
                            adapter.models = productsData.data // Correctly accessing List<PData>
                            productsData.data.forEach { product ->
                                Log.d("YYY", "Category ID: ${product.category_id}")
                            }
                        }
                    }
                    is SealedClass.ErrorMessage<*> -> {
                        Toast.makeText(requireContext(),"Qatelik",Toast.LENGTH_SHORT).show()
                    }
                    is SealedClass.NetworkError -> {

                        Toast.makeText(requireContext(),"Internet penen qatelik bar",Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }
            }
        }
    }
    override fun onItemClick(position: PData) {

        val fragment = AllProductsClickFragment()
        val bundle = Bundle()
        fragment.arguments = bundle
        bundle.putInt("categoryIdProduct",position.id)
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}