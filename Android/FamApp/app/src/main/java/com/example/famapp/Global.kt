package com.example.famapp

import android.app.Application


class Global : Application() {

    companion object{

        var memberslist = arrayListOf<Members>()
    }
}