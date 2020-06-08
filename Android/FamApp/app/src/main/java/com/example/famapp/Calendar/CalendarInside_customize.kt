package com.example.famapp.Calendar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.famapp.R
import kotlinx.android.synthetic.main.activity_calendar_inside_customize.*

class CalendarInside_customize : AppCompatActivity() {

    lateinit var calendarinsideCadapter: CalendarInside_custAdpater

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar_inside_customize)

        var remindlist = arrayListOf("월요일마다", "화요일마다", "수요일마다", "목요일마다", "금요일마다", "토요일마다", "일요일마다")

        calendarinsideCadapter = CalendarInside_custAdpater(this, remindlist)
        custom_listview.adapter = calendarinsideCadapter

        finish_buttom_cus.setOnClickListener {
            finish()
        }
    }
}
