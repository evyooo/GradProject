package com.example.famapp.LoginRegister

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.famapp.R
import kotlinx.android.synthetic.main.activity_choose_room.*

class ChooseRoom : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_room)


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