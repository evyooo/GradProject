package com.example.famapp.todo


import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.famapp.Global
import com.example.famapp.Global.Companion.basic_url
import com.example.famapp.MyPreference
import com.example.famapp.R
import com.example.famapp.Todo
import kotlinx.android.synthetic.main.fragment_todo.*
import org.json.JSONObject
import java.time.LocalDateTime


class TodoFragment : Fragment() {

    lateinit var todoAdapter: TodoAdapter
    lateinit var myPreference: MyPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todo, container, false)
    }

    override fun onResume() {
        super.onResume()

        var filterlist = arrayOf(filter1_conslay, filter2_conslay, filter3_conslay, filter4_conslay, filter5_conslay, filter6_conslay)
        var textlist = arrayOf(filter1_textview, filter2_textview, filter3_textview, filter4_textview, filter5_textview, filter6_textview)
        var flaglist = arrayListOf(0, 0, 0, 0, 0, 0)

        var current = 0

        //  TODO multiselect
        //  filter
        for (i in 0..5){
            filterlist[i].setOnClickListener {

                //  다 끄고
                for (i in 0..5){
                    filterlist[i].setBackgroundResource(R.drawable.filter_default)
                    textlist[i].setTextColor(resources.getColor(R.color.black))
                    flaglist[i] = 0
                }

                //  해당 켜기
                if (flaglist[i] == 0){
                    filterlist[i].setBackgroundResource(R.drawable.fileter_selected)
                    textlist[i].setTextColor(resources.getColor(R.color.white))
                    flaglist[i] = 1
                    current = i
                    bringTodo(current)

                }
                else{
                    filterlist[i].setBackgroundResource(R.drawable.filter_default)
                    textlist[i].setTextColor(resources.getColor(R.color.black))
                    flaglist[i] = 0
                }
            }
        }


        //  서버에서 가져오기
        bringTodo(current)



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

    fun bringTodo(filter: Int){

        myPreference = MyPreference(requireContext())
        val roomindex = myPreference.getRoomindex()


        //  현재 날짜 설정
        val current = LocalDateTime.now()

        var mon = ""
        if (current.monthValue.toString().length < 2){
            mon = "0${current.monthValue}"
        }

        var duedate = "${current.year}.$mon.${current.dayOfMonth}"



        val myJson = JSONObject()
        val requestBody = myJson.toString()

        val login_url = basic_url + "get_todo?roomindex=$roomindex&duedate=$duedate&filter=$filter"

        val testRequest = object : StringRequest(Method.GET, login_url,
            Response.Listener { response ->

                var json_response = JSONObject(response)
                if (json_response["result"].toString() == "1") {

                    var templist = arrayListOf<Todo>()

                    var noticeresult = json_response.getJSONArray("todo")
                    var arraysize = noticeresult.length()

                    for (i in (0 .. arraysize-1)){

                        var obj = noticeresult.getJSONObject(i)

                        var index = obj.getInt("index").toString()
                        var title = obj.getString("title")
                        var duedate = obj.getString("duedate")
                        var score = obj.getString("score")
                        var remind = obj.getString("remind")
                        var doneby = obj.getString("userid")
                        var donedate = obj.getString("donedate")

                        var temp = Todo(
                            "$index",
                            "$title",
                            "$duedate",
                            "$score",
                            "$remind",
                            "$doneby",
                            "$donedate")

                        templist.add(temp)

                    }
                    todoAdapter = TodoAdapter(requireContext(), templist)
                    listview_todolist.adapter = todoAdapter


                }

            }, Response.ErrorListener {
                Toast.makeText(context, "서버 연결을 확인해주세요", Toast.LENGTH_SHORT).show()

            }) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray {
                return requestBody.toByteArray()
            }
        }

        Volley.newRequestQueue(context).add(testRequest)


    }


}
