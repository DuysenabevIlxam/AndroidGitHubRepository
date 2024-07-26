package com.example.furniq

import android.health.connect.datatypes.units.Power
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.furniq.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(PowerFragment())


        binding.bottomNavigatin.setOnItemSelectedListener{

         when(it.itemId){

             R.id.power ->replaceFragment(PowerFragment())
             R.id.search ->replaceFragment(SearchFragment())
             R.id.shoppinCart ->replaceFragment(ShoppingCartFragment())
             R.id.heart ->replaceFragment(HeartFragment())
             R.id.person ->replaceFragment(PersonFragment())

             else->{

             }

         }

          true

        }

    }

    private fun replaceFragment(fragment:Fragment){
        val fragmentManager=supportFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.freame_layaut,fragment)
        fragmentTransaction.commit()

    }
}