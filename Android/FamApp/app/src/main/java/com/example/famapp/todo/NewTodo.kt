package com.example.famapp.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.famapp.R
import kotlinx.android.synthetic.main.activity_new_todo.*

class NewTodo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_todo)

        cancel_button_newtodo.setOnClickListener {
            finish()
        }



    }
}
