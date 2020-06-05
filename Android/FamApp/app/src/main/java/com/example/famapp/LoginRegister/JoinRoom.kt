package com.example.famapp.LoginRegister

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.famapp.Global
import com.example.famapp.Global.Companion.basic_url
import com.example.famapp.MainActivity
import com.example.famapp.MyPreference
import com.example.famapp.R
import kotlinx.android.synthetic.main.activity_invite.*
import kotlinx.android.synthetic.main.activity_join_room.*
import org.json.JSONObject

class JoinRoom : AppCompatActivity() {

    lateinit var myPreference: MyPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_room)

        //  취소
        cancel_button_joinroom.setOnClickListener {
            finish()
        }

        //  저장
        save_button_joinroom.setOnClickListener {


            myPreference = MyPreference(this)
            val userid = myPreference.getUsername()

            val inputcode = inputcode_edittext_join.text.toString()

            join(inputcode, userid)


        }
    }


    fun join(inputcode: String, userid: String){

        val myJson = JSONObject()
        val requestBody = myJson.toString()

        val login_url = basic_url + "join_room?randomstring=$inputcode&userid=$userid"

        val testRequest = object : StringRequest(Method.GET, login_url,
            Response.Listener { response ->

                var json_response = JSONObject(response)
                if(json_response["result"].toString() == "1"){

                    myPreference.setRoomindex(json_response["roomindex"].toString())

                    var intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)


                }
                else if (json_response["result"].toString() == "2") {
                    Toast.makeText(this, "정원이 다 찬 방입니다.", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this, "존재하지 않는 초대 코드입니다.", Toast.LENGTH_SHORT).show()
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
