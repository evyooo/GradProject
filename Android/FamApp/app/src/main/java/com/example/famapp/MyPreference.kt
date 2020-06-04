package com.example.famapp

import android.content.Context

class MyPreference(context: Context){

    val Preference_name = "USERDATA"

    val preference = context.getSharedPreferences(Preference_name, Context.MODE_PRIVATE)


    fun getUsername(): String{
        var name =preference.getString(Preference_name, "")
        return name
    }

    fun setUsername(username: String): String{
        val editor = preference.edit()
        editor.putString(Preference_name, username)
        editor.apply()
        return ""
    }

}