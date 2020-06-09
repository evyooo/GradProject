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
import kotlinx.android.synthetic.main.fragment_stats.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.Month
import java.time.format.DateTimeFormatter
import java.util.*




/**
 * A simple [Fragment] subclass.
 */
class CalendarFragment : Fragment() {

    lateinit var calendarAdapter: CalendarAdapter

    var calendarlist = arrayListOf("")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }

    override fun onResume() {
        super.onResume()

        add_imageview_calendar.setOnClickListener {
            var intent = Intent(context, CalendarInside::class.java)
            startActivity(intent)
        }

        //  TODO


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


            //  TODO ..  최대 최소 설정
            year.minValue = 2019
            year.maxValue = 2020

            year.setDisplayedValues(arrayOf("2019년", "2020년"))


            month.minValue = 1
            month.maxValue = 12

            month.setDisplayedValues(arrayOf("1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"))


            dialog.setView(mView)
            dialog.create()
            dialog.show()


//            var window = dialog.window
//            window.setLayout(238 * , WindowManager.LayoutParams.WRAP_CONTENT)


            //  취소
            cancel.setOnClickListener {
                dialog.dismiss()
                dialog.cancel()
            }

            //  완료
            save.setOnClickListener {

                year_textview_calendar.text = (year.value).toString() + "년"
                month_textview_calendar.text = (month.value).toString() + "월"


                //  TODO ..  서버에 값 넘겨주고 불러오기

                drawCalendar((year.value).toString(), (month.value).toString())
                Log.d("", "${year.value} ${month.value}")


                dialog.dismiss()
                dialog.cancel()
            }
        }


        //  날짜
        val current = LocalDateTime.now()
        drawCalendar(current.year.toString(), current.monthValue.toString())
        Log.d("1", "${current.year}")

        year_textview_calendar.text = current.year.toString() + "년"
        month_textview_calendar.text = current.monthValue.toString() + "월"







    }

    fun drawCalendar(year: String, month: String){

        calendarlist.clear()

        Log.d("", "$year $month")

        var mon = month

        if (mon.length == 1){
            mon = "0" + mon
        }


        var date = "${year}${mon}01"
        var dateType = "yyyyMMdd"

        val dateFormat = SimpleDateFormat(dateType)
        val nDate = dateFormat.parse(date)

        val cal = Calendar.getInstance()
        cal.time = nDate

        val dayNum = cal.get(Calendar.DAY_OF_WEEK)


        //  요일에 따라 앞에 여백 삽입
        if (dayNum != 1){
            for (i in 0 .. dayNum-2){
                calendarlist.add("")
            }
        }






        var totalday = 0



        when (month.toInt()){
            1, 3, 5, 7, 8, 10, 12 -> totalday = 31
            4, 6, 9, 11 -> totalday = 30
            2 -> totalday = 28
        }

        for (i in 0..totalday-1){
            calendarlist.add("${i+1}")
        }



        var date1 = "${year}${mon}${totalday}"

        val nDate1 = dateFormat.parse(date1)

        val cal1 = Calendar.getInstance()
        cal1.time = nDate1

        val dayNum1 = cal1.get(Calendar.DAY_OF_WEEK)
        

        //  요일에 따라 뒤에 여백 삽입
        if (dayNum1 != 7){
            for (i in 0 .. 6-dayNum1){
                calendarlist.add("")
            }
        }



        calendarAdapter = CalendarAdapter(requireContext(), calendarlist)
        calendar_grid.adapter = calendarAdapter



    }

}
