package com.example.famapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_setting_myaccount.*

class Setting_myaccount : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_myaccount)

        //  취소
        cancel_button_settingacc.setOnClickListener {
            finish()
        }
    }
}
