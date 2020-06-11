package com.example.famapp.Calendar

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.famapp.Global
import com.example.famapp.Global.Companion.calindex
import com.example.famapp.Global.Companion.parentpos
import com.example.famapp.R
import com.example.famapp.forCalendar
import com.example.famapp.todo.NewTodo

class listviewAdapter (val context: Context?, val todolist: ArrayList<ArrayList<forCalendar>>, val parentposition: Int, var dialog: AlertDialog) : BaseAdapter(){

    val todo = todolist[parentposition]

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.layout_calfraglistview, null)


        val date = view.findViewById<TextView>(R.id.date_textview_calfrag)
        val title = view.findViewById<TextView>(R.id.title_textview_calfrag)
        val more = view.findViewById<ImageView>(R.id.more_imageview_calfrag)

        val each = view.findViewById<ConstraintLayout>(R.id.linearLayout11_each)

        each.setOnClickListener {

            calindex = todo[position].index
            parentpos = parentposition

            var intent = Intent(context, CalendarInside_saved::class.java)
            context!!.startActivity(intent)

            dialog.dismiss()
            dialog.cancel()

        }




        if (todo[position].startdate == todo[position].enddate){
            date.text = todo[position].startdate
        }
        else{
            date.text = "${todo[position].startdate} - ${todo[position].enddate}"
        }


        title.text = todo[position].title


        more.setOnClickListener {

        }


        return view
    }

    override fun getItem(position: Int): Any {

        return todo[position]
    }

    override fun getItemId(position: Int): Long {

        return 0
    }

    override fun getCount(): Int {

        return todo.size
    }
}