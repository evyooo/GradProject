package com.example.famapp.settings

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.famapp.Global
import com.example.famapp.Global.Companion.basic_url
import com.example.famapp.MyPreference
import com.example.famapp.R
import kotlinx.android.synthetic.main.activity_setting_myaccount.*
import kotlinx.android.synthetic.main.activity_setting_room.*
import org.json.JSONObject

class Setting_room : AppCompatActivity() {

    lateinit var myPreference: MyPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_room)

        myPreference = MyPreference(this)
        val roomindex = myPreference.getRoomindex()

        bringData(roomindex)


        //  키보드 내려가게
        outer_layout.setOnClickListener {
            hideKeyboard()
        }


        //  취소
        cancel_button_setroom.setOnClickListener {
            finish()
        }


        //  저장
        save_button_setroom.setOnClickListener {

            val title = roomname_edittext_setroom.text.toString()
            val des = roomdes_edittext_setroom.text.toString()

            if (title.isEmpty() || des.isEmpty()){
                Toast.makeText(this, "정보를 모두 입력해주세요.", Toast.LENGTH_SHORT).show()

            }
            else{
                saveData(roomindex, title, des)
            }

        }



    }

    fun bringData(roomindex: String){

        val myJson = JSONObject()
        val requestBody = myJson.toString()

        val login_url = basic_url + "get_roominfo?roomindex=$roomindex"

        val testRequest = object : StringRequest(Method.GET, login_url,
            Response.Listener { response ->

                var json_response = JSONObject(response)
                if(json_response["result"].toString() == "1"){

                    roomname_edittext_setroom.setText(json_response["title"].toString())
                    roomdes_edittext_setroom.setText(json_response["des"].toString())

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

    fun saveData(roomindex: String, title: String, des: String){

        val myJson = JSONObject()
        val requestBody = myJson.toString()

        val login_url = basic_url + "update_roominfo?roomindex=$roomindex&title=$title&des=$des"

        val testRequest = object : StringRequest(Method.GET, login_url,
            Response.Listener { response ->

                var json_response = JSONObject(response)
                if(json_response["result"].toString() == "1"){

                    Toast.makeText(this, "성공적으로 변경되었습니다. ", Toast.LENGTH_SHORT).show()
                    finish()

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
        imm.hideSoftInputFromWindow(roomdes_edittext_setroom.windowToken, 0)
    }
}
