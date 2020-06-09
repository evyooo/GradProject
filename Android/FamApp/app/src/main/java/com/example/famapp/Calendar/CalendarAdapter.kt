package com.example.famapp.Calendar

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
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

        var box1 = view.findViewById<Button>(R.id.box1_button_cal)
        var box2 = view.findViewById<Button>(R.id.box2_button_cal)
        var box3 = view.findViewById<Button>(R.id.box3_button_cal)

        circle.visibility = View.INVISIBLE

        if (arrayList[position] == ""){
            box1.visibility = View.INVISIBLE
            box2.visibility = View.INVISIBLE
            box3.visibility = View.INVISIBLE

        }
        else{
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
        }


        date.text = arrayList[position]


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