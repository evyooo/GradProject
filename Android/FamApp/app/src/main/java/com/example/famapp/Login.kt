package com.example.famapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.activity_board_write.*
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //  키보드 숨기기
        outer_layout_login.setOnClickListener {
            hideKeyboard()
        }


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

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(userid_edittext_login.windowToken, 0)
    }

}
