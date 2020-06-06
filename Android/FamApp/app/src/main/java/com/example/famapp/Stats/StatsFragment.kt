package com.example.famapp.Stats


import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.NumberPicker
import com.example.famapp.R
import com.example.famapp.StatMembers
import kotlinx.android.synthetic.main.fragment_stats.*




class StatsFragment : Fragment() {

    //  expandableListView (제목 / 내용 : [group][child].plname 형태)
    val header : MutableList<StatMembers> = ArrayList()
    val body : MutableList<MutableList<String>> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stats, container, false)
    }


    override fun onStart() {
        super.onStart()

        for (i in 0..3){
            header.add(StatMembers("", ""))

            var tempbasket : MutableList<String> = ArrayList()
            tempbasket.add("123")
            tempbasket.add("123")
            tempbasket.add("123")

            body.add(tempbasket)
        }


        //  날짜 dialog
        date_conslay_statfrag.setOnClickListener {

            val dialog = AlertDialog.Builder(context).create()
            val edialog : LayoutInflater = LayoutInflater.from(context)
            val mView : View = edialog.inflate(R.layout.dialog_datepicker,null)

            val year : NumberPicker = mView.findViewById(R.id.yearpicker_datepicker)
            val month : NumberPicker = mView.findViewById(R.id.monthpicker_datepicker)

            //  순환 안되게 막기
            year.wrapSelectorWheel = false

            //  editText 설정 해제
            year.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

            month.wrapSelectorWheel = false
            month.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS


            val cancel : Button = mView.findViewById(R.id.cancel_button_datepicker)
            val save : Button = mView.findViewById(R.id.save_button_datepicker)


            //  TODO ..  최대 최소 설정
            year.minValue = 2019
            year.maxValue = 2020

            year.setDisplayedValues(arrayOf("2019년", "2020년"))


            month.minValue = 1
            month.maxValue = 12

            month.setDisplayedValues(arrayOf("1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"))


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

                year_textview_statsfrag.text = (year.value).toString() + "년"
                month_textview_statsfrag.text = (month.value).toString() + "월"


                //  TODO ..  서버에 값 넘겨주고 불러오기


                dialog.dismiss()
                dialog.cancel()
            }

        }




        expandable_stats.setAdapter(StatsAdapter(requireContext(), header, body))

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


}
