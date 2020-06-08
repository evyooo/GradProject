package com.example.famapp.Calendar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.famapp.R
import kotlinx.android.synthetic.main.activity_calendar_inside_remind.*

class CalendarInside_remind : AppCompatActivity() {

    lateinit var calendarinsideCadapter: CalendarInside_remAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar_inside_remind)

        var remindlist = arrayListOf("안함", "매일", "매주", "매월", "매년", "사용자화")
        calendarinsideCadapter = CalendarInside_remAdapter(this, remindlist)
        listview_remind.adapter = calendarinsideCadapter

        exit_imageview_cinside.setOnClickListener {
            finish()
        }
    }
}
