package com.example.famapp


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.example.famapp.settings.Settings
import kotlinx.android.synthetic.main.fragment_home.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class HomeFragment : Fragment() {

    lateinit var calendarFragment: CalendarFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onStart() {
        super.onStart()

        //  설정
        setting_imageview_home.setOnClickListener {
            var intent = Intent(context, Settings::class.java)
            startActivity(intent)
        }

        //  초대
        invite_imageview_home.setOnClickListener {
            var intent = Intent(context, Invite::class.java)
            startActivity(intent)
        }


        //  멤버 구성 (처음엔 접어두기)
        var flag = 1
        members_conslay_main.visibility = View.GONE

        //  접었다 펴기
        showmembers_imageview_main.setOnClickListener {
            if (flag == 0){
                flag = 1
                members_conslay_main.visibility = View.GONE
            }
            else{
                flag = 0
                members_conslay_main.visibility = View.VISIBLE
            }
        }



        //  날짜
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("M월 d일")
        val formatted = current.format(formatter)

        //  요일
        val rawdayOfWeek = DateTimeFormatter.ofPattern("E")
        val formatdayOfWeek = current.format(rawdayOfWeek)
        var dayOfWeek = "월"

        when (formatdayOfWeek){
            "Mon" -> dayOfWeek = "월"
            "Tue" -> dayOfWeek = "화"
            "Wed" -> dayOfWeek = "수"
            "Thu" -> dayOfWeek = "목"
            "Fri" -> dayOfWeek = "금"
            "Sat" -> dayOfWeek = "토"
            "Sun" -> dayOfWeek = "일"
        }

        //  오늘의 날짜 설정 [m월 d일 (금)]형태
        todaydate_textview_main.text = "$formatted ($dayOfWeek)"




        //  이동
        calendar_move.setOnClickListener {
            calendarFragment = CalendarFragment()
            fragmentManager?.beginTransaction()
                ?.replace(R.id.framelayout, calendarFragment)
                ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                ?.commit()
        }

    }


}
