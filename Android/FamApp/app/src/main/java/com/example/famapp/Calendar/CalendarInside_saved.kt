package com.example.famapp.Calendar

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.famapp.Global
import com.example.famapp.Global.Companion.basic_url
import com.example.famapp.Global.Companion.calendarlist
import com.example.famapp.Global.Companion.calindex
import com.example.famapp.Global.Companion.childpos
import com.example.famapp.Global.Companion.parentpos
import com.example.famapp.R
import kotlinx.android.synthetic.main.activity_calendar_inside_saved.*
import kotlinx.android.synthetic.main.activity_setting_room.*
import org.json.JSONObject

class CalendarInside_saved : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar_inside_saved)

        title_textview_cinside.text = calendarlist[parentpos][childpos].title
        startday_textview_cinside.text = calendarlist[parentpos][childpos].startdate
        endday_textview_cinside.text = calendarlist[parentpos][childpos].enddate

        if (calendarlist[parentpos][childpos].startdate == calendarlist[parentpos][childpos].enddate){
            allday_switch_cinside.setChecked(true)
        }


        //  일정 삭제
        delete_imageview.setOnClickListener {
            deleteDialog(calindex)
        }
    }

    fun deleteDialog(index: String){

        val dialog = AlertDialog.Builder(this).create()
        val edialog : LayoutInflater = LayoutInflater.from(this)
        val mView : View = edialog.inflate(R.layout.dialog_delete,null)

        val no : Button = mView.findViewById(R.id.no_button_dl)
        val yes : Button = mView.findViewById(R.id.yes_button_dl)

        no.setOnClickListener{
            dialog.dismiss()
            dialog.cancel()

        }

        yes.setOnClickListener{
            delete(index, dialog)
            dialog.dismiss()
            dialog.cancel()
        }

        dialog.setView(mView)
        dialog.create()
        dialog.show()
    }


    fun delete(index: String, dialog: AlertDialog){

        //  TODO
        val myJson = JSONObject()
        val requestBody = myJson.toString()

        val login_url = basic_url + "delete_calendar?calindex=$index"

        val testRequest = object : StringRequest(Method.GET, login_url,
            Response.Listener { response ->

                var json_response = JSONObject(response)
                if(json_response["result"].toString() == "1"){

                    Toast.makeText(this, "성공적으로 삭제되었습니다.", Toast.LENGTH_SHORT).show()

                    finish()

                }

            }, Response.ErrorListener {
                Toast.makeText(this, "서버 연결을 확인해주세요", Toast.LENGTH_SHORT).show()

            }) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray {
                return requestBody.toByteArray()
            }
        }

        Volley.newRequestQueue(this).add(testRequest)

    }
}
