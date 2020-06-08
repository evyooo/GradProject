package com.example.famapp.Calendar

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.famapp.Global.Companion.calremind
import com.example.famapp.R

class CalendarInside_custAdpater (val context: Context, val namelist: ArrayList<String>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.layout_calendarlistview, null)

        val text = view.findViewById<TextView>(R.id.textview_callistview)
        val check = view.findViewById<ImageView>(R.id.check_imageview_clistview)
        val each = view.findViewById<ConstraintLayout>(R.id.each_conslay_clistview)

        text.text = namelist[position]

        var remindlist = arrayListOf("월요일마다", "화요일마다", "수요일마다", "목요일마다", "금요일마다", "토요일마다", "일요일마다")


        check.visibility = View.INVISIBLE

        each.setOnClickListener {

            calremind = remindlist[position]
            check.visibility = View.VISIBLE

        }

        return view
    }

    override fun getItem(position: Int): Any {

        return namelist[position]
    }

    override fun getItemId(position: Int): Long {

        return 0
    }

    override fun getCount(): Int {

        return namelist.size
    }


}