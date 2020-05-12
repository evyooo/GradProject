package com.example.famapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        //  회원가입
        register_textview_login.setOnClickListener {
            var intent = Intent(this, Register::class.java)
            startActivity(intent)        }

        //  TODO 서버 연결, 추후 조건걸기
        //  로그인
        login_button_login.setOnClickListener {

            //  사용자 인풋 값
            var userid = userid_edittext_login.text.toString()
            var userpw = userpw_edittext_login.text.toString()

            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }
}
