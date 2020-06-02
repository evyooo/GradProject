package com.example.famapp.settings

import android.app.AlertDialog
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import com.example.famapp.Members
import com.example.famapp.R

class MembersAdapter (val context: Context, val memberlist: ArrayList<Members>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.layout_memberslistview, null)

        val name = view.findViewById<TextView>(R.id.name_textview_memlay)
        val coloring = view.findViewById<ImageView>(R.id.coloring_shape_memlay)
        val more = view.findViewById<ImageView>(R.id.more_imageview_memlay)

        val memberinfo = memberlist[position]

        //  이름 띄우기
        name.text = memberinfo.name


        //  본인 색상 띄우기
        var background = ResourcesCompat.getDrawable(context.resources, R.drawable.design_12circle, null) as GradientDrawable
        background.mutate() // Mutate the drawable so changes don't affect every other drawable

        when (memberinfo.coloring) {
            1 -> background.setColor(context.getColor(R.color.profile1))
            2 -> background.setColor(context.getColor(R.color.profile2))
            3 -> background.setColor(context.getColor(R.color.profile3))
            4 -> background.setColor(context.getColor(R.color.profile4))
            5 -> background.setColor(context.getColor(R.color.profile5))
            6 -> background.setColor(context.getColor(R.color.profile6))
            7 -> background.setColor(context.getColor(R.color.profile7))
            8 -> background.setColor(context.getColor(R.color.profile8))
            9 -> background.setColor(context.getColor(R.color.profile9))
            10 -> background.setColor(context.getColor(R.color.profile10))
            11 -> background.setColor(context.getColor(R.color.profile11))
            12 -> background.setColor(context.getColor(R.color.profile12))
            13 -> background.setColor(context.getColor(R.color.profile13))
            14 -> background.setColor(context.getColor(R.color.profile14))
            15 -> background.setColor(context.getColor(R.color.profile15))
            16 -> background.setColor(context.getColor(R.color.profile16))
        }

        coloring.setImageDrawable(background)


        //  more 아이콘 클릭
        more.setOnClickListener {
            //  dialog 띄우기

            val dialog = AlertDialog.Builder(context)
            val edialog : LayoutInflater = LayoutInflater.from(context)
            val mView : View = edialog.inflate(R.layout.dialog_member1,null)

            val changecolor : ConstraintLayout = mView.findViewById(R.id.firstlay_dm1)
            val kickout : ConstraintLayout = mView.findViewById(R.id.seclay_dm1)

            //  대표 색상 바꾸기
            changecolor.setOnClickListener{

                val dialog3 = AlertDialog.Builder(context)
                val edialog3 : LayoutInflater = LayoutInflater.from(context)
                val mView3 : View = edialog3.inflate(R.layout.dialog_member3,null)

                val alertdialog3 : TextView = mView3.findViewById(R.id.alertTitle_dm3)
                alertdialog3.text = "${memberinfo.name}님의 대표 색상을 선택해주세요"

                val color1 : ImageView = mView3.findViewById(R.id.selectcircle1_dm3)
                val color2 : ImageView = mView3.findViewById(R.id.selectcircle2_dm3)
                val color3 : ImageView = mView3.findViewById(R.id.selectcircle3_dm3)
                val color4 : ImageView = mView3.findViewById(R.id.selectcircle4_dm3)
                val color5 : ImageView = mView3.findViewById(R.id.selectcircle5_dm3)
                val color6 : ImageView = mView3.findViewById(R.id.selectcircle6_dm3)
                val color7 : ImageView = mView3.findViewById(R.id.selectcircle7_dm3)
                val color8 : ImageView = mView3.findViewById(R.id.selectcircle8_dm3)
                val color9 : ImageView = mView3.findViewById(R.id.selectcircle9_dm3)
                val color10 : ImageView = mView3.findViewById(R.id.selectcircle10_dm3)
                val color11 : ImageView = mView3.findViewById(R.id.selectcircle11_dm3)
                val color12 : ImageView = mView3.findViewById(R.id.selectcircle12_dm3)
                val color13 : ImageView = mView3.findViewById(R.id.selectcircle13_dm3)
                val color14 : ImageView = mView3.findViewById(R.id.selectcircle14_dm3)
                val color15 : ImageView = mView3.findViewById(R.id.selectcircle15_dm3)
                val color16 : ImageView = mView3.findViewById(R.id.selectcircle16_dm3)

                val colorlist = arrayOf(R.color.profile1, R.color.profile2, R.color.profile3, R.color.profile4,
                    R.color.profile5, R.color.profile6, R.color.profile7, R.color.profile8,
                    R.color.profile9, R.color.profile10, R.color.profile11, R.color.profile12,
                    R.color.profile13, R.color.profile14, R.color.profile15, R.color.profile16)

                val circlelist = arrayOf(color1, color2, color3, color4, color5, color6, color7, color8,
                    color9, color10, color11, color12, color13, color14, color15, color16)

                for (i in 0..15){

                    var back = ResourcesCompat.getDrawable(context.resources, R.drawable.design_40circle, null) as GradientDrawable
                    back.mutate() // Mutate the drawable so changes don't affect every other drawable

                    back.setColor(context.getColor(colorlist[i]))

                    circlelist[i].setImageDrawable(back)
                }

                dialog3.setView(mView3)
                dialog3.create()
                dialog3.show()
            }

            //  쫒아내기
            kickout.setOnClickListener{

                val dialog2 = AlertDialog.Builder(context)
                val edialog2 : LayoutInflater = LayoutInflater.from(context)
                val mView2 : View = edialog2.inflate(R.layout.dialog_member2,null)

                val alert : TextView = mView2.findViewById(R.id.textView13)
                alert.text = "${memberinfo.name}님을 이 방에서\n정말 내보내시겠습니까?"

                dialog2.setView(mView2)
                dialog2.create()
                dialog2.show()

            }

            dialog.setView(mView)
            dialog.create()
            dialog.show()
        }

        return view
    }

    override fun getItem(position: Int): Any {

        return memberlist[position]
    }

    override fun getItemId(position: Int): Long {

        return 0
    }

    override fun getCount(): Int {

        return memberlist.size
    }


}