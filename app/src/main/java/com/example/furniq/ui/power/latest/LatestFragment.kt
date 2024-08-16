package com.example.furniq.ui.power.latest

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.furniq.R
import com.example.furniq.adapters.LatestAdapter
import com.example.furniq.adapters.PopularAdapter
import com.example.furniq.data.latest_data.Data
import com.example.furniq.data.latest_data.LatestData
import com.example.furniq.databinding.FragmentLatestBinding
import com.example.furniq.sealedClass.SealedClass
import com.example.furniq.ui.power.all_products_clicked.LatestClickedFragment
import com.example.furniq.ui.power.all_products_clicked.PopularClickedFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class LatestFragment : Fragment(R.layout.fragment_latest) , LatestAdapter.OnItemClickListener{

    private  var latestAdapter= LatestAdapter(this)
    private lateinit var binding: FragmentLatestBinding
    private val vm: LatestVM by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLatestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.getLatest()
        // Initialize the adapter

        // Set up RecyclerView
        binding.recyclerViewLatest.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewLatest.adapter = latestAdapter

        // Collect ViewModel data
        viewLifecycleOwner.lifecycleScope.launch {
            vm.latestState.collect { state ->
                when (state) {
                    is SealedClass.SuccessData<*> -> {
                        // Hide loading indicator
                         binding.progressLatest.visibility = View.INVISIBLE
                        // Update the adapter with the new data
                        val latestData = state.data as? LatestData
                        Log.d("RRR", "Fragmetnke ppp:${latestData} ")
                        Log.d("RRR", "Fragmetnke State:${state.data} ")
                        if (latestData != null) {
                            latestAdapter.models = latestData.data // Correctly accessing List<PData>

                            Log.d("RRR", "Fragmetnke keldi: ")
                        }
                    }
                    is SealedClass.ErrorMessage<*> -> {
                        // Hide loading indicator
                         //binding.progressLatest.visibility = View.INVISIBLE
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
                         binding.progressLatest.visibility = View.VISIBLE
                        //binding.errorTextView.visibility = View.GONE
                    }
                    else -> {}
                }
            }
        }

        // Load data

    }


    override fun onItemClick(position: Data) {
        val fragment = LatestClickedFragment()
        val bundle = Bundle()
        bundle.putInt("productIdLatest",position.id)
        fragment.arguments = bundle
        Log.d("QQQ", "latestID:${position.id} ")

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

}