package com.example.famapp.LoginRegister

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.famapp.Global.Companion.basic_url
import com.example.famapp.MainActivity
import com.example.famapp.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONObject
import java.lang.reflect.Method

class Register : AppCompatActivity() {

    var idcheck = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        //  취소
        cancel_button_register.setOnClickListener {
            finish()
        }


        //  키보드 내려가게
        outer_layout.setOnClickListener {
            hideKeyboard()
        }


        //  아이디 중복확인
        checkid_imageview_register.setOnClickListener {

            val userid = userid_edittext_register.text.toString()
            checkid(userid)
        }


        //  저장
        save_button_register.setOnClickListener {

            val name = name_edittext_register.text.toString()
            val userpw = userpw_edittext_register.text.toString()
            val confirmpw = confirmpw_edittext_register.text.toString()
            val userid = userid_edittext_register.text.toString()
            val birthday = birthday_edittext_register.text.toString()

            //  모두 입력했는지 확인 (하나라도 비어있으면)
            if (name.isEmpty() || userpw.isEmpty() || confirmpw.isEmpty() || userid.isEmpty() || birthday.isEmpty()){
                Toast.makeText(this, "모든 항목을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
            //  아이디 형식
            else if (" " in userid){
                Toast.makeText(this, "아이디에 사용할 수 없는 문자가 포함되어있습니다.", Toast.LENGTH_SHORT).show()
            }
            //  아이디 중복 확인 했는지 확인 (안했으면)
            else if (idcheck == 0){
                Toast.makeText(this, "아이디 중복 확인을 해주세요.", Toast.LENGTH_SHORT).show()
            }
            //  비밀번호 길이 체크
            else if (userpw.length < 6){
                Toast.makeText(this, "비밀번호는 6자보다 길어야합니다.", Toast.LENGTH_SHORT).show()
            }
            //  비번 == 비번확인인지 확인
            else if (userpw != confirmpw){
                Toast.makeText(this, "비밀번호와 비밀번호 확인이 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            }
            //  생일 날짜 형식 확인
            else if (birthday.length != 10 || "." !in birthday){
                Toast.makeText(this, "생일 날짜 형식이 맞지 않습니다.", Toast.LENGTH_SHORT).show()
            }
            else{
                //  회원가입 서버요청
                register(name, userid, userpw, birthday)
            }

        }

    }

    fun checkid(userid: String){

        val myJson = JSONObject()
        val requestBody = myJson.toString()

        val findid_url = basic_url + "check_id?userid=$userid"

        val testRequest = object : StringRequest(Method.GET, findid_url,
            Response.Listener { response ->

                var json_response = JSONObject(response)
                if(json_response["result"].toString() == "-1"){
                    Toast.makeText(this, "사용가능한 아이디입니다.", Toast.LENGTH_SHORT).show()

                    idcheck = 1

                }else{
                    Toast.makeText(this, "이미 누가 가입한 아이디입니다.", Toast.LENGTH_SHORT).show()
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


    fun register(name: String, userid: String, userpw: String, birthday: String){

        val myJson = JSONObject()
        val requestBody = myJson.toString()

        val findid_url = basic_url + "register?name=$name&userid=$userid&userpw=$userpw&birthday=$birthday"

        val testRequest = object : StringRequest(Method.GET, findid_url,
            Response.Listener { response ->

                var json_response = JSONObject(response)
                if(json_response["result"].toString() == "1"){
                    Toast.makeText(this, "가입 성공", Toast.LENGTH_SHORT).show()

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
        imm.hideSoftInputFromWindow(name_edittext_register.windowToken, 0)
    }


}
