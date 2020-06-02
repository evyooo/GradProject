package com.example.famapp.LoginRegister

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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

        //  이미지 변경
        roompic_imageview_create.setOnClickListener {
            //  TODO ..  사진 불러오기, 저장하기 ? 흠..
        }

        //  저장
        save_button_newroom.setOnClickListener {

            val name = roomname_edittext_create.text.toString()
            val description = roomdes_edittext_create.text.toString()

            //  공란 있는경우 토스트 띄우기
            if (name.isEmpty() || description.isEmpty()){
                Toast.makeText(this, "방 정보를 모두 입력해주세요!", Toast.LENGTH_SHORT).show()
            }

            //  TODO ..  서버 저장

            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
