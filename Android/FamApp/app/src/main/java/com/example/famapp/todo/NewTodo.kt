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
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.famapp.Global
import com.example.famapp.Global.Companion.basic_url
import com.example.famapp.Global.Companion.newtodo_repeat
import com.example.famapp.Global.Companion.newtodo_score
import com.example.famapp.MyPreference
import com.example.famapp.R
import kotlinx.android.synthetic.main.activity_calendar_inside.*
import kotlinx.android.synthetic.main.activity_new_todo.*
import kotlinx.android.synthetic.main.activity_setting_room.*
import org.json.JSONObject
import java.time.LocalDateTime

class NewTodo : AppCompatActivity() {

    lateinit var myPreference: MyPreference

    var duedate = "날짜 설정 안함"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_todo)


        //  바깥 터치시 키보드 내리기
        outer_layout_newtodo.setOnClickListener {
            hideKeyboard()
        }



        //  스위치 꺼져있으면 날짜 설정 못함
        var switchflag = 0

        switch_imageview_newtodo.setOnClickListener {
            if (switchflag == 0){
                date_imageview_newtodo.setOnClickListener {
                    dateDialog()
                }
                duedate_textview_imageview.setOnClickListener {
                    dateDialog()
                }
                switch_imageview_newtodo.setImageResource(R.drawable.on)
                switchflag = 1
            }
            else{
                date_imageview_newtodo.setOnClickListener(null)
                duedate_textview_imageview.setOnClickListener(null)
                switchflag = 0
                switch_imageview_newtodo.setImageResource(R.drawable.off)

            }
        }



        //  난이도 설정
        score_imageview_newtodo.setOnClickListener {
            var intent = Intent(this, NewTodo_score::class.java)
            startActivity(intent)
        }

        score_textview_newtodo.setOnClickListener {
            var intent = Intent(this, NewTodo_score::class.java)
            startActivity(intent)
        }


        //  반복
        repeat_imageview_newtodo.setOnClickListener {
            var intent = Intent(this, NewTodo_repeat::class.java)
            startActivity(intent)
        }

        repeat_textview_newtodo.setOnClickListener {
            var intent = Intent(this, NewTodo_repeat::class.java)
            startActivity(intent)
        }


        //  취소
        cancel_button_newtodo.setOnClickListener {
            finish()
        }


    }


    override fun onResume() {
        super.onResume()

        myPreference = MyPreference(this)
        var roomindex = myPreference.getRoomindex()



        var repeatlist = arrayListOf("월", "화", "수", "목", "금", "토", "일", "반복 없음")
        var scorelist = arrayListOf("상 - 3점 획득", "중 - 2점 획득", "하 - 1점 획득", "없음")

        var repeatstr = ""
        for (i in 0..newtodo_repeat.size -1){
            if (newtodo_repeat[i] == 1){
                repeatstr = repeatstr + repeatlist[i] + " "
            }
        }
        if (repeatlist.size == 0){
            repeatstr = "반복 없음"
        }

        var scoreint = 0

        when (newtodo_score){
            0 -> scoreint = 3
            1 -> scoreint = 2
            2 -> scoreint = 1
            3 -> scoreint = 0
        }


        duedate_textview_imageview.text = duedate

        score_textview_newtodo.text = "${scorelist[newtodo_score]}"
        repeat_textview_newtodo.text = repeatstr


        //  저장
        save_button_newtodo.setOnClickListener {

            var title = inputtitle_edittext_newtodo.text.toString()
            var remind = newtodo_repeat.toString()

            var inputdate = duedate

            if (inputdate == "날짜 설정 안함"){
                inputdate = ""
            }


            val myJson = JSONObject()
            val requestBody = myJson.toString()

            val login_url =
                basic_url + "put_todo?roomindex=$roomindex&title=$title&duedate=$inputdate&score=$scoreint&remind=$remind"

            val testRequest = object : StringRequest(Method.GET, login_url,
                Response.Listener { response ->

                    var json_response = JSONObject(response)
                    if (json_response["result"].toString() == "1") {

                        Toast.makeText(this, "성공적으로 등록되었습니다.", Toast.LENGTH_SHORT).show()

                        finish()
                    }

                }, Response.ErrorListener {
                    Toast.makeText(this, "서버 연결을 확인해주세요", Toast.LENGTH_SHORT).show()

                }) {
                override fun getBodyContentType(): String {
                    return "application/json; charset=utf-8"
                }

                override fun getBody(): ByteArray {
                    return requestBody.toByteArray()
                }
            }

            Volley.newRequestQueue(this).add(testRequest)

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

            var monthstr = ""
            if (month.value.toString().length < 2){
                monthstr = "0${month.value}"
            }

            duedate = "${year.value}.${monthstr}.${day.value}"
            duedate_textview_imageview.text = duedate
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
