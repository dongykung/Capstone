package com.example.capstone

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.example.capstone.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailViewFragment=DetailViewFragment()
        supportFragmentManager.beginTransaction().replace(R.id.main_content,detailViewFragment).commit()
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)

        binding.bottomNavigation.setOnItemSelectedListener{
            when(it.itemId){
                R.id.action_home ->{
                    val detailViewFragment=DetailViewFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.main_content,detailViewFragment).commit()

                    return@setOnItemSelectedListener true
                }
                R.id.action_gps ->{

                    return@setOnItemSelectedListener true
                }
                R.id.action_account ->{

                    return@setOnItemSelectedListener true
                }
                R.id.action_more ->{

                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
}