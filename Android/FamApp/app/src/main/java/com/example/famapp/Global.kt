package com.example.famapp

import android.app.Application


class Global : Application() {

    companion object{

        val basic_url = "http://13.124.180.27:5000/"


        var memberslist = arrayListOf<Members>()

        //  choose room
        var roomlist = arrayListOf<RoomInfo>()

        //  calendar startday endday
        var calstartday = ""
        var calendday = ""
        var calremind = 0
        var calcust = mutableListOf<String>()

        var calindex = ""
        var caldate = ""

        var calcurrentyr = ""
        var calcurrentmon = ""

        //  일정데이터가 들어갈 배열 초기화
        var calendarlist = arrayListOf<ArrayList<forCalendar>>()

        //  날짜데이터가 들어갈 배열 초기화
        var daylist = arrayListOf<String>()

        var parentpos = 0
        var childpos = 0


        var newtodo_repeat = arrayListOf(0, 0, 0, 0, 0, 0, 0, 0)
        var newtodo_score = 3


    }
}