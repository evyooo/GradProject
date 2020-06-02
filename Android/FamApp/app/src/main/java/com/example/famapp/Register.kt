package com.example.famapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register.*

class Register : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        cancel_button_register.setOnClickListener {
            finish()
        }


        var idcheck = 0

        checkid_imageview_register.setOnClickListener {

            //  TODO ..  여기 서버 연결해서 확인하기
            idcheck = 1
        }


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
            //  아이디 길이 체크
            else if (userid.length < 6){
                Toast.makeText(this, "아이디는 6자보다 길어야합니다.", Toast.LENGTH_SHORT).show()
            }
            //  TODO 아이디 형식
            else if (" " in userid){
                Toast.makeText(this, "아이디에 포함된 우앵", Toast.LENGTH_SHORT).show()
            }
            //  아이디 중복 확인 했는지 확인 (안했으면)
            else if (idcheck == 0){
                Toast.makeText(this, "아이디 중복 확인을 해주세요.", Toast.LENGTH_SHORT).show()
            }
            //  비번 == 비번확인인지 확인
            else if (userpw != confirmpw){
                Toast.makeText(this, "비밀번호와 비밀번호 확인이 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            }
            else{
                //  TODO .. 여기 서버 연결해서 디비에 저장하기

                var intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }



        }





    }
}
