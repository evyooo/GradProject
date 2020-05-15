package com.example.famapp.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.famapp.R
import kotlinx.android.synthetic.main.activity_setting_room.*

class Setting_room : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_room)

        //  취소
        cancel_button_setroom.setOnClickListener {
            finish()
        }
    }
}
