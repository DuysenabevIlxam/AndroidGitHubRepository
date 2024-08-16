package com.example.furniq.ui.power.all_products_clicked

import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.furniq.R
import com.example.furniq.adapters.ImagePagerAdapter
import com.example.furniq.data.get_products_by_id.ProductByIdData
import com.example.furniq.databinding.FragmentAllProductsClickBinding
import com.example.furniq.sealedClass.SealedClass
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel


class AllProductsClickFragment : Fragment(R.layout.fragment_all_products_click) {

    private lateinit var binding: FragmentAllProductsClickBinding
    private val vm: ProductsByIdVM by viewModel()
    var quantityCount = 0
    private var isHeartFilled = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllProductsClickBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // findNavController().popBackStack()

        arguments?.getInt("productId")?.let ( vm::getProductsById)
        arguments?.getInt("categoryIdProduct")?.let ( vm::getProductsById)

        binding.imgHeartAllProductsClicked.setOnClickListener{
            toggleHeartIcon()

        }


        binding.textQosiw.setOnClickListener {
            quantityCount++
            updateQuantity()
            animateBackgroundColor(binding.textQosiw, Color.argb(150,53,81,92), 150)
        }
        binding.textAliw.setOnClickListener {
            animateBackgroundColor(binding.textAliw, Color.argb(150,53,81,92), 150)
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

                        binding.progressAllProductsClicked.visibility = View.VISIBLE
                    }

                    is SealedClass.SuccessData<*> -> {
                        // Bind data to UI
                        if (state.data is ProductByIdData) {
                            bindData(state.data)
                        }
                        binding.progressAllProductsClicked.visibility = View.INVISIBLE

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
        binding.nameMebel.text = productData.data.name.latin
        binding.priceMebel.text = productData.data.price.toString()
        binding.colorMebel.text = productData.data.color.name.latin
        binding.materialMebel.text = productData.data.material.name.latin
        // binding.textCount.text = productData.data.reviews_count.toString()
        binding.textDescription.text = productData.data.description.latin
        binding.nameProdavecAllProductsClicked.text = productData.data.seller.company_name
        binding.starCountAllProductsClicked.text = productData.data.rating.toString()
        binding.textMugdari.text = quantityCount.toString()
        binding.ostalosCount.text = productData.data.quantity.toString()

        // Set up the ViewPager images dynamically if needed
        val imageUrls = productData.data.images
        val adapter = ImagePagerAdapter(imageUrls)
        binding.viewPagerImages.adapter = adapter

        binding.dotsIndicator.setViewPager2(binding.viewPagerImages)
    }
    private fun showError(message: String) {
        // Show error message to the user
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
    private fun updateQuantity() {
        binding.textMugdari.text = quantityCount.toString()
    }
    private fun animateBackgroundColor(view: View, color: Int, duration: Long) {
        // Set initial color
        view.setBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.transparent))

        // Create an ObjectAnimator to animate the background color
        val colorAnim = ObjectAnimator.ofArgb(view, "backgroundColor", color, ContextCompat.getColor(requireContext(), android.R.color.transparent))
        colorAnim.duration = duration
        colorAnim.start()
    }

    private fun toggleHeartIcon() {
        if (isHeartFilled) {
            // Change to empty heart icon
            binding.imgHeartAllProductsClicked.setImageResource(R.drawable.ic_saved)
        } else {
            // Change to filled heart icon
            binding.imgHeartAllProductsClicked.setImageResource(R.drawable.heart_svgrepo_com)
        }
        isHeartFilled = !isHeartFilled
    }
}