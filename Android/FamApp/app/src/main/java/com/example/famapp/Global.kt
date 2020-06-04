package com.example.famapp

import android.app.Application


class Global : Application() {

    companion object{

        val basic_url = "http://13.124.180.27:5000/"


        var tempnum = 0


        var memberslist = arrayListOf<Members>()

    }
}