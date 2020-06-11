package com.example.famapp.Calendar

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.famapp.R
import com.example.famapp.forCalendar

class CalendarAdapter(var context: Context, var startblank: Int, var datelist: ArrayList<String>, var arrayList: ArrayList<ArrayList<forCalendar>>, var month: String): BaseAdapter(){

    lateinit var listviewAdapter: listviewAdapter

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var view = View.inflate(context, R.layout.layout_gridviewcalendar, null)

        var date = view.findViewById<TextView>(R.id.date)
        var circle = view.findViewById<ImageView>(R.id.selected_circle)
        var each = view.findViewById<ConstraintLayout>(R.id.each_conslay)

        var box1 = view.findViewById<Button>(R.id.box1_button_cal)
        var box2 = view.findViewById<Button>(R.id.box2_button_cal)
        var box3 = view.findViewById<Button>(R.id.box3_button_cal)
        var box4 = view.findViewById<Button>(R.id.box4_button_cal)

        var boxlist = arrayOf(box1, box2, box3, box4)

        circle.visibility = View.INVISIBLE

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

                for (i in 0..until){
                    boxlist[i].visibility = View.VISIBLE
                    boxlist[i].setText("${arrayList[position - startblank][i].title}")
//                    boxlist[i].setBackgroundColor("${arrayList[position][0].color}")

                }


            }



            //  객체 선택 후 다이얼로그
            each.setOnClickListener {
                //  todo
//            circle.visibility = View.VISIBLE

                val dialog = AlertDialog.Builder(context).create()
                val edialog : LayoutInflater = LayoutInflater.from(context)
                val mView : View = edialog.inflate(R.layout.layout_dialog_calendar,null)

                val date : TextView = mView.findViewById(R.id.textView50)
                val plus: ImageView = mView.findViewById(R.id.imageView20)

                date.text = "${month}월 ${datelist[position]}일"

                var listview : ListView = mView.findViewById(R.id.listview_caldialog)

                listviewAdapter = listviewAdapter(context, arrayList, position-startblank, dialog)
                listview.adapter = listviewAdapter


                plus.setOnClickListener {

                    var intent = Intent(context, CalendarInside::class.java)
                    context.startActivity(intent)

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