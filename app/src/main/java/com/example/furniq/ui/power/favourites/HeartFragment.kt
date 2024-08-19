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
import com.example.furniq.adapters.ItemAdapter
import com.example.furniq.adapters.SavedProductsAdapter
import com.example.furniq.ui.auth.sign_in.FData
import com.example.furniq.data.favourites_data.FavouritesData
import com.example.furniq.databinding.FragmentHeartBinding
import com.example.furniq.sealedClass.SealedClass
import com.example.furniq.ui.auth.sign_in.Demo
import com.example.furniq.ui.auth.sign_in.PData
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HeartFragment : Fragment(R.layout.fragment_heart), SavedProductsAdapter.OnItemClickListener {

    private lateinit var savedProductsAdapter : SavedProductsAdapter
    private lateinit var binding: FragmentHeartBinding
    private val vm: GetFavouritesVM by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHeartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        vm.getFavourites()
        savedProductsAdapter = SavedProductsAdapter(requireContext(),this)
        binding.recyclerViewFavourites.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewFavourites.adapter = savedProductsAdapter
        viewLifecycleOwner.lifecycleScope.launch {
            vm.favouriteState.collect { state ->
                when (state) {
                    is SealedClass.SuccessData<*> -> {
                        val favouritesData = state.data as? FavouritesData
                        if (favouritesData!=null){
                            if (favouritesData.data.isEmpty()){
                                binding.constraintVisibl.visibility=View.VISIBLE
                            }else{
                                binding.constraintVisibl.visibility=View.INVISIBLE
                                savedProductsAdapter.models = favouritesData.data
                            }

                        }
                        binding.progressHeart.visibility = View.INVISIBLE
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
                        binding.progressHeart.visibility = View.VISIBLE
                        //binding.errorTextView.visibility = View.GONE
                    }else -> {}
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

    override fun btnSaveClick(position: FData) {
    }
    override fun onAddFavorite(position: Demo) {
        val productId = (position as FData).id
        vm.postFavourites(productId)
    }
    override fun onRemoveFavorite(position: Demo) {
        val productId = (position as FData).id
        vm.deleteFavourites(productId)
    }
}


