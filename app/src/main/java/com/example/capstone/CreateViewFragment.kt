package com.example.capstone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_create.view.*

class CreateViewFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view= LayoutInflater.from(activity).inflate(R.layout.fragment_create,container,false)
        val mActivity = activity as CreateActivity
        view.changehobby.setOnClickListener{
            mActivity.changeFragment(1,"운동")
        }

        view.CreateBtn.setOnClickListener{
            view.useraddress.text="됫노?"
        }
        return view
    }
}