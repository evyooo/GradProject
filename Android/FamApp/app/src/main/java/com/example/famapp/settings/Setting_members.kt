package com.example.famapp.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.famapp.Global.Companion.memberslist
import com.example.famapp.Members
import com.example.famapp.R
import kotlinx.android.synthetic.main.activity_setting_members.*

class Setting_members : AppCompatActivity() {

    lateinit var membersAdapter: MembersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_members)


        //  listview
        membersAdapter = MembersAdapter(this, memberslist)
        members_listview.adapter = membersAdapter


    }
}
