package com.example.famapp.Board

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.famapp.*
import com.example.famapp.Calendar.CalendarInside
import com.example.famapp.Calendar.listviewAdapter
import com.example.famapp.Global.Companion.basic_url
import com.example.famapp.Global.Companion.editBoard
import org.json.JSONObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList
import android.view.WindowManager
import com.example.famapp.Global.Companion.density


class BoardAdapter (var context: Context, var arrayList: ArrayList<BoardSticker>): BaseAdapter(){

    lateinit var myPreference: MyPreference

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        myPreference = MyPreference(context)
        var userid = myPreference.getUsername()

        var view = View.inflate(context, R.layout.layout_boardsticker, null)

        var sticker = view.findViewById<ConstraintLayout>(R.id.linearLayout11)

        var date = view.findViewById<TextView>(R.id.postdate_textview_board)
        var content = view.findViewById<TextView>(R.id.content_textview_board)
        var writtenby = view.findViewById<TextView>(R.id.writtenby_textview_board)
        var more = view.findViewById<ImageView>(R.id.more_imageview_board)
        var fixedpin = view.findViewById<ImageView>(R.id.fix_imageview_board)


        //  랜덤 색상으로 컬러 바꿔주기
        var colorset = arrayOf(R.drawable.new_boardgreen, R.drawable.new_boardyellow, R.drawable.new_boardpurple, R.drawable.new_boardpurplelight, R.drawable.new_boardpink)
        var detailcolor = arrayOf(R.drawable.board_green, R.drawable.board_yellow, R.drawable.board_dark, R.drawable.board_light, R.drawable.pinkdetail)

        val random = Random()
        val num = random.nextInt(5)


        var username = arrayList[position].userid
        var realname = ""


        //  이름 가져오기
        val myJson = JSONObject()
        val requestBody = myJson.toString()

        val login_url = basic_url + "get_name?userid=$username"

        val testRequest = object : StringRequest(Method.GET, login_url,
            Response.Listener { response ->

                var json_response = JSONObject(response)
                if (json_response["result"].toString() == "1") {

                    realname = json_response["name"].toString()
                    writtenby.text = "written by. " + realname

                }

            }, Response.ErrorListener {

            }) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray {
                return requestBody.toByteArray()
            }
        }

        Volley.newRequestQueue(context).add(testRequest)


        sticker.setBackgroundResource(colorset[num])

        sticker.setOnClickListener {

            val dialog = AlertDialog.Builder(context).create()
            val edialog : LayoutInflater = LayoutInflater.from(context)
            val mView : View = edialog.inflate(R.layout.dialog_boarddetail,null)

            val content : TextView = mView.findViewById(R.id.content_textview_detail)
            val date : TextView = mView.findViewById(R.id.date_textview_detail)
            val writtenby : TextView = mView.findViewById(R.id.writtenby_textview_detail)
            val more : ImageView = mView.findViewById(R.id.more_imageview_detail)

            val each : ConstraintLayout = mView.findViewById(R.id.linearLayout13)

            each.setBackgroundResource(detailcolor[num])


            date.text = arrayList[position].postdate
            content.text = arrayList[position].content


            writtenby.text = "written by. " + realname



            more.setOnClickListener {

                moreDialog(userid, position)

            }


            dialog.setView(mView)
            dialog.create()
            dialog.show()

            var width = 281 * density
            var height = 426 * density


            dialog.getWindow().setLayout(width.toInt(), height.toInt())

        }


        //  날짜, 내용, 쓴사람 값 띄우기
        date.text = arrayList[position].postdate

        if (arrayList[position].content.length > 45){
            content.text = arrayList[position].content.substring(0, 40)
        }
        else{
            content.text = arrayList[position].content

        }


        //  고정된 애들만 아이콘 띄우기
        if (arrayList[position].fixed == "1"){
            fixedpin.visibility = View.VISIBLE
        }
        else{
            fixedpin.visibility = View.INVISIBLE
        }


        more.setOnClickListener {

            moreDialog(userid, position)

        }




        return view

    }

    override fun getItem(position: Int): Any {
        return arrayList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return arrayList.size
    }


    fun moreDialog(userid: String, position: Int){
        val dialog2 = AlertDialog.Builder(context).create()
        val edialog2 : LayoutInflater = LayoutInflater.from(context)
        val mView2 : View = edialog2.inflate(R.layout.dialog_boardmore,null)

        val delete : ConstraintLayout = mView2.findViewById(R.id.conslay1_boarddialog)
        val edit : ConstraintLayout = mView2.findViewById(R.id.conslay2_boarddialog)


        //  삭제 다이얼로그 뜨기
        delete.setOnClickListener {

            val dialog = AlertDialog.Builder(context).create()
            val edialog : LayoutInflater = LayoutInflater.from(context)
            val mView : View = edialog.inflate(R.layout.dialog_delete,null)

            val no : Button = mView.findViewById(R.id.no_button_dl)
            val yes : Button = mView.findViewById(R.id.yes_button_dl)


            no.setOnClickListener {
                dialog.dismiss()
                dialog.cancel()
            }

            yes.setOnClickListener {
                delete(arrayList[position].index, dialog, dialog2)

            }

            dialog.setView(mView)
            dialog.create()
            dialog.show()

        }


        //  편집창으로 이동
        edit.setOnClickListener {

            if (userid == arrayList[position].userid){
                editBoard = arrayList[position]
                var intent = Intent(context, BoardEdit::class.java)
                context.startActivity(intent)

                dialog2.dismiss()
                dialog2.cancel()
            }
            else{
                Toast.makeText(context, "본인이 작성한 게시물만 편집이 가능합니다.", Toast.LENGTH_SHORT).show()

            }

        }


        dialog2.setView(mView2)
        dialog2.create()
        dialog2.show()
    }



    fun delete(index: String, dialog: AlertDialog, dialog2: AlertDialog){

        val myJson = JSONObject()
        val requestBody = myJson.toString()

        val login_url = basic_url + "delete_board?boardindex=$index"

        val testRequest = object : StringRequest(Method.GET, login_url,
            Response.Listener { response ->

                var json_response = JSONObject(response)
                if(json_response["result"].toString() == "1"){

                    Toast.makeText(context, "성공적으로 삭제되었습니다. ", Toast.LENGTH_SHORT).show()

                    dialog.dismiss()
                    dialog2.dismiss()

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