package com.example.capstone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_create.view.*
import kotlinx.android.synthetic.main.fragment_selecthobby.view.*

class SelectFragment:Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view= LayoutInflater.from(activity).inflate(R.layout.fragment_selecthobby,container,false)
        var lastview=LayoutInflater.from(activity).inflate(R.layout.fragment_create,container,false)
        val mActivity2 = activity as CreateActivity
        view.button.setOnClickListener{
            lastview.useraddress.textSize = 40f
            mActivity2.changeFragment(2,"운동")
        }

        return view
    }
}