package com.example.famapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //  접었다 펴기
        var flag = 0

        membercount_textview_main.setOnClickListener {
            if (flag == 0){
                flag = 1
                members_conslay_main.visibility = View.GONE
            }
            else{
                flag = 0
                members_conslay_main.visibility = View.VISIBLE
            }
        }

    }
}
