package com.example.capstone

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.icu.lang.UCharacter.IndicPositionalCategory.LEFT
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.view.marginStart
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstone.data.ClubData
import com.example.capstone.data.SignUpData
import com.example.capstone.data.getclubuid
import com.google.android.gms.tasks.Task
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_login.view.*
import kotlinx.android.synthetic.main.activity_signup.view.*
import kotlinx.android.synthetic.main.fragment_create.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.android.synthetic.main.item_main.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await


class DetailViewFragment: Fragment() {
    lateinit var db : FirebaseFirestore
    var myhobby:ArrayList<String>?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view= LayoutInflater.from(activity).inflate(R.layout.fragment_main,container,false)
        db= Firebase.firestore




        val uid= Firebase.auth.currentUser?.uid
        var test:String=""
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
        val consMainLayout:ConstraintLayout=view.mainlayout





            //val item= it.toObject(SignUpData::class.java)
          //  view.UserName.text=item?.nickname+"님을"
            //myhobby= item?.interest_array!!



        view.detailviewfragment_recyclerview.adapter=DetailViewRecyclerViewAdapter()
        view.detailviewfragment_recyclerview.layoutManager=LinearLayoutManager(activity)

        return view
    }



    @SuppressLint("NotifyDataSetChanged")
    inner class DetailViewRecyclerViewAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var clubdata:ArrayList<ClubData> = arrayListOf()

        init{
            clubdata.clear()
            db.collection("user").document(Firebase.auth.currentUser?.uid.toString()).get().addOnSuccessListener{   document->
                val item=document.toObject(SignUpData::class.java)
                for(data in item?.interest_array!!){
                    db.collection("category").document(data.toString()).get().addOnSuccessListener{    document2->
                        val item2=document2.toObject(getclubuid::class.java)
                        for(data2 in item2?.RoomId!!){
                            db.collection("meeting_room").document(data2).get().addOnSuccessListener{   document3->
                                val item3=document3.toObject(ClubData::class.java)
                                clubdata.add(item3!!)
                                notifyDataSetChanged()
                                println(item3)
                            }
                        }
                    }
                }
            }

            }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view=LayoutInflater.from(parent.context).inflate(R.layout.item_main,parent,false)
            return CustomViewHolder(view)
        }
        inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        }
        @SuppressLint("CheckResult")
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var viewholder=(holder as CustomViewHolder).itemView
                Glide.with(holder.itemView.context).load(clubdata[position].imageUrl).into(viewholder.detailviewitem_imageview_content)
                viewholder.ClubName.text=clubdata[position].title
                viewholder.NumberCount.text= clubdata[position].max.toString()
                viewholder.ClubExplain.text=clubdata[position].info_text
                viewholder.CardView.setOnClickListener{
                    println("클릭ㅋㅋ")
                }
        }


        override fun getItemCount(): Int {
            return clubdata.size
        }

    }
}


