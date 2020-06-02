package com.example.famapp.LoginRegister

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.famapp.MainActivity
import com.example.famapp.R
import kotlinx.android.synthetic.main.activity_join_room.*

class JoinRoom : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_room)

        //  취소
        cancel_button_joinroom.setOnClickListener {
            finish()
        }

        //  저장
        save_button_joinroom.setOnClickListener {
            val inputcode = inputcode_edittext_join.text.toString()

            //  TODO ..  서버 여기

            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
