package com.example.furniq.ui.power.popular

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.furniq.R
import com.example.furniq.adapters.PopularAdapter
import com.example.furniq.ui.auth.sign_in.Data
import com.example.furniq.data.popular_data.PopularData
import com.example.furniq.databinding.FragmentPopularBinding
import com.example.furniq.sealedClass.SealedClass
import com.example.furniq.ui.auth.sign_in.Demo
import com.example.furniq.ui.auth.sign_in.PData
import com.example.furniq.ui.power.all_products_clicked.PopularClickedFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PopularFragment : Fragment(R.layout.fragment_popular), PopularAdapter.OnItemClickListener  {

    private lateinit var popularAdapter: PopularAdapter
    private lateinit var binding: FragmentPopularBinding
    private val vm: PopularVM by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPopularBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Initialize the adapter
        popularAdapter = PopularAdapter(requireContext(),this)
          vm.getPopular()
        // Set up RecyclerView
        binding.recyclerViewPopular.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewPopular.adapter = popularAdapter

        // Collect ViewModel data
        viewLifecycleOwner.lifecycleScope.launch {
            vm.popularState.collect { state ->
                when (state) {
                    is SealedClass.SuccessData<*> -> {
                        // Hide loading indicator
                         binding.progressPopular.visibility = View.INVISIBLE
                        // Update the adapter with the new data
                        val popularData = state.data as? PopularData
                        Log.d("RRR", "Fragmetnke ppp:${popularData} ")
                        Log.d("RRR", "Fragmetnke State:${state.data} ")
                        if (popularData != null) {
                            popularAdapter.models = popularData.data // Correctly accessing List<PData>

                            Log.d("RRR", "Fragmetnke keldi: ")
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
                     binding.progressPopular.visibility = View.VISIBLE
                        Log.i("TTT", "qalay: ")
                    //binding.errorTextView.visibility = View.GONE
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

        // Load data

    }

    override fun onItemClick(position: Data) {
        val fragment = PopularClickedFragment()
        val bundle = Bundle()
        bundle.putInt("productIdPopular",position.id)
        fragment.arguments = bundle


        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun btnSaveClick(position: Data) {
        TODO("Not yet implemented")
    }

    override fun onAddFavorite(position: Demo) {
        val productId = (position as Data).id
        vm.postFavourites(productId)
    }

    override fun onRemoveFavorite(position: Demo) {
        val productId = (position as Data).id
        vm.deleteFavourites(productId)
    }




}