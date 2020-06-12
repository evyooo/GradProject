package com.example.famapp.Calendar

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import com.example.famapp.Global.Companion.calcurrentmon
import com.example.famapp.Global.Companion.calcurrentyr
import com.example.famapp.Global.Companion.caldate
import com.example.famapp.R
import com.example.famapp.forCalendar
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CalendarAdapter(var context: Context, var startblank: Int, var datelist: ArrayList<String>, var arrayList: ArrayList<ArrayList<forCalendar>>, var month: String): BaseAdapter(){

    lateinit var listviewAdapter: listviewAdapter

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var view = View.inflate(context, R.layout.layout_gridviewcalendar, null)

        var date = view.findViewById<TextView>(R.id.date)
        var circle = view.findViewById<ImageView>(R.id.selected_circle)
        var each = view.findViewById<ConstraintLayout>(R.id.each_conslay)

        var box1 = view.findViewById<ImageView>(R.id.box1_button_cal)
        var box2 = view.findViewById<ImageView>(R.id.box2_button_cal)
        var box3 = view.findViewById<ImageView>(R.id.box3_button_cal)
        var box4 = view.findViewById<ImageView>(R.id.box4_button_cal)

        var text1 = view.findViewById<TextView>(R.id.box1_textview_cal)
        var text2 = view.findViewById<TextView>(R.id.box2_textview_cal)
        var text3 = view.findViewById<TextView>(R.id.box3_textview_cal)
        var text4 = view.findViewById<TextView>(R.id.box4_textview_cal)

        var boxlist = arrayOf(box1, box2, box3, box4)
        var boxtextlist = arrayOf(text1, text2, text3, text4)


        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("YYYY.MM.dd")
        val formatted = current.format(formatter)

        //  오늘에 동그라미 띄우기
        if ("$calcurrentyr.$calcurrentmon.${datelist[position]}" == formatted){
            circle.visibility = View.VISIBLE
            date.setTextColor(context.getColor(R.color.white))
        }
        else{
            circle.visibility = View.INVISIBLE
            date.setTextColor(context.getColor(R.color.black))
        }



        date.text = datelist[position]

        for (i in 0..3){
            boxlist[i].visibility = View.INVISIBLE
        }



        //  날짜 띄우기

        if (datelist[position] != ""){

            if (arrayList[position-startblank].size != 0){
                //  가져오기

                var until = arrayList[position-startblank].size - 1
                if (arrayList[position-1].size > 4){
                    until = 3

                }

                var colorchip : Int



                for (i in 0..until){
                    boxlist[i].visibility = View.VISIBLE
                    boxtextlist[i].text = "${arrayList[position - startblank][i].title}"

                    var background = ResourcesCompat.getDrawable(context.resources, R.drawable.shape_calendarschedule, null) as GradientDrawable
                    background.mutate() // Mutate the drawable so changes don't affect every other drawable


                    when ((arrayList[position - startblank][i].color).toInt()){
                        0 -> colorchip = R.color.colorPrimary
                        1 -> colorchip = R.color.colorPrimary
                        2 -> colorchip = R.color.profile4
                        3 -> colorchip = R.color.profile3
                        4 -> colorchip = R.color.profile1
                        else -> colorchip = R.color.box5
                    }
                    background.setColor(context.getColor(colorchip))
                    boxlist[i].setImageDrawable(background)

                }


            }



            //  객체 선택 후 다이얼로그
            each.setOnClickListener {
                //  todo
//            circle.visibility = View.VISIBLE

                val dialog = AlertDialog.Builder(context).create()
                val edialog : LayoutInflater = LayoutInflater.from(context)
                val mView : View = edialog.inflate(R.layout.dialog_calendar,null)

                val date : TextView = mView.findViewById(R.id.textView50)
                val plus: ImageView = mView.findViewById(R.id.imageView20)

                date.text = "${month}월 ${datelist[position]}일"

                var listview : ListView = mView.findViewById(R.id.listview_caldialog)

                listviewAdapter = listviewAdapter(context, arrayList, position-startblank, dialog)
                listview.adapter = listviewAdapter


                plus.setOnClickListener {

                    var intent = Intent(context, CalendarInside::class.java)
                    context.startActivity(intent)

                    if (month.length < 2){
                        month = "0$month"
                    }

                    caldate = "2020.${month}.${datelist[position]}"

                    dialog.dismiss()
                    dialog.cancel()

                }

                dialog.setView(mView)
                dialog.create()
                dialog.show()

            }


        }





        return view

    }

    override fun getItem(position: Int): Any {
        return datelist.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return datelist.size
    }

    fun color(colorchip: Int){



    }


}