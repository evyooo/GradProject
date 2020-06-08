package com.example.famapp.Calendar


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.famapp.R
import kotlinx.android.synthetic.main.fragment_calendar.*
import android.widget.TextView
import android.content.Context.LAYOUT_INFLATER_SERVICE
import androidx.core.content.ContextCompat.getSystemService
import android.widget.BaseAdapter
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import java.text.SimpleDateFormat
import java.time.LocalDateTime
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



        drawCalendar()



        calendarAdapter = CalendarAdapter(requireContext(), calendarlist)
        calendar_grid.adapter = calendarAdapter



    }

    fun drawCalendar(){

        calendarlist.clear()


        var date = "20200601"
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





        //  날짜
        val current = LocalDateTime.now()
        var totalday = 0



        when (current.dayOfMonth){
            1, 3, 5, 7, 8, 10, 12 -> totalday = 31
            4, 6, 9, 11 -> totalday = 30
            2 -> totalday = 28
        }

        for (i in 0..totalday-1){
            calendarlist.add("${i+1}")
        }





        var date1 = "20200630"

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



    }

}
