package com.example.famapp.Board


import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.fragment_board.*
import java.util.*
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.widget.LinearLayout
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.famapp.*
import com.example.famapp.Calendar.CalendarAdapter
import com.example.famapp.Global.Companion.basic_url
import kotlinx.android.synthetic.main.fragment_calendar.*
import org.json.JSONObject


class BoardFragment : Fragment() {

    lateinit var boardAdapter: BoardAdapter
    lateinit var myPreference: MyPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_board, container, false)
    }

    override fun onResume() {
        super.onResume()

        myPreference = MyPreference(requireContext())
        val roomindex = myPreference.getRoomindex()


        //  추가
        add_imageview_board.setOnClickListener {
            activity?.let{
                val intent = Intent(context, Board_write::class.java)
                startActivity(intent)
            }
        }


        drawSticker(roomindex)






    }

    fun drawSticker(roomindex: String){

        val myJson = JSONObject()
        val requestBody = myJson.toString()

        val url = basic_url + "get_board?roomindex=$roomindex"

        val testRequest = object : StringRequest(Method.GET, url,
            Response.Listener { response ->


                var stickerlist = arrayListOf<BoardSticker>()

                var json_response = JSONObject(response)
                if(json_response["result"].toString() == "1"){

                    var boardinfo = json_response.getJSONArray("boardinfo")
                    var arraysize = boardinfo.length()


                    for (i in 0..arraysize-1){
                        var tempwhole = boardinfo.getJSONObject(i)

                        var boardindex = tempwhole.getString("boardindex")
                        var content = tempwhole.getString("content")
                        var userid = tempwhole.getString("userid")
                        var postdate = tempwhole.getString("postdate")
                        var fixed = tempwhole.getString("fixed")

                        stickerlist.add(BoardSticker(boardindex, content, userid, postdate, fixed))


                    }

                }


                boardAdapter = BoardAdapter(requireContext(), stickerlist)
                gridview_board.adapter = boardAdapter



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

        Volley.newRequestQueue(requireContext()).add(testRequest)
    }



}
