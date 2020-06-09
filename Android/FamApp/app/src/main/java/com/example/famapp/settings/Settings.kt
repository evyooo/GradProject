package com.example.famapp.settings

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.famapp.Global
import com.example.famapp.Global.Companion.basic_url
import com.example.famapp.LoginRegister.ChooseRoom
import com.example.famapp.LoginRegister.CreateOrJoin
import com.example.famapp.LoginRegister.Login
import com.example.famapp.MainActivity
import com.example.famapp.MyPreference
import com.example.famapp.R
import kotlinx.android.synthetic.main.activity_settings.*
import org.json.JSONObject

class Settings : AppCompatActivity() {

    lateinit var myPreference: MyPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        myPreference = MyPreference(this)
        val userid = myPreference.getUsername()
        val roomindex = myPreference.getRoomindex()

        //  내 계정 설정
        myaccount_imageview_settings.setOnClickListener {
            var intent = Intent(this, Setting_myaccount::class.java)
            startActivity(intent)
        }

        //  방 설정
        room_imageview_settings.setOnClickListener {
            var intent = Intent(this, Setting_room::class.java)
            startActivity(intent)
        }

        //  멤버 설정
        members_imageview_settings.setOnClickListener {
            var intent = Intent(this, Setting_members::class.java)
            startActivity(intent)
        }

        //  이 방에서 나가기 (dialog)
        leave_imageview_settings.setOnClickListener {

            val dialog = AlertDialog.Builder(this).create()
            val edialog : LayoutInflater = LayoutInflater.from(this)
            val mView : View = edialog.inflate(R.layout.dialog_leave,null)

            val no : Button = mView.findViewById(R.id.no_button_dl)
            val yes : Button = mView.findViewById(R.id.yes_button_dl)

            no.setOnClickListener{
                dialog.dismiss()
                dialog.cancel()

            }

            yes.setOnClickListener{

                leaveRoom(userid, roomindex, dialog)


            }

            dialog.setView(mView)
            dialog.create()
            dialog.show()
        }


        //  로그아웃
        logout_imageview_setting.setOnClickListener {

            myPreference = MyPreference(this)
            myPreference.setUsername("")
            myPreference.setRoomindex("")

            var intent = Intent(this, Login::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }


        //  다른 방 이동
        switch_imageview_settings.setOnClickListener {
            var intent = Intent(this, ChooseRoom::class.java)
            startActivity(intent)
        }

    }

    fun leaveRoom(userid: String, roomindex: String, dialog: AlertDialog){

        val myJson = JSONObject()
        val requestBody = myJson.toString()

        val login_url = basic_url + "leave_room?roomindex=$roomindex&userid=$userid"

        val testRequest = object : StringRequest(Method.GET, login_url,
            Response.Listener { response ->

                var json_response = JSONObject(response)
                if(json_response["result"].toString() == "1"){

                    Toast.makeText(this, "성공적으로 변경되었습니다.", Toast.LENGTH_SHORT).show()

                    dialog.cancel()
                    dialog.dismiss()

                    myPreference.setRoomindex("")

                    var intent = Intent(this, Login::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)

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
