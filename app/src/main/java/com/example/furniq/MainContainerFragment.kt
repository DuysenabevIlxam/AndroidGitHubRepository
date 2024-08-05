package com.example.furniq

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainContainerFragment : Fragment(R.layout.fragment_main_container) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHostFragment = childFragmentManager.findFragmentById(R.id.fragmentNavHostView) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNavigationView = view.findViewById<BottomNavigationView>(R.id.bottom_Navigatin)
        bottomNavigationView.setupWithNavController(navController)
        bottomNavigationView.itemTextColor = ContextCompat.getColorStateList(requireContext(),R.color.black)
        bottomNavigationView.itemIconTintList = ContextCompat.getColorStateList(requireContext(),R.color.black)

    }



}