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



    }
}