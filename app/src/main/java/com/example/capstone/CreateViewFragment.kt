package com.example.capstone

import android.content.Intent
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_create.view.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.HashMap

class CreateViewFragment: Fragment() {
    lateinit var storage: FirebaseStorage
    lateinit var db: FirebaseFirestore
    var photoUri: Uri?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view= LayoutInflater.from(activity).inflate(R.layout.fragment_create,container,false)
        var mActivity = activity as CreateActivity
        view.changehobby.setOnClickListener{
            mActivity.changeFragment(1,"")
        }

        if(arguments?.getString("hobby")=="운동"){
            view.changehobby.setImageResource(R.drawable.icon_sports)
        }
        if(arguments?.getString("hobby")=="여행"){
            view.changehobby.setImageResource(R.drawable.icon_trip)
        }
        if(arguments?.getString("hobby")=="음악"){
            view.changehobby.setImageResource(R.drawable.icon_music)
        }
        if(arguments?.getString("hobby")=="사교"){
            view.changehobby.setImageResource(R.drawable.icon_job)
        }
        if(arguments?.getString("hobby")=="독서"){
            view.changehobby.setImageResource(R.drawable.icon_read)
        }
        if(arguments?.getString("hobby")=="요리"){
            view.changehobby.setImageResource(R.drawable.icon_cook)
        }
        if(arguments?.getString("hobby")=="사진"){
            view.changehobby.setImageResource(R.drawable.icon_photo)
        }
        if(arguments?.getString("hobby")=="게임"){
            view.changehobby.setImageResource(R.drawable.icon_game)
        }
        if(arguments?.getString("hobby")=="댄스"){
            view.changehobby.setImageResource(R.drawable.icon_dance)
        }
        if(arguments?.getString("hobby")=="자동차"){
            view.changehobby.setImageResource(R.drawable.icon_car)
        }
        if(arguments?.getString("hobby")=="애완동물"){
            view.changehobby.setImageResource(R.drawable.icon_pet)
        }
        if(arguments?.getString("hobby")=="공예"){
            view.changehobby.setImageResource(R.drawable.icon_art)
        }
        if(arguments?.getString("hobby")=="봉사활동"){
            view.changehobby.setImageResource(R.drawable.icon_volunteer)
        }
        if(arguments?.getString("hobby")=="스터디그룹"){
            view.changehobby.setImageResource(R.drawable.icon_study)
        }

        val filterActivityLauncher: ActivityResultLauncher<Intent> =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if(it.resultCode == AppCompatActivity.RESULT_OK && it.data !=null) {
                    photoUri = it.data?.data
                    try {
                        photoUri?.let {
                            if(Build.VERSION.SDK_INT < 28) {
                                val bitmap = MediaStore.Images.Media.getBitmap(
                                    mActivity.contentResolver,
                                    photoUri
                                )
                                view.clubprofile.setImageBitmap(bitmap)
                            } else {
                                val source = ImageDecoder.createSource(mActivity.contentResolver, photoUri!!)
                                val bitmap = ImageDecoder.decodeBitmap(source)
                                view.clubprofile.setImageBitmap(bitmap)
                            }
                        }


                    }catch(e:Exception) {
                        e.printStackTrace()
                    }
                } else if(it.resultCode == AppCompatActivity.RESULT_CANCELED){
                    Toast.makeText(mActivity, "사진 선택 취소", Toast.LENGTH_LONG).show();
                }else{
                    Log.d("ActivityResult","something wrong")
                }
            }

        view.clubprofile.setOnClickListener{
            val intent=Intent(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*")
            filterActivityLauncher.launch(intent)
        }
        view.CreateBtn.setOnClickListener{
            uploadContent(arguments?.getString("hobby").toString())
        }
        return view
    }
        fun uploadContent(hobby:String){
            val clubinfo:MutableList<String>=HashMap()
            db.collection("카테고리").document(hobby).
        }
}