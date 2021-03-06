package com.example.famapp.Calendar


import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.famapp.R
import kotlinx.android.synthetic.main.fragment_calendar.*
import android.content.Context.LAYOUT_INFLATER_SERVICE
import androidx.core.content.ContextCompat.getSystemService
import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import android.widget.*
import androidx.core.util.rangeTo
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.famapp.Global
import com.example.famapp.Global.Companion.basic_url
import com.example.famapp.Global.Companion.calcurrentmon
import com.example.famapp.Global.Companion.calcurrentyr
import com.example.famapp.Global.Companion.caldate
import com.example.famapp.Global.Companion.calendarlist
import com.example.famapp.Global.Companion.daylist
import com.example.famapp.Global.Companion.density
import com.example.famapp.LoginRegister.Login
import com.example.famapp.MyPreference
import com.example.famapp.forCalendar
import kotlinx.android.synthetic.main.fragment_stats.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.Month
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


/**
 * A simple [Fragment] subclass.
 */
class CalendarFragment : Fragment() {

    lateinit var calendarAdapter: CalendarAdapter

    lateinit var myPreference: MyPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }

    override fun onResume() {
        super.onResume()

        //  오늘 날짜
        val current = LocalDateTime.now()
        drawCalendar(current.year.toString(), current.monthValue.toString())

        year_textview_calendar.text = current.year.toString() + "년"
        month_textview_calendar.text = current.monthValue.toString() + "월"


        //  추가 버튼
        add_imageview_calendar.setOnClickListener {

            caldate = ""
            var intent = Intent(context, CalendarInside::class.java)
            startActivity(intent)
        }


        //  날짜 변경
        dialog_imageview_calendar.setOnClickListener {

            val dialog = AlertDialog.Builder(context).create()
            val edialog : LayoutInflater = LayoutInflater.from(context)
            val mView : View = edialog.inflate(R.layout.dialog_datepicker,null)

            val year : NumberPicker = mView.findViewById(R.id.yearpicker_datepicker)
            val month : NumberPicker = mView.findViewById(R.id.monthpicker_datepicker)

            //  순환 안되게 막기
            year.wrapSelectorWheel = false

            //  editText 설정 해제
            year.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

            month.wrapSelectorWheel = false
            month.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS


            val cancel : Button = mView.findViewById(R.id.cancel_button_datepicker)
            val save : Button = mView.findViewById(R.id.save_button_datepicker)

            year.minValue = 2019
            year.maxValue = 2021

            year.setDisplayedValues(arrayOf("2019년", "2020년","2021년"))
            year.value = 2020


            month.minValue = 1
            month.maxValue = 12
            month.value = 6

            month.setDisplayedValues(arrayOf("1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"))

            dialog.setView(mView)
            dialog.create()
            dialog.show()

            var width = 269 * density
            var height = 276 * density


            dialog.getWindow().setLayout(width.toInt(), height.toInt())


            //  취소
            cancel.setOnClickListener {
                dialog.dismiss()
                dialog.cancel()
            }

            //  완료
            save.setOnClickListener {

                year_textview_calendar.text = (year.value).toString() + "년"
                month_textview_calendar.text = (month.value).toString() + "월"

                //  서버에 날짜값 넘겨주고 달력 새로그리기
                drawCalendar((year.value).toString(), (month.value).toString())

                dialog.dismiss()
                dialog.cancel()
            }
        }

    }

    fun drawCalendar(year: String, month: String){

        calendarlist.clear()
        daylist.clear()

        myPreference = MyPreference(requireContext())
        var roomindex = myPreference.getRoomindex()


        //  TODO 윤년 고려 안함
        //  한달이 총 며칠인지 설정
        var totalday = 0

        when (month.toInt()){
            1, 3, 5, 7, 8, 10, 12 -> totalday = 31
            4, 6, 9, 11 -> totalday = 30
            2 -> totalday = 28
        }


        for (i in 0..totalday - 1){
            var basket = arrayListOf<forCalendar>()
            calendarlist.add(basket)

        }


        //  1~9월까지는 01~09로 바꿔줌
        var mon = month

        if (mon.length == 1){
            mon = "0" + mon
        }

        calcurrentyr = year
        calcurrentmon = mon

        var inputdate = "${year}.${mon}"


        //  요일에 따라 앞에 여백 삽입
        var date = "${year}${mon}01"
        var dateType = "yyyyMMdd"

        val dateFormat = SimpleDateFormat(dateType)
        val nDate = dateFormat.parse(date)
        val cal = Calendar.getInstance()
        cal.time = nDate

        val dayNum = cal.get(Calendar.DAY_OF_WEEK)

        if (dayNum != 1){
            for (i in 0 .. dayNum-2){
                daylist.add("")
            }
        }



        //  요일에 따라 앞에 여백 삽입
        for (i in 0..totalday-1){
            daylist.add("${i+1}")
        }



        //  요일에 따라 뒤에 여백 삽입
        var date1 = "${year}${mon}${totalday}"

        val nDate1 = dateFormat.parse(date1)
        val cal1 = Calendar.getInstance()
        cal1.time = nDate1

        val dayNum1 = cal1.get(Calendar.DAY_OF_WEEK)

        if (dayNum1 != 7){
            for (i in 0 .. 6-dayNum1){
                daylist.add("")
            }
        }


        //  서버에서 일정데이터 가져오기
        val myJson = JSONObject()
        val requestBody = myJson.toString()

        val url = basic_url + "get_calendar?roomindex=$roomindex&date=$inputdate"

        val testRequest = object : StringRequest(Method.GET, url,
            Response.Listener { response ->

                var json_response = JSONObject(response)
                if(json_response["result"].toString() == "1"){

                    var calresult = json_response.getJSONArray("calinfo")
                    var arraysize = calresult.length()


                    for (i in 0..arraysize-1){
                        var tempwhole = calresult.getJSONObject(i)

                        var title = tempwhole.getString("title")
                        var colorchip = tempwhole.getString("color")
                        var startdate = tempwhole.getString("startdate")
                        var enddate = tempwhole.getString("enddate")
                        var remind = tempwhole.getString("remind")
                        var calendarindex = tempwhole.getString("calendarindex")


                        //  TODO 이번달-다른달 하면 에러남 (blank관련)

                        //  시작일, 종료일 모두 이번달내 일 때
                        if (startdate.substring(0, 7) == inputdate && enddate.substring(0, 7) == inputdate){

                            //  사이 날짜 모두 가져오기
                            for (i in startdate.substring(8,10).toInt()..enddate.substring(8,10).toInt() ){

                                calendarlist[i-1].add(forCalendar("$title", "$colorchip", "$startdate", "$enddate", "$calendarindex"))

                            }

                        }
                        else if (startdate.substring(0, 7) == inputdate){

                            //  startdate ~ 월말
                            for (i in startdate.substring(8,10).toInt()..totalday){
                                calendarlist[i-1].add(forCalendar("$title", "$colorchip", "$startdate", "$enddate", "$calendarindex"))
                            }

                        }
                        else{
                            //  월초 ~ enddate

                            for (i in 1..enddate.substring(8,10).toInt() ){
                                calendarlist[i-1].add(forCalendar("$title", "$colorchip", "$startdate", "$enddate", "$calendarindex"))
                            }
                        }

                    }

                }

                calendarAdapter = CalendarAdapter(requireContext(), dayNum-1, daylist, calendarlist, month)
                calendar_grid.adapter = calendarAdapter



            }, Response.ErrorListener {
                Toast.makeText(context, "서버 연결을 확인해주세요", Toast.LENGTH_SHORT).show()

            }) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray {
                return requestBody.toByteArray()
            }
        }

        Volley.newRequestQueue(requireContext()).add(testRequest)


    }




}












