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
import kotlinx.android.synthetic.main.activity_invite.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_setting_myaccount.*
import org.json.JSONObject

class Setting_myaccount : AppCompatActivity() {

    lateinit var myPreference: MyPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_myaccount)

        myPreference = MyPreference(this)
        val username = myPreference.getUsername()

        userid_textview_mysetting.text = username


        //  기존 정보 불러오기
        bringdata(username)


        //  외부 터치시 키보드 내려가게
        outerlayout_settingacc.setOnClickListener {
            hideKeyboard()
        }


        //  취소
        cancel_button_settingacc.setOnClickListener {
            finish()
        }


        //  저장
        save_button_settingacc.setOnClickListener {

            var name = name_edittext_accset.text.toString()
            var userpw = userpw_edittext_accset.text.toString()
            var confirmpw = confirmpw_edittext_accset.text.toString()
            var birthday = birthday_edittext_accset.text.toString()


            if (name.isEmpty() || userpw.isEmpty() || confirmpw.isEmpty() || birthday.isEmpty()){
                Toast.makeText(this, "모든 항목을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }

            else if (userpw != confirmpw){
                Toast.makeText(this, "비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
            }

            else{
                updateData(username, name, userpw, birthday)
            }

        }
    }

    fun bringdata(userid: String){


        val myJson = JSONObject()
        val requestBody = myJson.toString()

        val login_url = basic_url + "get_userinfo?userid=$userid"

        val testRequest = object : StringRequest(Method.GET, login_url,
            Response.Listener { response ->

                var json_response = JSONObject(response)
                if(json_response["result"].toString() == "1"){

                    name_edittext_accset.setText(json_response["name"].toString())
                    birthday_edittext_accset.setText(json_response["birthday"].toString())

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



    fun updateData(userid: String, name: String, userpw: String, birthday: String){

        val myJson = JSONObject()
        val requestBody = myJson.toString()

        val login_url = basic_url + "update_userinfo?userid=$userid&name=$name&userpw=$userpw&birthday=$birthday"

        val testRequest = object : StringRequest(Method.GET, login_url,
            Response.Listener { response ->

                var json_response = JSONObject(response)
                if(json_response["result"].toString() == "1"){

                    Toast.makeText(this, "성공적으로 변경되었습니다.", Toast.LENGTH_SHORT).show()

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
        imm.hideSoftInputFromWindow(name_edittext_accset.windowToken, 0)
    }


}
