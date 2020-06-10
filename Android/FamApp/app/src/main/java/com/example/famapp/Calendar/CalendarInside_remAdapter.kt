package com.example.famapp.Calendar

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import com.example.famapp.Global.Companion.calremind
import com.example.famapp.Members
import com.example.famapp.R

class CalendarInside_remAdapter (val context: Context, val namelist: ArrayList<String>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.layout_calendarlistview, null)

        val text = view.findViewById<TextView>(R.id.textview_callistview)
        val check = view.findViewById<ImageView>(R.id.check_imageview_clistview)
        val each = view.findViewById<ConstraintLayout>(R.id.each_conslay_clistview)

        text.text = namelist[position]

        check.visibility = View.INVISIBLE


        each.setOnClickListener {

            if (position == namelist.size -1){

                var intent = Intent(context, CalendarInside_customize::class.java)
                context.startActivity(intent)
            }
            else{
                check.visibility = View.VISIBLE
            }

            calremind = position

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