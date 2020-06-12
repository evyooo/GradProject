package com.example.famapp.Board

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.famapp.Global
import com.example.famapp.Global.Companion.basic_url
import com.example.famapp.MyPreference
import com.example.famapp.R
import kotlinx.android.synthetic.main.activity_board_write.*
import kotlinx.android.synthetic.main.activity_board_write.outer_layout
import kotlinx.android.synthetic.main.activity_setting_room.*
import org.json.JSONObject
import java.time.LocalDateTime

class Board_write : AppCompatActivity() {

    lateinit var myPreference: MyPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_write)

        myPreference = MyPreference(this)
        val username = myPreference.getUsername()
        val roomindex = myPreference.getRoomindex()

        //  키보드 내려가게
        outer_layout.setOnClickListener {
            hideKeyboard()
        }


        //  editText 글자 수 세기
        userinput_edittext_boardwrite.addTextChangedListener(object: TextWatcher{

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                wordcount_textview_boardwrite.text = "0 / 100"
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var userinput = userinput_edittext_boardwrite.text.toString()
                wordcount_textview_boardwrite.text = userinput.length.toString() + " / 100"
            }

            override fun afterTextChanged(s: Editable?) {
                var userinput = userinput_edittext_boardwrite.text.toString()
                wordcount_textview_boardwrite.text = userinput.length.toString() + " / 100"
            }
        })


        //  고정
        var fixflag = 0
        fix_imageview_boardwrite.setOnClickListener {
            if (fixflag == 0){
                fix_imageview_boardwrite.setImageResource(R.drawable.icon_fixed)
                fixflag = 1
            }
            else{
                fix_imageview_boardwrite.setImageResource(R.drawable.icon_fixed_default)
                fixflag = 0
            }
        }



        //  취소 버튼
        cancel_button_boardwrite.setOnClickListener {
            finish()
        }



        //  저장 버튼
        save_button_boardwrite.setOnClickListener {

            val current = LocalDateTime.now()

            var mon = current.monthValue.toString()
            if (current.monthValue.toString().length < 2){
                mon = "0${current.monthValue}"
            }
            var postdate = "${current.year}.$mon.${current.dayOfMonth}"

            var content = userinput_edittext_boardwrite.text.toString()


            val myJson = JSONObject()
            val requestBody = myJson.toString()

            val login_url = basic_url + "put_board?roomindex=$roomindex&content=$content&userid=$username&postdate=$postdate&fixed=$fixflag"

            val testRequest = object : StringRequest(Method.GET, login_url,
                Response.Listener { response ->

                    var json_response = JSONObject(response)
                    if(json_response["result"].toString() == "1"){

                        Toast.makeText(this, "성공적으로 저장되었습니다.", Toast.LENGTH_SHORT).show()
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

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(userinput_edittext_boardwrite.windowToken, 0)
    }




}
