package com.example.capstone

import android.Manifest
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.capstone.databinding.ActivityCreateBinding
import com.example.capstone.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.fragment_create.*

class CreateActivity: AppCompatActivity() {
    private lateinit var binding:ActivityCreateBinding
    var createViewFragment=CreateViewFragment()
    var selectViewFragment=SelectFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backbtn.setOnClickListener{
            finish()
        }

        supportFragmentManager.beginTransaction().replace(R.id.createfragment,createViewFragment).commit()




    }
     fun changeFragment(index:Int,hobby:String){
        when(index){
            1->{
                supportFragmentManager.beginTransaction().replace(R.id.createfragment,selectViewFragment).commit()
            }
            2->{
                var bundle=Bundle()
                bundle.putString("hobby",hobby)
                createViewFragment.arguments=bundle
                supportFragmentManager.beginTransaction().replace(R.id.createfragment,createViewFragment).commit()
            }
        }
    }
}