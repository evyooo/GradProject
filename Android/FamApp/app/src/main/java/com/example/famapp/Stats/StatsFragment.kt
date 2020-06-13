package com.example.famapp.Stats


import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.NumberPicker
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.famapp.*
import com.example.famapp.Global.Companion.basic_url
import com.example.famapp.LoginRegister.Login
import com.example.famapp.todo.TodoAdapter
import kotlinx.android.synthetic.main.fragment_stats.*
import kotlinx.android.synthetic.main.fragment_todo.*
import org.json.JSONObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Collections.max
import kotlin.math.max


class StatsFragment : Fragment() {

    //  expandableListView (제목 / 내용 : [group][child].plname 형태)
    val header: MutableList<StatMembers> = ArrayList()
    val body: MutableList<MutableList<StatData>> = ArrayList()

    lateinit var myPreference: MyPreference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stats, container, false)
    }


    override fun onStart() {
        super.onStart()

        //  현재 날짜 설정
        val current = LocalDateTime.now()
        val year = current.year.toString()
        var month = current.monthValue.toString()

        if (month.length < 2){
            month = "0${month}"
        }

        //  오늘 날짜 띄우기
        year_textview_statsfrag.text = "${year}년"
        month_textview_statsfrag.text = "${month}월"



//        weekofMonth(current)


        myPreference = MyPreference(requireContext())
        val roomindex = myPreference.getRoomindex()




        bringData(roomindex, year, month)










        //  날짜 dialog
        date_conslay_statfrag.setOnClickListener {

            val dialog = AlertDialog.Builder(context).create()
            val edialog: LayoutInflater = LayoutInflater.from(context)
            val mView: View = edialog.inflate(R.layout.dialog_datepicker, null)

            val year: NumberPicker = mView.findViewById(R.id.yearpicker_datepicker)
            val month: NumberPicker = mView.findViewById(R.id.monthpicker_datepicker)

            //  순환 안되게 막기
            year.wrapSelectorWheel = false

            //  editText 설정 해제
            year.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

            month.wrapSelectorWheel = false
            month.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS


            val cancel: Button = mView.findViewById(R.id.cancel_button_datepicker)
            val save: Button = mView.findViewById(R.id.save_button_datepicker)

            year.minValue = 2020
            year.maxValue = 2020

            year.setDisplayedValues(arrayOf("2020년"))


            month.minValue = 1
            month.maxValue = 12

            month.setDisplayedValues(
                arrayOf(
                    "1월",
                    "2월",
                    "3월",
                    "4월",
                    "5월",
                    "6월",
                    "7월",
                    "8월",
                    "9월",
                    "10월",
                    "11월",
                    "12월"
                )
            )


            dialog.setView(mView)
            dialog.create()
            dialog.show()


            //  취소
            cancel.setOnClickListener {
                dialog.dismiss()
                dialog.cancel()
            }

            //  완료
            save.setOnClickListener {

                var yearval = year.value.toString()
                var monthval = month.value.toString()

                if (monthval.length < 2){
                    monthval = "0${monthval}"
                }

                year_textview_statsfrag.text = yearval + "년"
                month_textview_statsfrag.text = monthval + "월"

                bringData(roomindex, yearval, monthval)

                dialog.dismiss()
                dialog.cancel()
            }

        }





        //  TODO resize
//
//        //  resize
//        for (i in 0..header.size -1){
//            bodycount += body[i].size
//        }
//
//        var eachLen = header.size * 66 + bodycount * 16 + 50
//
//        val height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, eachLen.toFloat(), resources.displayMetrics).toInt()
//        val params = LinearLayout.LayoutParams(sizewidth, height)
//        expandable_stats.layoutParams = params
//


    }

    fun weekofMonth(current: LocalDateTime) : Int{

        val formatter = DateTimeFormatter.ofPattern("M월 d일")
        val formatted = current.format(formatter)

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


    fun bringData(roomindex: String, year: String, month: String){

        header.clear()
        body.clear()


        //  서버에서 받아온 값 담을 배열 초기화
        var rawStatData : MutableList<StatData> = ArrayList()
        var namelist : MutableList<String> = ArrayList()

        val myJson = JSONObject()
        val requestBody = myJson.toString()

        val login_url = basic_url + "get_stats?roomindex=$roomindex&year=$year&month=$month"

        val testRequest = object : StringRequest(Method.GET, login_url,
            Response.Listener { response ->

                var json_response = JSONObject(response)
                if(json_response["result"].toString() == "1"){

                    var statresult = json_response.getJSONArray("statsinfo")
                    var arraysize = statresult.length()

                    for (i in (0 .. arraysize-1)){

                        var obj = statresult.getJSONObject(i)

                        var content = obj.getString("content")
                        var score = obj.getString("score")
                        var userid = obj.getString("userid")
                        var weekofMonth = obj.getString("weekofMonth")


                        var temp = StatData(
                            "$userid",
                            "$year",
                            "$month",
                            "$weekofMonth",
                            "$content",
                            "$score"
                        )

                        rawStatData.add(temp)

                    }

                    //  week기준 소팅
                    rawStatData.sortBy { data -> data.week }


                    //  이름 모으기
                    for (each in rawStatData){
                        if (each.userid !in namelist){
                            namelist.add(each.userid)
                        }

                    }


                    //  이름별로 스코어 계산
                    for (name in namelist){
                        var score = 0

                        for (eachraw in rawStatData){
                            if (eachraw.userid == name){
                                score += eachraw.score.toInt()
                            }
                        }

                        header.add(StatMembers("$name", "$score"))
                    }


                    //  순위 매기기
                    header.sortByDescending { data -> data.score }

                    for (member in header){

                        var tempbasket: MutableList<StatData> = ArrayList()


                        for (each in rawStatData){
                            if (each.userid == member.name){
                                //  어차피 week으로 소팅되어있음
                                tempbasket.add(StatData("${member.name}", "${each.year}", "${each.month}", "${each.week}", "${each.content}", "${each.score}"))

                            }
                        }

                        body.add(tempbasket)
                    }






                    expandable_stats.setAdapter(StatsAdapter(requireContext(), header, body))



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
