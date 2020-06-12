package com.example.famapp.todo

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.NumberPicker
import androidx.lifecycle.ViewModelProvider
import com.example.famapp.Global
import com.example.famapp.R
import kotlinx.android.synthetic.main.activity_calendar_inside.*
import kotlinx.android.synthetic.main.activity_new_todo.*
import kotlinx.android.synthetic.main.activity_setting_room.*
import java.time.LocalDateTime

class NewTodo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_todo)


        //  바깥 터치시 키보드 내리기
        outer_layout_newtodo.setOnClickListener {
            hideKeyboard()
        }


        //  현재 날짜 설정
        val current = LocalDateTime.now()
        duedate_textview_imageview.text = "${current.year}.${current.monthValue}.${current.dayOfMonth}"


        //  스위치 꺼져있으면 날짜 설정 못함
        datebool_switch_newtodo.setOnCheckedChangeListener{CompoundButton, onSwitch ->

            if (onSwitch){
                date_imageview_newtodo.setOnClickListener {
                    dateDialog()
                }
            }
            else {
                date_imageview_newtodo.setOnClickListener(null)
            }
        }


        //  TODO
        score_textview_newtodo.text = ""
        repeat_textview_newtodo.text = ""



        //  날짜 다이얼로그
        date_imageview_newtodo.setOnClickListener {
            dateDialog()
        }

        //  난이도 설정
        score_imageview_newtodo.setOnClickListener {
            var intent = Intent(this, NewTodo_score::class.java)
            startActivity(intent)
        }

        //  반복
        repeat_imageview_newtodo.setOnClickListener {
            var intent = Intent(this, NewTodo_repeat::class.java)
            startActivity(intent)
        }


        //  취소
        cancel_button_newtodo.setOnClickListener {
            finish()
        }

        //  저장
        save_button_newtodo.setOnClickListener {
            var title = inputtitle_edittext_newtodo.text.toString()


            //  TODO 저장

        }



    }

    fun dateDialog(){

        val dialog = AlertDialog.Builder(this).create()
        val edialog : LayoutInflater = LayoutInflater.from(this)
        val mView : View = edialog.inflate(R.layout.dialog_datepicker3,null)

        val year : NumberPicker = mView.findViewById(R.id.year_numberPicker_3)
        val month : NumberPicker = mView.findViewById(R.id.month_numberPicker_3)
        val day : NumberPicker = mView.findViewById(R.id.day_numberPicker_3)


        //  순환 안되게 막기
        year.wrapSelectorWheel = false
        month.wrapSelectorWheel = false
        day.wrapSelectorWheel = false

        //  editText 설정 해제
        year.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        month.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        day.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS


        year.minValue = 2020
        year.maxValue = 2020
        year.setDisplayedValues(arrayOf("2020년"))

        month.minValue = 1
        month.maxValue = 12
        month.setDisplayedValues(arrayOf("1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"))

        day.minValue = 1
        day.maxValue = 31
        day.setDisplayedValues(arrayOf("1일", "2일", "3일", "4일", "5일", "6일", "7일", "8일", "9일", "10일", "11일", "12일", "13일", "14일", "15일", "16일", "17일", "18일", "19일", "20일"
            ,"21일", "22일", "23일", "24일", "25일", "26일", "27일", "28일", "29일", "30일", "31일"))


        when (month.value){
            1, 3, 5, 7, 8, 10, 12 -> {
                day.maxValue = 31
                day.setDisplayedValues(arrayOf("1일", "2일", "3일", "4일", "5일", "6일", "7일", "8일", "9일", "10일", "11일", "12일", "13일", "14일", "15일", "16일", "17일", "18일", "19일", "20일"
                    ,"21일", "22일", "23일", "24일", "25일", "26일", "27일", "28일", "29일", "30일", "31일"))
            }
            4, 6, 9, 11 -> {
                day.maxValue = 30
                day.setDisplayedValues(arrayOf("1일", "2일", "3일", "4일", "5일", "6일", "7일", "8일", "9일", "10일", "11일", "12일", "13일", "14일", "15일", "16일", "17일", "18일", "19일", "20일"
                    ,"21일", "22일", "23일", "24일", "25일", "26일", "27일", "28일", "29일", "30일"))
            }
            2 -> {
                //  TODO 윤년처리 안함
                day.maxValue = 28
                day.setDisplayedValues(arrayOf("1일", "2일", "3일", "4일", "5일", "6일", "7일", "8일", "9일", "10일", "11일", "12일", "13일", "14일", "15일", "16일", "17일", "18일", "19일", "20일"
                    ,"21일", "22일", "23일", "24일", "25일", "26일", "27일", "28일", "29일", "30일"))
            }
        }


        val cancel : Button = mView.findViewById(R.id.cancel_button_3)
        val save : Button = mView.findViewById(R.id.save_button_3)

        cancel.setOnClickListener {
            dialog.dismiss()
            dialog.cancel()
        }

        save.setOnClickListener {
            dialog.dismiss()
            dialog.cancel()

            //  TODO 서버 저장

            duedate_textview_imageview.text = "${year.value}.${month.value}.${day.value}"

        }

        dialog.setView(mView)
        dialog.create()
        dialog.show()

    }

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(inputtitle_edittext_newtodo.windowToken, 0)
    }
}
