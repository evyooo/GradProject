package com.example.famapp.todo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.famapp.Global.Companion.newtodo_repeat
import com.example.famapp.R

class RepeatAdapter (val context: Context?, var dialog: ArrayList<String>) : BaseAdapter(){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.layout_calendarlistview, null)

        val title = view.findViewById<TextView>(R.id.textview_callistview)
        val check = view.findViewById<ImageView>(R.id.check_imageview_clistview)

        val each = view.findViewById<ConstraintLayout>(R.id.each_conslay_clistview)

        title.text = dialog[position]
        check.visibility = View.INVISIBLE


        var flag = 0

        each.setOnClickListener {
            if (flag == 0){
                check.visibility = View.VISIBLE
                flag = 1
                newtodo_repeat[position] = 1
            }
            else{
                check.visibility = View.INVISIBLE
                flag = 0
                newtodo_repeat[position] = 0
            }

        }

        return view
    }

    override fun getItem(position: Int): Any {

        return dialog[position]
    }

    override fun getItemId(position: Int): Long {

        return 0
    }

    override fun getCount(): Int {

        return dialog.size
    }
}