package com.example.capstone

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.icu.lang.UCharacter.IndicPositionalCategory.LEFT
import android.os.Bundle
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
import com.example.capstone.data.ClubData
import com.example.capstone.data.SignUpData
import com.example.capstone.data.getclubuid
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_login.view.*
import kotlinx.android.synthetic.main.activity_signup.view.*
import kotlinx.android.synthetic.main.fragment_main.view.*


class DetailViewFragment: Fragment() {

    lateinit var db : FirebaseFirestore
    lateinit var myhobby:ArrayList<String>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view= LayoutInflater.from(activity).inflate(R.layout.fragment_main,container,false)
        view.detailviewfragment_recyclerview.adapter=DetailViewRecyclerViewAdapter()
        view.detailviewfragment_recyclerview.layoutManager=LinearLayoutManager(activity)

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




        db.collection("user").document(Firebase.auth.currentUser?.uid.toString()).get().addOnSuccessListener {  document->
            val item=document.toObject(SignUpData::class.java)
            view.UserName.text=item?.nickname+"님을"
            myhobby= item?.interest_array!!
            for((index,data)in item.interest_array!!.withIndex()){
                if(data=="게임"){
                    val imgbtn= ImageButton(view.context)
                    imgbtn.setImageResource(R.drawable.icon_game)
                    imgbtn.scaleType=(ImageView.ScaleType.FIT_XY)

                   // imgbtn.setBackgroundColor(Color.parseColor("#eeeeee"))
                    imgbtn.setBackgroundResource(R.drawable.shape_for_circle_button)

                    val param:GridLayout.LayoutParams=GridLayout.LayoutParams()
                    param.width=200
                    param.height=200
                    param.marginStart=20
                    imgbtn.layoutParams=param
                    scrapMainLayout.addView(imgbtn)



                }
                if(data=="사진"){
                    val imgbtn:ImageButton= ImageButton(view.context)
                    imgbtn.setImageResource(R.drawable.icon_photo)
                    imgbtn.scaleType=(ImageView.ScaleType.FIT_XY)
                    imgbtn.setBackgroundColor(Color.parseColor("#eeeeee"))
                    imgbtn.setBackgroundResource(R.drawable.shape_for_circle_button)
                    val param:GridLayout.LayoutParams=GridLayout.LayoutParams()
                    param.width=170
                    param.height=170
                    param.marginStart=20
                    imgbtn.layoutParams=param
                    scrapMainLayout.addView(imgbtn)
                }
                if(data=="운동"){
                    val imgbtn:ImageButton= ImageButton(view.context)
                    imgbtn.setImageResource(R.drawable.icon_sports)
                    imgbtn.scaleType=(ImageView.ScaleType.FIT_XY)
                    imgbtn.setBackgroundColor(Color.parseColor("#eeeeee"))
                    imgbtn.setBackgroundResource(R.drawable.shape_for_circle_button)
                    val param:GridLayout.LayoutParams=GridLayout.LayoutParams()
                    param.width=170
                    param.height=170
                    param.marginStart=20
                    imgbtn.layoutParams=param
                    scrapMainLayout.addView(imgbtn)
                }
                if(data=="여행"){
                    val imgbtn:ImageButton= ImageButton(view.context)
                    imgbtn.setImageResource(R.drawable.icon_trip)
                    imgbtn.scaleType=(ImageView.ScaleType.FIT_XY)
                    imgbtn.setBackgroundColor(Color.parseColor("#eeeeee"))
                    imgbtn.setBackgroundResource(R.drawable.shape_for_circle_button)
                    val param:GridLayout.LayoutParams=GridLayout.LayoutParams()
                    param.width=170
                    param.height=170
                    param.marginStart=20
                    imgbtn.layoutParams=param
                    scrapMainLayout.addView(imgbtn)
                }
                if(data=="음악"){
                    val imgbtn:ImageButton= ImageButton(view.context)
                    imgbtn.setImageResource(R.drawable.icon_music)
                    imgbtn.scaleType=(ImageView.ScaleType.FIT_XY)
                    imgbtn.setBackgroundColor(Color.parseColor("#eeeeee"))
                    imgbtn.setBackgroundResource(R.drawable.shape_for_circle_button)
                    val param:GridLayout.LayoutParams=GridLayout.LayoutParams()
                    param.width=170
                    param.height=170
                    param.marginStart=20
                    imgbtn.layoutParams=param
                    scrapMainLayout.addView(imgbtn)
                }
                if(data=="사교/직업"){
                    val imgbtn:ImageButton= ImageButton(view.context)
                    imgbtn.setImageResource(R.drawable.icon_job)
                    imgbtn.scaleType=(ImageView.ScaleType.FIT_XY)
                    imgbtn.setBackgroundColor(Color.parseColor("#eeeeee"))
                    imgbtn.setBackgroundResource(R.drawable.shape_for_circle_button)
                    val param:GridLayout.LayoutParams=GridLayout.LayoutParams()
                    param.width=170
                    param.height=170
                    param.marginStart=20
                    imgbtn.layoutParams=param
                    scrapMainLayout.addView(imgbtn)
                }
                if(data=="독서"){
                    val imgbtn:ImageButton= ImageButton(view.context)
                    imgbtn.setImageResource(R.drawable.icon_read)
                    imgbtn.scaleType=(ImageView.ScaleType.FIT_XY)
                    imgbtn.setBackgroundColor(Color.parseColor("#eeeeee"))
                    imgbtn.setBackgroundResource(R.drawable.shape_for_circle_button)
                    val param:GridLayout.LayoutParams=GridLayout.LayoutParams()
                    param.width=170
                    param.height=170
                    param.marginStart=20
                    imgbtn.layoutParams=param
                    scrapMainLayout.addView(imgbtn)
                }
                if(data=="요리"){
                    val imgbtn:ImageButton= ImageButton(view.context)
                    imgbtn.setImageResource(R.drawable.icon_cook)
                    imgbtn.scaleType=(ImageView.ScaleType.FIT_XY)
                    imgbtn.setBackgroundColor(Color.parseColor("#eeeeee"))
                    imgbtn.setBackgroundResource(R.drawable.shape_for_circle_button)
                    val param:GridLayout.LayoutParams=GridLayout.LayoutParams()
                    param.width=170
                    param.height=170
                    param.marginStart=20
                    imgbtn.layoutParams=param
                    scrapMainLayout.addView(imgbtn)
                }
                if(data=="댄스"){
                    val imgbtn:ImageButton= ImageButton(view.context)
                    imgbtn.setImageResource(R.drawable.icon_dance)
                    imgbtn.scaleType=(ImageView.ScaleType.FIT_XY)
                    imgbtn.setBackgroundColor(Color.parseColor("#eeeeee"))
                    imgbtn.setBackgroundResource(R.drawable.shape_for_circle_button)
                    val param:GridLayout.LayoutParams=GridLayout.LayoutParams()
                    param.width=170
                    param.height=170
                    param.marginStart=20
                    imgbtn.layoutParams=param
                    scrapMainLayout.addView(imgbtn)
                }
                if(data=="차/오토바이"){
                    val imgbtn:ImageButton= ImageButton(view.context)
                    imgbtn.setImageResource(R.drawable.icon_car)
                    imgbtn.scaleType=(ImageView.ScaleType.FIT_XY)
                    imgbtn.setBackgroundColor(Color.parseColor("#eeeeee"))
                    imgbtn.setBackgroundResource(R.drawable.shape_for_circle_button)
                    val param:GridLayout.LayoutParams=GridLayout.LayoutParams()
                    param.width=170
                    param.height=170
                    param.marginStart=20
                    imgbtn.layoutParams=param
                    scrapMainLayout.addView(imgbtn)
                }
                if(data=="반려동물"){
                    val imgbtn:ImageButton= ImageButton(view.context)
                    imgbtn.setImageResource(R.drawable.icon_pet)
                    imgbtn.scaleType=(ImageView.ScaleType.FIT_XY)
                    imgbtn.setBackgroundColor(Color.parseColor("#eeeeee"))
                    imgbtn.setBackgroundResource(R.drawable.shape_for_circle_button)
                    val param:GridLayout.LayoutParams=GridLayout.LayoutParams()
                    param.width=170
                    param.height=170
                    param.marginStart=20
                    imgbtn.layoutParams=param
                    scrapMainLayout.addView(imgbtn)
                }
                if(data=="공예"){
                    val imgbtn:ImageButton= ImageButton(view.context)
                    imgbtn.setImageResource(R.drawable.icon_art)
                    imgbtn.scaleType=(ImageView.ScaleType.FIT_XY)
                    imgbtn.setBackgroundColor(Color.parseColor("#eeeeee"))
                    imgbtn.setBackgroundResource(R.drawable.shape_for_circle_button)
                    val param:GridLayout.LayoutParams=GridLayout.LayoutParams()
                    param.width=170
                    param.height=170
                    param.marginStart=20
                    imgbtn.layoutParams=param
                    scrapMainLayout.addView(imgbtn)
                }
                if(data=="봉사활동"){
                    val imgbtn:ImageButton= ImageButton(view.context)
                    imgbtn.setImageResource(R.drawable.icon_volunteer)
                    imgbtn.scaleType=(ImageView.ScaleType.FIT_XY)
                    imgbtn.setBackgroundColor(Color.parseColor("#eeeeee"))
                    imgbtn.setBackgroundResource(R.drawable.shape_for_circle_button)
                    val param:GridLayout.LayoutParams=GridLayout.LayoutParams()
                    param.width=170
                    param.height=170
                    param.marginStart=20
                    imgbtn.layoutParams=param
                    scrapMainLayout.addView(imgbtn)
                }
                if(data=="공부/자기개발"){
                    val imgbtn:ImageButton= ImageButton(view.context)
                    imgbtn.setImageResource(R.drawable.icon_study)
                    imgbtn.scaleType=(ImageView.ScaleType.FIT_XY)
                    imgbtn.setBackgroundColor(Color.parseColor("#eeeeee"))
                    imgbtn.setBackgroundResource(R.drawable.shape_for_circle_button)
                    val param:GridLayout.LayoutParams=GridLayout.LayoutParams()
                    param.width=170
                    param.height=170
                    param.marginStart=20
                    imgbtn.layoutParams=param
                    scrapMainLayout.addView(imgbtn)
                }
            }
        }



        return view
    }
    inner class DetailViewRecyclerViewAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var clubdata:ArrayList<ClubData> = arrayListOf()

        init{
            clubdata.clear()
            for((index,data)in myhobby.withIndex()){
                db.collection("category").document(data).get().addOnSuccessListener {  document->
                    val item2=document.toObject(getclubuid::class.java)
                    for((index,data2) in item2?.myhobbylist!!.withIndex()){
                        db.collection("meeting_room").document(data2).get().addOnSuccessListener {  document1->
                            val item3=document1.toObject(ClubData::class.java)
                            if (item3 != null) {
                                clubdata.add(item3)
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
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var viewholder=(holder as CustomViewHolder).itemView

        }


        override fun getItemCount(): Int {
            return 13
        }

    }
}


