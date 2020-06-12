package com.example.famapp.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.famapp.R
import kotlinx.android.synthetic.main.activity_new_todo_repeat.*

class NewTodo_repeat : AppCompatActivity() {

    lateinit var repeatAdapter: RepeatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_todo_repeat)

        var repeatlist = arrayListOf("월요일마다", "화요일마다", "수요일마다", "목요일마다", "금요일마다", "토요일마다", "일요일마다", "반복 없음")

        repeatAdapter = RepeatAdapter(this, repeatlist)
        listview_todorepeat.adapter = repeatAdapter
    }
}
