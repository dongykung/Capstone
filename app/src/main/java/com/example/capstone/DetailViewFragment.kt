package com.example.capstone

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.capstone.data.ClubData
import com.example.capstone.data.SignUpData
import com.example.capstone.data.getclubuid
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.android.synthetic.main.item_main.view.*



class DetailViewFragment: Fragment() {
    lateinit var db : FirebaseFirestore
    var scrapMainLayout:GridLayout?=null
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

        scrapMainLayout=view.imagegrid
        scrapMainLayout?.columnCount=5
        val consMainLayout:ConstraintLayout=view.mainlayout





            //val item= it.toObject(SignUpData::class.java)
          //  view.UserName.text=item?.nickname+"님을"
            //myhobby= item?.interest_array!!



        view.detailviewfragment_recyclerview.adapter=DetailViewRecyclerViewAdapter()
        view.detailviewfragment_recyclerview.layoutManager=LinearLayoutManager(activity)

        return view
    }



    @SuppressLint("NotifyDataSetChanged", "ResourceAsColor")
    inner class DetailViewRecyclerViewAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var clubdata:ArrayList<ClubData> = arrayListOf()

        init{
            clubdata.clear()
            db.collection("user").document(Firebase.auth.currentUser?.uid.toString()).get().addOnSuccessListener{   document->
                val item=document.toObject(SignUpData::class.java)
                view?.UserName?.text=item?.nickname+"님을"
                for(data in item?.interest_array!!){
                        if(data=="운동"){
                            val param=GridLayout.LayoutParams()
                            val test2=Button(context)
                            //test2.setBackgroundColor(R.color.not)
                            test2.setBackgroundResource(R.drawable.shape_for_circle_button)
                             test2.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.xml_sports,0,0)
                            test2.text="운동"
                            param.width =175
                            param.height =237
                            param.marginStart=40
                            test2.layoutParams=param
                            scrapMainLayout?.addView(test2)
                        }
                    if(data=="음악"){
                        val test=Button(context)
                        val param=GridLayout.LayoutParams()
                        test.setBackgroundResource(R.drawable.shape_for_circle_button)
                        test.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.xml_music,0,0)
                        test.text="음악"
                        param.width=175
                        param.height=245
                        param.marginStart=40
                        test.layoutParams=param
                        scrapMainLayout?.addView(test)
                    }
                    if(data=="여행"){
                        val test=Button(context)
                        val param=GridLayout.LayoutParams()
                        test.setBackgroundResource(R.drawable.shape_for_circle_button)
                        test.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.xml_trip,0,0)
                        test.text="여행"
                        param.width=175
                        param.height=245
                        param.marginStart=40
                        test.layoutParams=param
                        scrapMainLayout?.addView(test)
                    }
                    db.collection("category").document(data).get().addOnSuccessListener{    document2->
                        val item2=document2.toObject(getclubuid::class.java)
                        for(data2 in item2?.RoomId!!){
                            db.collection("meeting_room").document(data2).get().addOnSuccessListener{   document3->
                                val item3=document3.toObject(ClubData::class.java)
                                clubdata.add(item3!!)
                                clubdata.sortByDescending { it.timestamp }
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
        @SuppressLint("CheckResult", "SuspiciousIndentation")
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val viewholder=(holder as CustomViewHolder).itemView
                Glide.with(holder.itemView.context).load(clubdata[position].imageUrl).apply(RequestOptions().circleCrop()).into(viewholder.detailviewitem_imageview_content)
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


