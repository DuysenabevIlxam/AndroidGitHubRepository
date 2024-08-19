package com.example.furniq.ui.power.all_products_clicked

import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.furniq.R
import com.example.furniq.adapters.ImagePagerAdapter
import com.example.furniq.data.get_products_by_id.ProductByIdData
import com.example.furniq.databinding.FragmentLatestClickedBinding
import com.example.furniq.databinding.FragmentPopularClickedBinding
import com.example.furniq.sealedClass.SealedClass
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class LatestClickedFragment : Fragment(R.layout.fragment_latest_clicked) {

    private lateinit var binding: FragmentLatestClickedBinding
    private val vm: ProductsByIdVM by viewModel()
    var quantityCount = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLatestClickedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        arguments?.getInt("productIdLatest")?.let ( vm::getProductsById)

        binding.textQosiwLatest.setOnClickListener {
            quantityCount++
            updateQuantity()
            animateBackgroundColor(binding.textQosiwLatest, Color.argb(150,53,81,92), 150)
        }
        binding.textAliwLatest.setOnClickListener {
            animateBackgroundColor(binding.textAliwLatest, Color.argb(150,53,81,92), 150)
            if(quantityCount>0){
                quantityCount--
                updateQuantity()
            }
        }
        vm.pbiState
            .onEach { state ->
                when (state) {
                    is SealedClass.Loading -> {
                        // Show a loading indicator or similar
                    }

                    is SealedClass.SuccessData<*> -> {
                        // Bind data to UI
                        if (state.data is ProductByIdData) {
                            bindData(state.data)
                        }

                    }

                    is SealedClass.ErrorMessage<*> -> {
                        // Show error message
                        // showError(state.message)
                    }

                    is SealedClass.NetworkError -> {
                        // Show network error message
                        //showError(state.message)
                    }

                    else -> {

                    }
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun bindData(productData: ProductByIdData) {
        // Update UI with product data
        binding.nameMebelLatest.text = productData.data.name.latin
        binding.priceMebelLatest.text = productData.data.price.toString()
        binding.colorMebelLatest.text = productData.data.color.name.latin
        binding.materialMebelLatest.text = productData.data.material.name.latin
        // binding.textCount.text = productData.data.reviews_count.toString()
        binding.textDescriptionLatest.text = productData.data.description.latin
        binding.nameProdavecLatestClicked.text = productData.data.seller.company_name
        binding.starCountLatestClicked.text = productData.data.rating.toString()
        binding.textMugdariLatest.text = quantityCount.toString()
        binding.ostalosCountLatest.text = productData.data.quantity.toString()

        // Set up the ViewPager images dynamically if needed
        val imageUrls = productData.data.images
        val adapter = ImagePagerAdapter(imageUrls)
        binding.viewPagerImagesLatest.adapter = adapter

        binding.dotsIndicatorLatest.setViewPager2(binding.viewPagerImagesLatest)
    }
    private fun showError(message: String) {
        // Show error message to the user
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
    private fun updateQuantity() {
        binding.textMugdariLatest.text = quantityCount.toString()
    }
    private fun animateBackgroundColor(view: View, color: Int, duration: Long) {
        // Set initial color
        view.setBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.transparent))

        // Create an ObjectAnimator to animate the background color
        val colorAnim = ObjectAnimator.ofArgb(view, "backgroundColor", color, ContextCompat.getColor(requireContext(), android.R.color.transparent))
        colorAnim.duration = duration
        colorAnim.start()
    }
}