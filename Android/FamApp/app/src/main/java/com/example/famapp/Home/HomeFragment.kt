package com.example.famapp.Home


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.famapp.*
import com.example.famapp.Calendar.CalendarFragment
import com.example.famapp.Global.Companion.basic_url
import com.example.famapp.Global.Companion.memberslist
import com.example.famapp.settings.Settings
import kotlinx.android.synthetic.main.fragment_home.*
import org.json.JSONObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class HomeFragment : Fragment() {

    lateinit var calendarFragment: CalendarFragment
    lateinit var homeMembersAdapter: HomeMembersAdapter

    lateinit var myPreference: MyPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onStart() {
        super.onStart()

        myPreference = MyPreference(requireContext())
        val currentroom = myPreference.getRoomindex()

        bringData(currentroom)


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


                //  TODO 에러 고치기 (addbutton보고 고치기)
//                if (memberslist.size != 0){
//
//
//                    homeMembersAdapter = HomeMembersAdapter()
//                    members_recyclerView_home.adapter = homeMembersAdapter
//                }
            }
        }


        //  멤버 리사이클러뷰
        members_recyclerView_home.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)


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


        //  일정 가져오기
        bringCal()


        //  이동
        calendar_move.setOnClickListener {
            calendarFragment = CalendarFragment()
            fragmentManager?.beginTransaction()
                ?.replace(R.id.framelayout, calendarFragment)
                ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                ?.commit()
        }

    }

    fun bringData(index: String){

        memberslist.clear()

        val myJson = JSONObject()
        val requestBody = myJson.toString()

        val login_url = basic_url + "get_roominfo?roomindex=$index"

        val testRequest = object : StringRequest(Method.GET, login_url,
            Response.Listener { response ->

                var json_response = JSONObject(response)

                if(json_response["result"].toString() == "1"){

                    famname_textview_main.text = json_response["title"].toString()
                    motto_textview_main.text = json_response["des"].toString()
                    membercount_textview_main.text = json_response["membercount"].toString()

                    var members = json_response["users"].toString().split(",").toTypedArray()

                    //  멤버 불러오기 (마지막 여백 제외)
                    for (i in 0..members.size - 1){

                        if (i != members.size-1){
                            memberslist.add(Members(members[i], i))
                        }

                    }

                    homeMembersAdapter = HomeMembersAdapter()
                    members_recyclerView_home.adapter = homeMembersAdapter

                }

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

        Volley.newRequestQueue(context).add(testRequest)


    }

    fun bringCal(){



        thisweek_textview_home.text = ""

    }




}
