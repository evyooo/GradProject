package com.example.famapp.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.famapp.R
import kotlinx.android.synthetic.main.activity_new_todo_score.*

class NewTodo_score : AppCompatActivity() {

    lateinit var scoreAdapter: ScoreAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_todo_score)

        var scorelist = arrayListOf("상 - 3점 획득", "중 - 2점 획득", "하 - 1점 획득", "없음")

        scoreAdapter = ScoreAdapter(this, scorelist)
        listview_todoscore.adapter = scoreAdapter
    }
}
