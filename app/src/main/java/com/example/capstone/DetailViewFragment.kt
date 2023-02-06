package com.example.capstone

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_main.view.*


class DetailViewFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view= LayoutInflater.from(activity).inflate(R.layout.fragment_main,container,false)
        view.detailviewfragment_recyclerview.adapter=DetailViewRecyclerViewAdapter()
        view.detailviewfragment_recyclerview.layoutManager=LinearLayoutManager(activity)


        val fab:FloatingActionButton=view.CreateClub
        val recyclerView:RecyclerView=view.detailviewfragment_recyclerview
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 10 && fab.isShown) {
                    fab.hide()
                }

                if (dy < -10 && !fab.isShown) {
                    fab.show()
                }
                if (!recyclerView.canScrollVertically(-1)) {
                    fab.show()
                }
            }
        })
    fab.setOnClickListener{
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            val intent = Intent(activity, CreateActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(activity, "스토리지 읽기 권한이 없습니다.", Toast.LENGTH_LONG).show()
        }
    }

        val scrapMainLayout: GridLayout =view.imagegrid
        scrapMainLayout.columnCount=5

        val text:TextView=TextView(view.context)
        text.setText("gkdl")
        text.textSize=10f
        val param:GridLayout.LayoutParams=GridLayout.LayoutParams()
        param.marginStart=200
        text.layoutParams=param


        scrapMainLayout.addView(text)


        return view
    }
    inner class DetailViewRecyclerViewAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            var view=LayoutInflater.from(parent.context).inflate(R.layout.item_main,parent,false)
            return CustomViewHolder(view)
        }
        inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        }
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var viewholder=(holder as CustomViewHolder).itemView

        }

        override fun getItemCount(): Int {
            return 13
        }

    }
}