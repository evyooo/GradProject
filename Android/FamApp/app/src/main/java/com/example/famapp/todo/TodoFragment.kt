package com.example.famapp.todo


import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.famapp.R
import com.example.famapp.Todo
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
        var templist = arrayListOf<Todo>()

        templist.add(Todo("title1", "2020.06.03", "상", "매일", ""))
        templist.add(Todo("title2", "2020.06.03", "중", "매일", ""))
        templist.add(Todo("title3", "", "하", "", ""))
        templist.add(Todo("title4", "2020.06.03", "", "매일", ""))



        todoAdapter = TodoAdapter(requireContext(), templist)
        listview_todolist.adapter = todoAdapter


        //  dialog 띄우기
        more_imageview_todo.setOnClickListener {

            val dialog2 = AlertDialog.Builder(context).create()
            val edialog2 : LayoutInflater = LayoutInflater.from(context)
            val mView2 : View = edialog2.inflate(R.layout.dialog_todo_delete,null)

            val deletedone : ConstraintLayout = mView2.findViewById(R.id.select1_conslay_diatodo)
            val deleteselect : ConstraintLayout = mView2.findViewById(R.id.select2_conslay_diatodo)
            val deletepast : ConstraintLayout = mView2.findViewById(R.id.select3_conslay_diatodo)


            //  완료 항목 삭제
            deletedone.setOnClickListener {
                dialog2.dismiss()
                dialog2.cancel()

            }

            //  선택 항목 삭제
            deleteselect.setOnClickListener {
                dialog2.dismiss()
                dialog2.cancel()

            }

            //  과거 항목 삭제
            deletepast.setOnClickListener {
                dialog2.dismiss()
                dialog2.cancel()

            }


            dialog2.setView(mView2)
            dialog2.create()
            dialog2.show()
        }


        //  추가하기
        add_imageview_todo.setOnClickListener {
            var intent = Intent(context, NewTodo::class.java)
            startActivity(intent)
        }


    }


}
