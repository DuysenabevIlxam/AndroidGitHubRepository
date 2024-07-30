package com.example.furniq.ui.auth.sign_in

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.furniq.R
import com.example.furniq.databinding.FragmentLoginBinding
import com.example.furniq.sealedClass.SealedClass
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private val vm: SignInVM by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigateUp()
        }

        binding.apply {
            btnLogin.setOnClickListener {
                val phone = inputPhone.text.toString()
                val password = inputParol.text.toString()
                if (phone.isNotEmpty() && password.isNotEmpty()) {
                    vm.signIn(
                        phone = phone,
                        password = password,
                        networkError = "Internetti Jalga",
                        error = "Qate"
                    )
                } else {
                    if (phone.isEmpty()) {
                        inputPhone.error = "Toliqtirin"
                    }
                    if (password.isEmpty()) {
                        inputParol.error = "Toliqtirin"
                    }
                }

            }
        }

        setUpObservers()
    }

    private fun setUpObservers() {
        lifecycleScope.launch {
            vm.loginState.collect { result ->
                when (result) {
                    is SealedClass.SuccessData<*> -> {
                        showMessage("Login successful")
                        findNavController().navigate(R.id.action_loginFragment2_to_profilFragment)
                    }
                    is SealedClass.NetworkError -> {
                        showMessage(result.msg ?: "Connection Error")
                    }
                    is SealedClass.ErrorMessage<*> -> {
                        showMessage(result.error.toString())
                    }
                    is SealedClass.Loading -> {
                        // Show progress bar if necessary
                    }
                    else -> {}
                }
            }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }
}
