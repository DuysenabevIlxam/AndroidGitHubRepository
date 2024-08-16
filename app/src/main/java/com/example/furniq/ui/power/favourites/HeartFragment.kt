package com.example.furniq.ui.power.favourites

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.furniq.R
import com.example.furniq.adapters.SavedProductsAdapter
import com.example.furniq.data.create_favourite.CreateFavouriteData
import com.example.furniq.data.favourites_data.FData
import com.example.furniq.data.favourites_data.FavouritesData
import com.example.furniq.data.get_all_products_data.PData
import com.example.furniq.databinding.FragmentHeartBinding
import com.example.furniq.sealedClass.SealedClass
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HeartFragment : Fragment(R.layout.fragment_heart), SavedProductsAdapter.OnItemClickListener {

    private var savedProductsAdapter = SavedProductsAdapter(this)

    private lateinit var binding: FragmentHeartBinding
    private val vm: GetFavouritesVM by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHeartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        vm.getFavourites()


        viewLifecycleOwner.lifecycleScope.launch {
            vm.favouriteState.collect { state ->
                when (state) {
                    is SealedClass.SuccessData<*> -> {
                        val favouritesData = state.data as? FavouritesData
                        if (favouritesData!=null){
                            savedProductsAdapter.models = favouritesData.data
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
                        //binding.progressLatest.visibility = View.VISIBLE
                        //binding.errorTextView.visibility = View.GONE
                    }

                    else -> {}
                }


                binding.recyclerViewFavourites.layoutManager = LinearLayoutManager(requireContext())
                binding.recyclerViewFavourites.adapter = savedProductsAdapter


            }
        }
    }

    override fun onItemClick(position: FData) {
        /*
      val fragment = LatestClickedFragment()
      val bundle = Bundle()
      bundle.putInt("productIdLatest",position.id)
      fragment.arguments = bundle
      Log.d("QQQ", "latestID:${position.id} ")

      requireActivity().supportFragmentManager.beginTransaction()
          .replace(R.id.fragment_container, fragment)
          .addToBackStack(null)
          .commit()

       */
    }
}


