package com.example.famapp.LoginRegister

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.famapp.MainActivity
import com.example.famapp.MyPreference
import com.example.famapp.R
import kotlinx.android.synthetic.main.activity_createorjoin.*

class CreateOrJoin : AppCompatActivity() {

    lateinit var myPreference: MyPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_createorjoin)

        myPreference = MyPreference(this)
        val username = myPreference.getUsername()
        textView27.text = "가족들이 ${username}님을 기다리고 있어요"


        //  새로운 방 만들기
        newroom_conslay_choose.setOnClickListener {
            var intent = Intent(this, CreateRoom::class.java)
            startActivity(intent)
        }

        //  기존 방
        existroom_conslay_choose.setOnClickListener {
            var intent = Intent(this, JoinRoom::class.java)
            startActivity(intent)
        }
    }
}
