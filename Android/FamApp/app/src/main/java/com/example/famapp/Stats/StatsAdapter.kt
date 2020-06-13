package com.example.famapp.Stats

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import android.content.ClipData
import android.content.ClipDescription
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.DragEvent
import android.widget.*
import com.example.famapp.R
import com.example.famapp.StatData
import com.example.famapp.StatMembers


class StatsAdapter(var context: Context, var header: MutableList<StatMembers>, var body: MutableList<MutableList<StatData>>) : BaseExpandableListAdapter() {
    override fun getGroup(groupPosition: Int): StatMembers {
        return header[groupPosition]
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View? {
        var convertView = convertView
        if (convertView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.layout_statsgroup, null)
        }
        val line = convertView?.findViewById<View>(R.id.line_view_statsgroup)
        val prize_img = convertView?.findViewById<ImageView>(R.id.prize_imageview_statsgroup)
        val prize_txt = convertView?.findViewById<TextView>(R.id.prize_textview_stats)
        val name = convertView?.findViewById<TextView>(R.id.name_textview_statsgroup)
        val score = convertView?.findViewById<TextView>(R.id.score_textview_statsgroup)
        val indicator = convertView?.findViewById<ImageView>(R.id.indicator_imageview_statsgroup)

        name!!.text = header[groupPosition].name
        score!!.text = header[groupPosition].score


        when(groupPosition){
            0 -> {
                prize_img!!.visibility = View.VISIBLE
                prize_img.setImageResource(R.drawable.first)
                prize_txt!!.visibility = View.INVISIBLE
            }
            1 -> {
                prize_img!!.visibility = View.VISIBLE
                prize_img.setImageResource(R.drawable.second)
                prize_txt!!.visibility = View.INVISIBLE
            }
            2 -> {
                prize_img!!.visibility = View.VISIBLE
                prize_img.setImageResource(R.drawable.third)
                prize_txt!!.visibility = View.INVISIBLE
            }
            else -> {
                prize_img!!.visibility = View.INVISIBLE
                prize_txt!!.visibility = View.VISIBLE
            }
        }


        if (isExpanded) {
            indicator?.setImageResource(R.drawable.indicator_up)
            line!!.visibility = View.GONE
        } else {
            indicator?.setImageResource(R.drawable.indicator_down)
            line!!.visibility = View.VISIBLE
        }

        return convertView
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return body[groupPosition].size
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return body[groupPosition][childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View? {

        var convertView = convertView
        if (convertView == null) {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.layout_statschild, null)
        }

        val week = convertView?.findViewById<TextView>(R.id.week_textview_statschild)
        val content = convertView?.findViewById<TextView>(R.id.content_textview_statschild)
        val line = convertView?.findViewById<View>(R.id.line_statschild)
        val blank = convertView?.findViewById<ImageView>(R.id.blank_imageview_statschild)

        when (childPosition){
            0 -> week!!.text = "첫째주"
            1 -> week!!.text = "둘째주"
            2 -> week!!.text = "셋째주"
            3 -> week!!.text = "넷째주"
        }


        var contentData = body[groupPosition][childPosition].content
        var contentarr = contentData.split("]],[[").toTypedArray()

        var tempstr = ""

        for (each in contentarr){

            //  TODO 여러개일때 다시보기

            var temparr = each.split(",").toTypedArray()

            var first = temparr[0].replace("[", "")
            first = first.replace('"',' ')
            var second = temparr[1].replace('"',' ')
            second = second.replace("]", "")
            var third = temparr[2].replace('"',' ')
            third = third.replace("]", "")
            third = third.replace(" ", "")


            //  0점 획득이면 점수 안쓰기
            if(second == "0"){
                tempstr += "$third $first "
            }
            else{
                tempstr += "$third $first +$second "
            }


        }


        content!!.text = tempstr




        //  마지막 child가 아닐 경우
        if (childPosition != body[groupPosition].size - 1){
            line!!.visibility = View.INVISIBLE
            blank!!.visibility = View.GONE
        }
        else{
            line!!.visibility = View.VISIBLE
            blank!!.visibility = View.VISIBLE
        }

        return convertView
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getGroupCount(): Int {
        return header.size
    }

}