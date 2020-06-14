package com.example.famapp.Calendar

import android.app.AlertDialog
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.TypedValue
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.NumberPicker
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.famapp.Global
import com.example.famapp.Global.Companion.basic_url
import com.example.famapp.Global.Companion.calcust
import com.example.famapp.Global.Companion.caldate
import com.example.famapp.Global.Companion.calendday
import com.example.famapp.Global.Companion.calremind
import com.example.famapp.Global.Companion.calstartday
import com.example.famapp.MyPreference
import com.example.famapp.R
import kotlinx.android.synthetic.main.activity_calendar_inside.*
import kotlinx.android.synthetic.main.activity_setting_room.*
import kotlinx.android.synthetic.main.fragment_stats.*
import org.json.JSONObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.coroutines.coroutineContext

class CalendarInside : AppCompatActivity() {

    lateinit var myPreference: MyPreference

    var colorflag = 0
    var colorchip = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar_inside)

        //  오늘 날짜
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("YYYY.MM.dd")
        val formatted = current.format(formatter)

        if (caldate == ""){
            caldate = formatted
        }

        calstartday = caldate
        calendday = caldate

        calremind = 0
        calcust.clear()

        startday_textview_cinside.text = calstartday
        endday_textview_cinside.text = calendday


        //  제목 길이에 따라 글자 사이즈 변하게
        title_edittext_cinside.addTextChangedListener(object: TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                var userinput = title_edittext_cinside.text.toString()

                if (userinput.length < 12){
                    title_edittext_cinside.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22f)
                }
                else{
                    title_edittext_cinside.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17f)
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var userinput = title_edittext_cinside.text.toString()

                if (userinput.length < 12){
                    title_edittext_cinside.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22f)
                }
                else{
                    title_edittext_cinside.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17f)
                }
            }

            override fun afterTextChanged(s: Editable?) {
                var userinput = title_edittext_cinside.text.toString()

                if (userinput.length < 12){
                    title_edittext_cinside.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22f)
                }
                else{
                    title_edittext_cinside.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17f)
                }            }
        })


        showShort()
        setDefaultColor()

        color_box_short.setOnClickListener {

            if (colorflag == 0){
                showLong()
            }
            colorflag = 1
        }

        color_box_long.setOnClickListener {

            if (colorflag == 1){
                showShort()
            }
            colorflag = 0
        }

        color1_long_cinside.setOnClickListener {
            changeBoxColor(1)
        }

        color2_long_cinside.setOnClickListener {
            changeBoxColor(2)
        }

        color3_long_cinside.setOnClickListener {
            changeBoxColor(3)
        }

        color4_long_cinside.setOnClickListener {
            changeBoxColor(4)
        }

        color5_long_cinside.setOnClickListener {
            changeBoxColor(5)

        }



        //  하루종일 스위치
        var switchflag = 0
        switch_imageview_calendar.setOnClickListener {
            if (switchflag == 0){
                calendday = calstartday
                endday_textview_cinside.text = calendday

                startday_imageview_cinside.setOnClickListener(null)
                endday_imageview_cinside.setOnClickListener(null)
                switch_imageview_calendar.setImageResource(R.drawable.on)
                switchflag = 1

            }
            else{
                startday_imageview_cinside.setOnClickListener{
                    startday()
                }
                endday_imageview_cinside.setOnClickListener{
                    enddate()
                }
                switch_imageview_calendar.setImageResource(R.drawable.off)
                switchflag = 0
                switchflag = 0
            }
        }



        //  시작일 다이얼로그
        startday_imageview_cinside.setOnClickListener {
            startday()
        }



        //  종료일 다이얼로그
        endday_imageview_cinside.setOnClickListener {
            enddate()
        }

        //  반복
        remind_imageview_cinside.setOnClickListener {
            var intent = Intent(this, CalendarInside_remind::class.java)
            startActivity(intent)
        }


        //  취소
        cancel_button_cinside.setOnClickListener {
            finish()
        }


        //  저장
        save_button_cinside.setOnClickListener {

            myPreference = MyPreference(this)
            var roomindex = myPreference.getRoomindex()

            val title = title_edittext_cinside.text.toString()
            var savecalremind = ""

            if (calremind == 5){

                for (each in calcust){
                    savecalremind += each + " "
                }
            }
            else{
                savecalremind = calremind.toString()
            }

            val memo = memo_edittext_cinside.text.toString()


            val myJson = JSONObject()
            val requestBody = myJson.toString()

            val login_url = basic_url + "save_calendar?roomindex=$roomindex&color=$colorchip&title=$title&startdate=$calstartday&enddate=$calendday&remind=$savecalremind&memo=$memo"

            val testRequest = object : StringRequest(Method.GET, login_url,
                Response.Listener { response ->

                    var json_response = JSONObject(response)
                    if(json_response["result"].toString() == "1"){

                        Toast.makeText(this, "완료되었습니다.", Toast.LENGTH_SHORT).show()
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


    override fun onResume() {
        super.onResume()

        startday_textview_cinside.text = calstartday
        endday_textview_cinside.text = calendday

        var setremind : String

        when(calremind){
            0 -> setremind = "안함"
            1 -> setremind = "매일"
            2 -> setremind = "매주"
            3 -> setremind = "매월"
            4 -> setremind = "매년"
            else -> {
                setremind = checkCustom(calcust)
            }
        }

        remind_textview_cinside.text = setremind
    }


    fun startday(){

        val dialog = AlertDialog.Builder(this).create()
        val edialog : LayoutInflater = LayoutInflater.from(this)
        val mView : View = edialog.inflate(R.layout.dialog_datepicker3,null)

        val year : NumberPicker = mView.findViewById(R.id.year_numberPicker_3)
        val month : NumberPicker = mView.findViewById(R.id.month_numberPicker_3)
        val day : NumberPicker = mView.findViewById(R.id.day_numberPicker_3)


        //  순환 안되게 막기
        year.wrapSelectorWheel = false

        //  editText 설정 해제
        year.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

        month.wrapSelectorWheel = false
        month.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS


        val cancel : Button = mView.findViewById(R.id.cancel_button_3)
        val save : Button = mView.findViewById(R.id.save_button_3)


        year.minValue = 2019
        year.maxValue = 2021

        year.setDisplayedValues(arrayOf("2019년", "2020년", "2021년"))


        month.minValue = 1
        month.maxValue = 12

        month.setDisplayedValues(arrayOf("1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"))

        day.minValue = 1
        day.maxValue = 30

        day.setDisplayedValues(arrayOf("1일", "2일", "3일", "4일", "5일", "6일", "7일", "8일", "9일", "10일", "11일", "12일", "13일", "14일", "15일", "16일", "17일", "18일", "19일", "20일"
            ,"21일", "22일", "23일", "24일", "25일", "26일", "27일", "28일", "29일", "30일", "31일"))


        year.value = 2020
        month.value = 6
        day.value = 15


        dialog.setView(mView)
        dialog.create()
        dialog.show()


        //  취소
        cancel.setOnClickListener {
            dialog.dismiss()
            dialog.cancel()
        }

        //  완료
        save.setOnClickListener {

            //  mm 형태로 맞춰주기
            var savemonth = month.value.toString()

            if (savemonth.length < 2){
                savemonth = "0" + month.value
            }

            //  dd 형태로 맞춰주기
            var saveday = day.value.toString()

            if (saveday.length < 2){
                saveday = "0" + day.value
            }


            calstartday = (year.value).toString() + "." + savemonth + "." + saveday

            startday_textview_cinside.text = calstartday

            dialog.dismiss()
            dialog.cancel()
        }

    }


    fun enddate(){


        val dialog = AlertDialog.Builder(this).create()
        val edialog : LayoutInflater = LayoutInflater.from(this)
        val mView : View = edialog.inflate(R.layout.dialog_datepicker3,null)

        val year : NumberPicker = mView.findViewById(R.id.year_numberPicker_3)
        val month : NumberPicker = mView.findViewById(R.id.month_numberPicker_3)
        val day : NumberPicker = mView.findViewById(R.id.day_numberPicker_3)


        //  순환 안되게 막기
        year.wrapSelectorWheel = false

        //  editText 설정 해제
        year.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

        month.wrapSelectorWheel = false
        month.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS


        val cancel : Button = mView.findViewById(R.id.cancel_button_3)
        val save : Button = mView.findViewById(R.id.save_button_3)


        //  TODO ..  최대 최소 설정 / 끝날짜가 시작날짜보다 앞일경우
        year.minValue = 2019
        year.maxValue = 2020

        year.setDisplayedValues(arrayOf("2019년", "2020년"))


        month.minValue = 1
        month.maxValue = 12

        month.setDisplayedValues(arrayOf("1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"))

        day.minValue = 1
        day.maxValue = 30


        dialog.setView(mView)
        dialog.create()
        dialog.show()


        //  취소
        cancel.setOnClickListener {
            dialog.dismiss()
            dialog.cancel()
        }

        //  완료
        save.setOnClickListener {

            //  mm 형태로 맞춰주기
            var savemonth = month.value.toString()

            if (savemonth.length < 2){
                savemonth = "0" + month.value
            }

            //  dd 형태로 맞춰주기
            var saveday = day.value.toString()

            if (saveday.length < 2){
                saveday = "0" + day.value
            }


            calendday = (year.value).toString() + "." + savemonth + "." + saveday

            endday_textview_cinside.text = calendday

            dialog.dismiss()
            dialog.cancel()
        }

    }



    fun checkCustom(mutableList: MutableList<String>) : String{

        var str = ""

        if (mutableList.size == 1){
            str = mutableList[0] + "요일마다"
        }
        else{
            for (each in mutableList){
                str += each + " "
            }
        }

        return str
    }


    fun showLong(){

        color_box_long.visibility = View.VISIBLE
        color1_long_cinside.visibility = View.VISIBLE
        color2_long_cinside.visibility = View.VISIBLE
        color3_long_cinside.visibility = View.VISIBLE
        color4_long_cinside.visibility = View.VISIBLE
        color5_long_cinside.visibility = View.VISIBLE

        color_box_short.visibility = View.INVISIBLE
        colorshort_imageview_cinside.visibility = View.INVISIBLE
    }

    fun showShort(){
        color_box_long.visibility = View.INVISIBLE
        color1_long_cinside.visibility = View.INVISIBLE
        color2_long_cinside.visibility = View.INVISIBLE
        color3_long_cinside.visibility = View.INVISIBLE
        color4_long_cinside.visibility = View.INVISIBLE
        color5_long_cinside.visibility = View.INVISIBLE

        color_box_short.visibility = View.VISIBLE
        colorshort_imageview_cinside.visibility = View.VISIBLE
    }

    fun changeBoxColor(i: Int){

        if (colorflag == 1){
            showShort()

            colorchip = i

            var colorlist = arrayOf(R.color.colorPrimary, R.color.profile4, R.color.profile3, R.color.profile1, R.color.box5)

            var back = ResourcesCompat.getDrawable(this.resources, R.drawable.shape_stickerboard, null) as GradientDrawable
            back.mutate() // Mutate the drawable so changes don't affect every other drawable
            back.setColor(this.getColor(colorlist[colorchip-1]))

            colorshort_imageview_cinside.setImageDrawable(back)

            colorflag = 0
        }

    }

    fun setDefaultColor(){

        var colorimglist = arrayOf(color1_long_cinside, color2_long_cinside, color3_long_cinside, color4_long_cinside, color5_long_cinside)
        var colorlist = arrayOf(R.color.colorPrimary, R.color.profile4, R.color.profile3, R.color.profile1, R.color.box5)

        for (i in 0..4){
            var back = ResourcesCompat.getDrawable(this.resources, R.drawable.shape_stickerboard, null) as GradientDrawable
            back.mutate() // Mutate the drawable so changes don't affect every other drawable
            back.setColor(this.getColor(colorlist[i]))

            colorimglist[i].setImageDrawable(back)
        }
    }
}


