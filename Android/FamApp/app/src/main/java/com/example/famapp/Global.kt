package com.example.famapp

import android.app.Application


class Global : Application() {

    companion object{

        var memberslist = arrayListOf<Members>()

        val basic_url = "http://13.124.180.27:5000/"



    }
}