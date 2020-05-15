package com.example.famapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class TodoAdapter (val context: Context?, val todolist: ArrayList<String>) : BaseAdapter(){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.layout_todolistview, null)


        val title = view.findViewById<TextView>(R.id.title_textview_todolay)

        val todo = todolist[position]
        title.text = todo

        //  add
        if (position == todolist.size - 1){
            title.text = "마지막"
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