package com.example.furniq.ui.auth.getProfil

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.furniq.MainActivity
import com.example.furniq.R
import com.example.furniq.data.sign_up_data.Data
import com.example.furniq.databinding.FragmentProfilBinding
import com.example.furniq.sealedClass.ProfileSealedClass
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProfileFragment : Fragment(R.layout.fragment_profil) {

    private val vm: ProfileVM by viewModel()
    private var binding: FragmentProfilBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfilBinding.bind(view)
        setUpObserver()

        binding?.btnShigiw?.setOnClickListener {
           // binding?.progresBarProfile?.visibility = View.INVISIBLE
            vm.logOut()

          // findNavController().navigate(R.id.action_profilFragment_to_registerFragment)
            //(activity as? MainActivity)?.setNewLocale(settings.language)
        }
    }

    private fun setUpObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            vm.profileState.collect { result ->
                when (result) {
                    is ProfileSealedClass.Success -> {
                        val pData = result.profileData.data
                        displayProfileData(pData)
                    }
                    is ProfileSealedClass.NetworkError -> {
                        showMessage(result.msg.toString())
                    }
                    is ProfileSealedClass.Error -> {
                        showMessage(result.message)
                    }
                    is ProfileSealedClass.Loading -> {
                    }
                    else -> {}
                }
            }
        }
    }

    private fun displayProfileData(pData: Data) {
        binding?.let {
            it.txtProfilName.text = pData.name
            it.txtProfilPhone.text = pData.phone
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
