package com.example.famapp.Board

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.famapp.Global
import com.example.famapp.Global.Companion.basic_url
import com.example.famapp.Global.Companion.editBoard
import com.example.famapp.R
import kotlinx.android.synthetic.main.activity_board_edit.*
import org.json.JSONObject

class BoardEdit : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_edit)


    }

    override fun onResume() {
        super.onResume()

        userinput_edittext_boardedit.setText(editBoard.content)

        //  켜져있으면
        var fixedflag : Int
        if (editBoard.fixed == "1"){
            fix_imageview_boardwrite.setImageResource(R.drawable.icon_fixed)
            fixedflag = 1
        }
        else{
            fix_imageview_boardwrite.setImageResource(R.drawable.icon_fixed_default)
            fixedflag = 0
        }



        fix_imageview_boardwrite.setOnClickListener {
            if (fixedflag == 0){
                fix_imageview_boardwrite.setImageResource(R.drawable.icon_fixed)
                fixedflag = 1
            }
            else{
                fix_imageview_boardwrite.setImageResource(R.drawable.icon_fixed_default)
                fixedflag = 0
            }
        }


        cancel_button_boardedit.setOnClickListener {
            finish()
        }

        save_button_boardedit.setOnClickListener {

            val content = userinput_edittext_boardedit.text.toString()
            var boardindex = editBoard.index


            val myJson = JSONObject()
            val requestBody = myJson.toString()

            val login_url = basic_url + "update_board?content=$content&fixed=$fixedflag&boardindex=$boardindex"

            val testRequest = object : StringRequest(Method.GET, login_url,
                Response.Listener { response ->

                    var json_response = JSONObject(response)
                    if(json_response["result"].toString() == "1"){

                        Toast.makeText(this, "성공적으로 편집되었습니다. ", Toast.LENGTH_SHORT).show()

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
}
