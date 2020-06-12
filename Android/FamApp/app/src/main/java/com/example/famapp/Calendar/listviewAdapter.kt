package com.example.famapp.Calendar

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import com.example.famapp.Global
import com.example.famapp.Global.Companion.calindex
import com.example.famapp.Global.Companion.childpos
import com.example.famapp.Global.Companion.parentpos
import com.example.famapp.R
import com.example.famapp.forCalendar
import com.example.famapp.todo.NewTodo
import kotlinx.android.synthetic.main.activity_calendar_inside.*

class listviewAdapter (val context: Context?, val todolist: ArrayList<ArrayList<forCalendar>>, val parentposition: Int, var dialog: AlertDialog) : BaseAdapter(){

    val todo = todolist[parentposition]

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.layout_calfraglistview, null)


        val tag = view.findViewById<ImageView>(R.id.imageView19)
        val date = view.findViewById<TextView>(R.id.date_textview_calfrag)
        val title = view.findViewById<TextView>(R.id.title_textview_calfrag)
        val more = view.findViewById<ImageView>(R.id.more_imageview_calfrag)

        val each = view.findViewById<ConstraintLayout>(R.id.linearLayout11_each)

        //  객체 클릭시
        each.setOnClickListener {

            calindex = todo[position].index
            parentpos = parentposition
            childpos = position

            var intent = Intent(context, CalendarInside_saved::class.java)
            context!!.startActivity(intent)

            dialog.dismiss()
            dialog.cancel()

        }




        //  날짜 변경
        if (todo[position].startdate == todo[position].enddate){
            date.text = todo[position].startdate
        }
        else{
            date.text = "${todo[position].startdate} - ${todo[position].enddate}"
        }


        //  제목 변경
        title.text = todo[position].title


        //  태그 색 변경
        var colorchip = todo[position].color

        var colorlist = arrayOf(R.color.colorPrimary, R.color.colorPrimary, R.color.profile4, R.color.profile3, R.color.profile1, R.color.box5)

        var back = ResourcesCompat.getDrawable(context!!.resources, R.drawable.shape_stickerboard, null) as GradientDrawable
        back.mutate() // Mutate the drawable so changes don't affect every other drawable
        back.setColor(context.getColor(colorlist[colorchip.toInt()]))

        tag.setImageDrawable(back)

        //  날짜 색 변경
        date.setTextColor(context.getColor(colorlist[colorchip.toInt()]))




        more.setOnClickListener {
            //  TODO
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