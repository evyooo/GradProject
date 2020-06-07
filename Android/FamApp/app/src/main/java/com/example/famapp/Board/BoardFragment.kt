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
import com.example.famapp.R
import kotlinx.android.synthetic.main.fragment_board.*
import java.util.*
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.widget.LinearLayout


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




        //  TODO 임시
        title_textview_board.setOnClickListener {

            val constraintLayout = view!!.findViewById(R.id.stickerarea_constraitlay_board) as ConstraintLayout
            val imageView = ImageView(context)
            imageView.setImageResource(R.drawable.shape_stickerboard)

            //  사이즈임 포지션이아니라
            val params = LinearLayout.LayoutParams(
                500, 500
            )

            imageView.layoutParams = ViewGroup.LayoutParams(params)
            imageView.x = (1..1200).random().toFloat()
            imageView.y = (1..1500).random().toFloat()

            constraintLayout.addView(imageView)

        }


        //  전체 길이 설정해주기
//        stickerarea_constraitlay_board.layoutParams


    }



}
