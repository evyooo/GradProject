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
import com.example.famapp.Global.Companion.calendday
import com.example.famapp.Global.Companion.calremind
import com.example.famapp.Global.Companion.calstartday
import com.example.famapp.R
import kotlinx.android.synthetic.main.activity_calendar_inside.*
import kotlinx.android.synthetic.main.fragment_stats.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.coroutines.coroutineContext

class CalendarInside : AppCompatActivity() {

    var colorflag = 0
    var colorchip = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar_inside)

        //  오늘 날짜
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("YYYY.MM.dd")
        val formatted = current.format(formatter)

        calstartday = formatted
        calendday = formatted

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

        //  시작일
        startday_imageview_cinside.setOnClickListener {

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


            //  TODO ..  최대 최소 설정
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

                calstartday = (year.value).toString() + "." + (month.value).toString() + "." + (day.value).toString()
                startday_textview_cinside.text = calstartday

                //  TODO ..  서버에 값 넘겨주고 불러오기

                dialog.dismiss()
                dialog.cancel()
            }
        }

        //  종료일
        endday_imageview_cinside.setOnClickListener {

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


            //  TODO ..  최대 최소 설정
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

                calendday = (year.value).toString() + "." + (month.value).toString() + "." + (day.value).toString()

                endday_textview_cinside.text = calendday

                //  TODO ..  서버에 값 넘겨주고 불러오기


                dialog.dismiss()
                dialog.cancel()
            }
        }

        //  반복
        remind_imageview_cinside.setOnClickListener {
            var intent = Intent(this, CalendarInside_remind::class.java)
            startActivity(intent)
        }

        cancel_button_cinside.setOnClickListener {
            finish()
        }

        save_button_cinside.setOnClickListener {
            finish()
        }




        //  TODO 더블탭 구현

//        val gestureDetector = GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
//            override fun onDoubleTap(e: MotionEvent?): Boolean {
//                Log.d("myApp", "double tap")
//                return true
//            }
//            override fun onLongPress(e: MotionEvent?) {
//                super.onLongPress(e)
//                Log.d("dd","on long press")
//
//            }
//        })
//        title_textview_cinside.setOnTouchListener { _, event -> gestureDetector.onTouchEvent(event) }
//

    }


    override fun onResume() {
        super.onResume()

        startday_textview_cinside.text = calstartday
        endday_textview_cinside.text = calendday


        remind_textview_cinside.text = calremind
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


