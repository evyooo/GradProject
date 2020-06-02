package com.example.famapp.Board

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import com.example.famapp.R
import kotlinx.android.synthetic.main.activity_board_write.*

class Board_write : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_write)

        //  키보드 내려가게
        outer_layout.setOnClickListener {
            hideKeyboard()
        }


        //  editText 글자 수 세기
        userinput_edittext_boardwrite.addTextChangedListener(object: TextWatcher{

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                wordcount_textview_boardwrite.text = "0 / 100"
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var userinput = userinput_edittext_boardwrite.text.toString()
                wordcount_textview_boardwrite.text = userinput.length.toString() + " / 100"
            }

            override fun afterTextChanged(s: Editable?) {
                var userinput = userinput_edittext_boardwrite.text.toString()
                wordcount_textview_boardwrite.text = userinput.length.toString() + " / 100"
            }
        })

        //  고정
        var fixflag = 0
        fix_imageview_boardwrite.setOnClickListener {
            if (fixflag == 0){
                fix_imageview_boardwrite.setImageResource(R.drawable.icon_fixed)
                fixflag = 1
            }
            else{
                fix_imageview_boardwrite.setImageResource(R.drawable.icon_fixed_default)
                fixflag = 0
            }
        }


        //  취소 버튼
        cancel_button_boardwrite.setOnClickListener {
            finish()
        }

        //  저장 버튼
        save_button_boardwrite.setOnClickListener {
            //  TODO
        }

    }

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(userinput_edittext_boardwrite.windowToken, 0)
    }




}
