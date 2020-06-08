package com.example.famapp.Calendar

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.famapp.R

class CalendarAdapter(var context: Context, var arrayList: ArrayList<String>): BaseAdapter(){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var view = View.inflate(context, R.layout.layout_gridviewcalendar, null)

        var date = view.findViewById<TextView>(R.id.date)
        var circle = view.findViewById<ImageView>(R.id.selected_circle)
        var each = view.findViewById<ConstraintLayout>(R.id.each_conslay)

        circle.visibility = View.INVISIBLE


        date.text = arrayList[position]


        each.setOnClickListener {
            //  todo
//            circle.visibility = View.VISIBLE

            val dialog = AlertDialog.Builder(context)
            val edialog : LayoutInflater = LayoutInflater.from(context)
            val mView : View = edialog.inflate(R.layout.layout_dialog_calendar,null)

            val date : TextView = mView.findViewById(R.id.textView50)

            date.text = position.toString()

            dialog.setView(mView)
            dialog.create()
            dialog.show()

        }


        return view

    }

    override fun getItem(position: Int): Any {
        return arrayList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return arrayList.size
    }


}