package com.example.famapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_settings.*

class Settings : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)


        //  내 계정 설정
        myaccount_imageview_settings.setOnClickListener {
            var intent = Intent(this, Setting_myaccount::class.java)
            startActivity(intent)
        }

        //  방 설정
        room_imageview_settings.setOnClickListener {
            var intent = Intent(this, Setting_room::class.java)
            startActivity(intent)
        }

        //  멤버 설정
        members_imageview_settings.setOnClickListener {
            var intent = Intent(this, Setting_members::class.java)
            startActivity(intent)
        }

        //  이 방에서 나가기
        leave_imageview_settings.setOnClickListener {

        }

        //  다른 방 이동
        switch_imageview_settings.setOnClickListener {
            var intent = Intent(this, Setting_myaccount::class.java)
            startActivity(intent)
        }
    }
}
