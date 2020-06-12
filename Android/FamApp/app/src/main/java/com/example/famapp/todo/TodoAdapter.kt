package com.example.famapp.todo

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.famapp.MyPreference
import com.example.famapp.R
import com.example.famapp.Todo
import java.time.LocalDateTime

class TodoAdapter (val context: Context, val todolist: ArrayList<Todo>) : BaseAdapter(){

    lateinit var myPreference: MyPreference

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.layout_todolistview, null)

        myPreference = MyPreference(context)
        val username = myPreference.getUsername()


        val title = view.findViewById<TextView>(R.id.title_textview_todolay)
        val checkBox = view.findViewById<ImageView>(R.id.checkbox_todolay)

        val datetv = view.findViewById<TextView>(R.id.date_textview_todolay)
        val repeat = view.findViewById<ImageView>(R.id.repeat_imageview_todolay)
        val score = view.findViewById<TextView>(R.id.score_textview_todolay)
        val doneby = view.findViewById<TextView>(R.id.doneby_textview_todolay)

        doneby.visibility = View.GONE

        val todo = todolist[position]

        title.text = todo.title
        datetv.text = todo.duedate

        var scorelist = arrayListOf("없음", "하 - 1점 획득", "중 - 2점 획득", "상 - 3점 획득")
        score.text  = scorelist[todo.score.toInt()]


        if (todo.duedate == ""){
            datetv.visibility = View.GONE
            repeat.visibility = View.GONE
        }

        if (todo.score == "0"){
            score.visibility = View.GONE
        }




        //  오늘 날짜
        val current = LocalDateTime.now()



        //  체크박스
        var flag = 0 //  TODO flag도 db에 저장된거로 바꾸기
        checkBox.setOnClickListener {

            if (flag == 0){
                checkBox.setImageResource(R.drawable.icon_checked_small)
                doneby.visibility = View.VISIBLE
                doneby.text = "${current.monthValue}/${current.dayOfMonth} $username"

                title.setTextColor(context!!.getColor(R.color.colorPrimary))
                title.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG)
                flag = 1
            }
            else{
                checkBox.setImageResource(R.drawable.icon_defaultcheck_small)
                doneby.visibility = View.GONE

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