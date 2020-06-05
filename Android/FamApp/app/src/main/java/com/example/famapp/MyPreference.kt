package com.example.famapp

import android.content.Context

class MyPreference(context: Context){

    val Preference_name = "USERDATA"
    val Preference_room = ""

    val preference = context.getSharedPreferences(Preference_name, Context.MODE_PRIVATE)
    val preferenceroom = context.getSharedPreferences(Preference_name, Context.MODE_PRIVATE)


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

    fun getRoomindex(): String{
        var index =preferenceroom.getString(Preference_room, "")
        return index
    }

    fun setRoomindex(username: String): String{
        val editor = preferenceroom.edit()
        editor.putString(Preference_room, username)
        editor.apply()
        return ""
    }






}