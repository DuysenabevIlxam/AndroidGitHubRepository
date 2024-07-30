package com.example.furniq.ui.auth.sign_up

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.furniq.R
import com.example.furniq.databinding.FragmentRegisterBinding
import com.example.furniq.sealedClass.SealedClass
import com.example.furniq.settings.Settings
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val vm: SignUpVM by viewModel()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRegisterBinding.bind(view)

        val  settings = Settings(requireContext())

        if (settings.isUserLoggedIn()){
            findNavController().navigate(R.id.action_registerFragment_to_profilFragment)
        }



        binding.apply {
            btnRegistraciya.setOnClickListener {
                val name = txtName.text.toString()
                val phone = txtPhone.text.toString()
                val password = txtPassword.text.toString()

                if (name.isNotEmpty() && phone.isNotEmpty() && password.isNotEmpty()) {
                    vm.singUp(name, phone, password, error = "QATE???", networkError = "Internetke jalga")
                } else {
                    if (name.isEmpty()) txtName.error = "Toliqtiriw shart"
                    if (phone.isEmpty()) txtPhone.error = "Toliqtiriw shart"
                    if (password.isEmpty()) txtPassword.error = "Toliqtiriw shart"
                }


            }



            txtKiriw.setOnClickListener {
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment2)
            }



        }

        setUpObserver()
    }

    private fun setUpObserver() {

        lifecycleScope.launch {
            vm.signUpState.collect { result ->
                when (result) {
                    is SealedClass.SuccessData<*> -> {
                         findNavController().navigate(R.id.action_registerFragment_to_profilFragment)
                        Toast.makeText(requireContext(), "Dizimnen tabisli otti", Toast.LENGTH_SHORT).show()
                        Log.d("QQQ", "Success Register boldi->>:")
                    }
                    is SealedClass.NetworkError -> {
                        Toast.makeText(requireContext(), result.msg, Toast.LENGTH_SHORT).show()
                        Log.d("QQQ", "NetworkError boldi->>: ")
                    }
                    is SealedClass.ErrorMessage<*> -> {
                        Toast.makeText(requireContext(), result.error.toString(), Toast.LENGTH_SHORT).show()
                        Log.d("QQQ", "ErrorMessage Register boldi->>:${result.error.toString()} ")
                    }
                    is SealedClass.Loading -> {
                        // Show progress bar
                        Log.d("QQQ", "Loading  Register boldi->>: ")
                    }
                    else -> {}
                }
            }
        }
    }


}

