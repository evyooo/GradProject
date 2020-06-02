package com.example.famapp.LoginRegister

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.famapp.MainActivity
import com.example.famapp.R
import kotlinx.android.synthetic.main.activity_create_room.*

class CreateRoom : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_room)

        //  취소
        cancel_button_newroom.setOnClickListener {
            finish()
        }

        //  저장
        save_button_newroom.setOnClickListener {

            //  TODO ..  서버

            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
