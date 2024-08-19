package com.example.furniq

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.furniq.settings.Settings
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainContainerFragment : Fragment(R.layout.fragment_main_container) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHostFragment = childFragmentManager.findFragmentById(R.id.fragmentNavHostView) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNavigationView = view.findViewById<BottomNavigationView>(R.id.bottom_Navigatin)
        val izbrannoeMenuItem = bottomNavigationView.menu.findItem(R.id.heartFragment) // ID-ni to'g'ri tekshiring
        izbrannoeMenuItem.isEnabled = isTokenAvailable()

        bottomNavigationView.setupWithNavController(navController)
        bottomNavigationView.itemTextColor = ContextCompat.getColorStateList(requireContext(),R.color.black)
        bottomNavigationView.itemIconTintList = ContextCompat.getColorStateList(requireContext(),R.color.black)


    }

    private fun isTokenAvailable(): Boolean {
        val settings = Settings(requireContext())

        if(settings.token.isEmpty()||settings.token==null){
            Log.d("XXX", "isTokenAvailable--->${settings.token}: ")
            return false
        }
        else
            return true

    }




}