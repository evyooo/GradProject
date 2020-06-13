package com.example.famapp.LoginRegister

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.famapp.Global
import com.example.famapp.Global.Companion.basic_url
import com.example.famapp.MainActivity
import com.example.famapp.MyPreference
import com.example.famapp.R
import kotlinx.android.synthetic.main.activity_create_room.*
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class CreateRoom : AppCompatActivity() {

    lateinit var myPreference: MyPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_room)

        myPreference = MyPreference(this)
        val username = myPreference.getUsername()


        //  취소
        cancel_button_newroom.setOnClickListener {
            finish()
        }


        //  키보드 내리기
        outer_layout.setOnClickListener {
            hideKeyboard()
        }


        //  이미지 변경
        roompic_imageview_create.setOnClickListener {
            //  TODO ..  사진 불러오기, 저장하기 ? 흠..
        }

        //  저장
        save_button_newroom.setOnClickListener {

            val roomtitle = roomname_edittext_create.text.toString()
            val roomdes = roomdes_edittext_create.text.toString()

            //  공란 있는경우 토스트 띄우기
            if (roomtitle.isEmpty() || roomdes.isEmpty()){
                Toast.makeText(this, "방 정보를 모두 입력해주세요!", Toast.LENGTH_SHORT).show()
            }

            createRoom(roomtitle, roomdes, username)

        }
    }

    fun createRoom(roomtitle: String, roomdes: String, userid: String){

        val myJson = JSONObject()
        val requestBody = myJson.toString()

        val url = basic_url + "create_room?roomtitle=$roomtitle&roomdes=$roomdes&user1=$userid"

        val testRequest = object : StringRequest(Method.GET, url,
            Response.Listener { response ->

                var json_response = JSONObject(response)
                if(json_response["result"].toString() == "1"){
                    Toast.makeText(this, "생성 성공", Toast.LENGTH_SHORT).show()


                    var intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)




                }else{
                    Toast.makeText(this, "생성 실패", Toast.LENGTH_SHORT).show()
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




    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(roomname_edittext_create.windowToken, 0)
    }


}
