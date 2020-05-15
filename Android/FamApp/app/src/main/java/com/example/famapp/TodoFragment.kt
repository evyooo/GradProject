package com.example.famapp


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_todo.*


class TodoFragment : Fragment() {

    lateinit var todoAdapter: TodoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todo, container, false)
    }

    override fun onResume() {
        super.onResume()

        //  TODO 여기 서버에서 받아오기
        var templist = arrayListOf<String>()

        templist.add("1")
        templist.add("2")
        templist.add("3")
        templist.add("4")

        todoAdapter = TodoAdapter(context, templist)
        listview_todolist.adapter = todoAdapter


    }


}
