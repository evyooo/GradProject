package com.example.famapp

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView

class TodoAdapter (val context: Context?, val todolist: ArrayList<String>) : BaseAdapter(){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.layout_todolistview, null)


        val title = view.findViewById<TextView>(R.id.title_textview_todolay)
        val checkBox = view.findViewById<ImageView>(R.id.checkbox_todolay)

        val todo = todolist[position]
        title.text = todo

        //  add
        if (position == todolist.size - 1){
            title.text = "마지막"
        }

        //  체크박스
        var flag = 0 //  TODO flag도 db에 저장된거로 바꾸기
        checkBox.setOnClickListener {
            if (flag == 0){
                checkBox.setImageResource(R.drawable.icon_checked_small)
                title.setTextColor(context!!.getColor(R.color.colorPrimary))
                title.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG)
                flag = 1
            }
            else{
                checkBox.setImageResource(R.drawable.icon_defaultcheck_small)
                title.setTextColor(Color.BLACK)
                title.setPaintFlags(0)
                flag = 0
            }
        }


        return view
    }

    override fun getItem(position: Int): Any {

        return todolist[position]
    }

    override fun getItemId(position: Int): Long {

        return 0
    }

    override fun getCount(): Int {

        return todolist.size
    }
}