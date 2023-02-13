package com.example.capstone.data
import java.util.HashMap

data class ClubData(var category:String?=null,
                      var title:String?=null,
                      var info_text:String?=null,
                      var chatting_id_list:ArrayList<String>?=null,
                      var member_list:ArrayList<String>?=null,
                      var max:Int?=null,
                      var posting_id_list:ArrayList<String>?=null,
                      var timestamp: Long? = null,
                      var writer_uid:String?=null,
                      var imageUrl: String? = null
                        )


