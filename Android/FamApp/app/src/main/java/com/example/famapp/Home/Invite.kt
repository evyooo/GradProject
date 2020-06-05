package com.example.famapp.Home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.famapp.Global
import com.example.famapp.Global.Companion.basic_url
import com.example.famapp.MyPreference
import com.example.famapp.R
import kotlinx.android.synthetic.main.activity_invite.*
import org.json.JSONObject

class Invite : AppCompatActivity() {

    lateinit var myPreference: MyPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invite)

        myPreference = MyPreference(this)
        val roomindex = myPreference.getRoomindex()

        bringCode(roomindex)


    }

    fun bringCode(roomindex: String){


        val myJson = JSONObject()
        val requestBody = myJson.toString()

        val login_url = basic_url + "bring_randomcode?roomindex=$roomindex"

        val testRequest = object : StringRequest(Method.GET, login_url,
            Response.Listener { response ->

                var json_response = JSONObject(response)
                if(json_response["result"].toString() == "1"){

                    //  랜덤 코드
                    randomcode_textview_invite.text = json_response["randomstring"].toString()

                }

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
