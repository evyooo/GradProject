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
import com.example.famapp.Global.Companion.basic_url
import com.example.famapp.MainActivity
import com.example.famapp.MyPreference
import com.example.famapp.R
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class Login : AppCompatActivity() {

    lateinit var myPreference: MyPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        myPreference = MyPreference(this)
        if (myPreference.getRoomindex() != ""){

            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }



        //  키보드 숨기기
        outer_layout_login.setOnClickListener {
            hideKeyboard()
        }

        //  회원가입
        register_textview_login.setOnClickListener {
            var intent = Intent(this, Register::class.java)
            startActivity(intent)
        }

        //  로그인
        login_button_login.setOnClickListener {

            //  사용자 인풋 값
            var userid = userid_edittext_login.text.toString()
            var userpw = userpw_edittext_login.text.toString()

            login(userid, userpw)
        }

    }

    fun bring_index(userid: String){

        val myJson = JSONObject()
        val requestBody = myJson.toString()

        val login_url = basic_url + "bring_roomindex?userid=$userid"

        val testRequest = object : StringRequest(Method.GET, login_url,
            Response.Listener { response ->

                var json_response = JSONObject(response)
                if(json_response["result"].toString() == "1"){
                    Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()

                    myPreference = MyPreference(this)
                    myPreference.setUsername(userid)

                    if (json_response["index"].toString() != "null"){

                        myPreference.setRoomindex(json_response["index"].toString())

                        var intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)

                    }
                    else{
                        var intent = Intent(this, CreateOrJoin::class.java)
                        startActivity(intent)
                    }


                }else{
                    Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
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


    fun login(userid: String, userpw: String){

        val myJson = JSONObject()
        val requestBody = myJson.toString()

        val login_url = basic_url + "login?userid=$userid&userpw=$userpw"

        val testRequest = object : StringRequest(Method.GET, login_url,
            Response.Listener { response ->

                var json_response = JSONObject(response)
                if(json_response["result"].toString() == "1"){
                    Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()

                    myPreference = MyPreference(this)
                    myPreference.setUsername(userid)

                    bring_index(userid)

                }else{
                    Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
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
        imm.hideSoftInputFromWindow(userid_edittext_login.windowToken, 0)
    }

}
