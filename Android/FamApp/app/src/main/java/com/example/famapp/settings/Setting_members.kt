package com.example.famapp.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.famapp.Members
import com.example.famapp.R
import kotlinx.android.synthetic.main.activity_setting_members.*

class Setting_members : AppCompatActivity() {

    lateinit var membersAdapter: MembersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_members)

        var memberslist = arrayListOf<Members>()

        var temp = Members("user1", "1")
        var temp1 = Members("user2", "2")
        var temp2 = Members("user3", "3")

        memberslist.add(temp)
        memberslist.add(temp1)
        memberslist.add(temp2)


        //  listview
        membersAdapter = MembersAdapter(this, memberslist)
        members_listview.adapter = membersAdapter


    }
}
