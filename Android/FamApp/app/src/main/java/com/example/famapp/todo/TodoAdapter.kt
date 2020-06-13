package com.example.famapp.todo

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.famapp.Global
import com.example.famapp.Global.Companion.basic_url
import com.example.famapp.MyPreference
import com.example.famapp.R
import com.example.famapp.Todo
import org.json.JSONObject
import java.time.LocalDateTime

class TodoAdapter (val context: Context, val todolist: ArrayList<Todo>) : BaseAdapter(){

    lateinit var myPreference: MyPreference

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.layout_todolistview, null)

        myPreference = MyPreference(context)
        val username = myPreference.getUsername()


        val title = view.findViewById<TextView>(R.id.title_textview_todolay)
        val checkBox = view.findViewById<ImageView>(R.id.checkbox_todolay)

        val datetv = view.findViewById<TextView>(R.id.date_textview_todolay)
        val repeattv = view.findViewById<TextView>(R.id.repeat_textview_todolay)
        val repeatimg = view.findViewById<ImageView>(R.id.repeat_imageview_todolay)
        val score = view.findViewById<TextView>(R.id.score_textview_todolay)
        val doneby = view.findViewById<TextView>(R.id.doneby_textview_todolay)

        doneby.visibility = View.GONE

        val todo = todolist[position]

        title.text = todo.title
        datetv.text = todo.duedate

        var scorelist = arrayListOf("없음", "하 - 1점 획득", "중 - 2점 획득", "상 - 3점 획득")
        score.text  = scorelist[todo.score.toInt()]

//        var repeatlist = arrayListOf("월요일마다", "화요일마다", "수요일마다", "목요일마다", "금요일마다", "토요일마다", "일요일마다", "반복 없음")
//
//
//        repeattv.text = repeatlist[]



        if (todo.duedate == ""){
            datetv.visibility = View.GONE
            repeatimg.visibility = View.GONE
            repeattv.visibility = View.GONE
        }

        if (todo.score == "0"){
            score.visibility = View.GONE
        }


        //  오늘 날짜
        val current = LocalDateTime.now()

        //  체크박스
        var flag : Int

        if (todo.doneby == "null"){
            checkBox.setImageResource(R.drawable.icon_defaultcheck_small)
            doneby.visibility = View.GONE

            title.setTextColor(Color.BLACK)
            title.setPaintFlags(0)
            flag = 0
        }
        else{
            checkBox.setImageResource(R.drawable.icon_checked_small)
            doneby.visibility = View.VISIBLE
            doneby.text = "${todo.donedate} ${todo.doneby}"

            title.setTextColor(context!!.getColor(R.color.colorPrimary))
            title.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG)
            flag = 1
        }


        checkBox.setOnClickListener {

            if (flag == 0){
                checkBox.setImageResource(R.drawable.icon_checked_small)
                doneby.visibility = View.VISIBLE

                var monthstr = current.monthValue.toString()
                if (current.monthValue.toString().length < 2){
                    monthstr = "0${current.monthValue}"
                }

                doneby.text = "$monthstr/${current.dayOfMonth} $username"

                title.setTextColor(context!!.getColor(R.color.colorPrimary))
                title.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG)
                flag = 1

                done(username, "$monthstr/${current.dayOfMonth}", todo.index)

            }
            else{
                checkBox.setImageResource(R.drawable.icon_defaultcheck_small)
                doneby.visibility = View.GONE

                title.setTextColor(Color.BLACK)
                title.setPaintFlags(0)
                flag = 0

                undo(todo.index)

            }
        }






        return view
    }

    override fun getItem(position: Int): Any {

        return todolist[position]
    }

    override fun getItemId(position: Int): Long {

        return 0
    }

    override fun getCount(): Int {

        return todolist.size
    }



    fun done(userid: String, donedate: String, todoindex: String){

        val myJson = JSONObject()
        val requestBody = myJson.toString()

        val login_url =
            basic_url + "update_todo?userid=$userid&donedate=$donedate&todoindex=$todoindex"

        val testRequest = object : StringRequest(Method.GET, login_url,
            Response.Listener { response ->

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

    fun undo(todoindex: String){

        val myJson = JSONObject()
        val requestBody = myJson.toString()

        val login_url =
            basic_url + "undo_todo?todoindex=$todoindex"

        val testRequest = object : StringRequest(Method.GET, login_url,
            Response.Listener { response ->

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