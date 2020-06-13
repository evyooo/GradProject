package com.example.famapp.LoginRegister

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.famapp.*
import com.example.famapp.Global.Companion.basic_url
import com.example.famapp.Global.Companion.roomlist
import kotlinx.android.synthetic.main.activity_choose_room.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.json.JSONObject

class ChooseRoom : AppCompatActivity() {

    lateinit var myPreference: MyPreference

    lateinit var chooseRoomAdapter: ChooseRoomAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_room)

        myPreference = MyPreference(this)
        val username = myPreference.getUsername()

        bringRooms(username)

        textView33.setOnClickListener {

            var intent = Intent(this, CreateOrJoin::class.java)
            startActivity(intent)
        }

    }


    fun bringRooms(userid: String){

        val myJson = JSONObject()
        val requestBody = myJson.toString()

        val login_url = basic_url + "get_rooms?userid=$userid"

        val testRequest = object : StringRequest(Method.GET, login_url,
            Response.Listener { response ->

                var json_response = JSONObject(response)

                if(json_response["result"].toString() == "1"){

                    var roominfo = json_response.getJSONArray("roominfo")
                    var arraysize = roominfo.length()

                    for (i in (0 .. arraysize-1)) {
                        var obj = roominfo.getJSONObject(i)

                        var tempindex = obj.getInt("index")
                        var temptitle = obj.getString("title")


                        var temp = RoomInfo(
                            "$temptitle",
                            "$tempindex"
                        )

                        roomlist.add(temp)

                    }

                    }


                chooseRoomAdapter = ChooseRoomAdapter(this, roomlist)
                room_listview.adapter = chooseRoomAdapter


            }, Response.ErrorListener {
                Toast.makeText(this, "서버 연결을 확인해주세요", Toast.LENGTH_SHORT).show()

            }) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray {
                return requestBody.toByteArray()
            }
        }

        Volley.newRequestQueue(this).add(testRequest)




    }


}
