package com.example.famapp


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.fragment_board.*




class BoardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_board, container, false)
    }

    override fun onResume() {
        super.onResume()

        add_imageview_board.setOnClickListener {
            activity?.let{
                val intent = Intent(context, Board_write::class.java)
                startActivity(intent)
            }
        }


        //  전체 길이 설정해주기
//        stickerarea_constraitlay_board.layoutParams


        //  서버에서 받아오기 -> 스티커 그리기
        //  TODO 여기 왜 안되는지 몰겠음
        var count = 0
        fixed_textview_board.setOnClickListener {

            count += 1

            Log.d("", "$count")
            var newView: ImageView
            var textView : TextView

            var layout = view!!.findViewById<ConstraintLayout>(R.id.stickerarea_constraitlay_board)

            val params = ConstraintLayout.LayoutParams(200,200)

            newView = ImageView(context)
            newView.layoutParams = params

            newView.x = 300F
            newView.y = 500F
            newView.id = count
            newView.setImageResource(R.drawable.temp_profileimg)

            layout.addView(newView)



        }

    }



}
