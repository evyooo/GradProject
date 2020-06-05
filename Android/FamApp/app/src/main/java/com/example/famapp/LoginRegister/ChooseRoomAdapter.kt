package com.example.famapp.LoginRegister

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.famapp.MainActivity
import com.example.famapp.MyPreference
import com.example.famapp.R
import com.example.famapp.RoomInfo

class ChooseRoomAdapter (val context: Context, val roominfoist: ArrayList<RoomInfo>) : BaseAdapter() {

    lateinit var myPreference: MyPreference


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        /* LayoutInflater는 item을 Adapter에서 사용할 View로 부풀려주는(inflate) 역할을 한다. */
        val view: View = LayoutInflater.from(context).inflate(R.layout.layout_chooseroom, null)

        /* 위에서 생성된 view를 res-layout-places_listview.xml 파일의 각 View와 연결하는 과정이다. */

        val roominfo = roominfoist[position]

        val name = view.findViewById<TextView>(R.id.roomname)
        name.text = roominfo.roomname


        var eachitem = view.findViewById<ConstraintLayout>(R.id.eachlayout)

        eachitem.setOnClickListener {

            myPreference = MyPreference(context)
            myPreference.setRoomindex(roominfo.roomindex)

            var intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }




        return view
    }

    override fun getItem(position: Int): Any {

        return roominfoist[position]
    }

    override fun getItemId(position: Int): Long {

        return 0
    }

    override fun getCount(): Int {

        return roominfoist.size
    }


}