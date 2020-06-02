package com.example.famapp.Home

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.famapp.Global.Companion.memberslist
import com.example.famapp.R
import kotlinx.android.synthetic.main.layout_members_recyclerview.view.*
import org.json.JSONObject

class HomeMembersAdapter: RecyclerView.Adapter<CustomViewHolder>() {

    override fun getItemCount(): Int {
        return memberslist.size

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {

        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.layout_memberslistview, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        val member = memberslist[position]

        holder?.view?.name_textview_members.text = member.name


        //  색상 띄우기
        val colorlist = arrayOf(R.color.profile1, R.color.profile2, R.color.profile3, R.color.profile4,
            R.color.profile5, R.color.profile6, R.color.profile7, R.color.profile8,
            R.color.profile9, R.color.profile10, R.color.profile11, R.color.profile12,
            R.color.profile13, R.color.profile14, R.color.profile15, R.color.profile16)

        var background = ResourcesCompat.getDrawable(holder.view.context.resources, R.drawable.design_12circle, null) as GradientDrawable
        background.mutate() // Mutate the drawable so changes don't affect every other drawable

        //  TODO ..
        background.setColor(colorlist[member.coloring.toInt()])

        holder?.view?.color_imageview_members?.setImageDrawable(background)

    }
}



class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view){

}