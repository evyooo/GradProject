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
import java.time.format.DateTimeFormatter

class TodoAdapter (val context: Context, val todolist: ArrayList<Todo>) : BaseAdapter(){

    lateinit var myPreference: MyPreference

    //  오늘 날짜
    val current = LocalDateTime.now()
    val year = current.year.toString()
    var month = current.monthValue.toString()


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.layout_todolistview, null)

        myPreference = MyPreference(context)
        val username = myPreference.getUsername()
        val roomindex = myPreference.getRoomindex()


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


        if (month.length < 2){
            month = "0$month"
        }




        if (todo.duedate == ""){
            datetv.visibility = View.GONE
            repeatimg.visibility = View.GONE
            repeattv.visibility = View.GONE
        }

        if (todo.score == "0"){
            score.visibility = View.GONE
        }




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

                doneby.text = "$monthstr.${current.dayOfMonth} $username"

                title.setTextColor(context!!.getColor(R.color.colorPrimary))
                title.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG)
                flag = 1

                var weekofMonth = weekofMonth(current)

                done(roomindex, username, "$monthstr.${current.dayOfMonth}", todo.index, weekofMonth.toString())

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



    fun done(roomindex: String, userid: String, donedate: String, todoindex: String, weekofMonth: String){

        val myJson = JSONObject()
        val requestBody = myJson.toString()

        val login_url =
            basic_url + "update_todo?roomindex=$roomindex&userid=$userid&donedate=$donedate&todoindex=$todoindex&year=$year&month=$month&weekofMonth=$weekofMonth"

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

    fun weekofMonth(current: LocalDateTime) : Int{

        val formatter = DateTimeFormatter.ofPattern("M월 d일")

        //  요일
        val rawdayOfWeek = DateTimeFormatter.ofPattern("E")
        val formatdayOfWeek = current.format(rawdayOfWeek)


        var startofWeek = current

        when (formatdayOfWeek){
            "Mon" -> startofWeek = current.minusDays(1)
            "Tue" -> startofWeek = current.minusDays(2)
            "Wed" -> startofWeek = current.minusDays(3)
            "Thu" -> startofWeek = current.minusDays(4)
            "Fri" -> startofWeek = current.minusDays(5)
            "Sat" -> startofWeek = current.minusDays(6)
            "Sun" -> startofWeek = current
        }


        return startofWeek.dayOfMonth/7 + 1


    }
}