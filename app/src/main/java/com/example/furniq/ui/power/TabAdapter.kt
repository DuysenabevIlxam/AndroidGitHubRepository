package com.example.furniq.ui.power

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.furniq.ui.power.latest.LatestFragment
import com.example.furniq.ui.power.popular.PopularFragment

class TabAdapter(fragment:Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 3
    }
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AllProductsFragment()
            1 -> PopularFragment()
            2 -> LatestFragment()
            else -> AllProductsFragment()
        }
    }
}