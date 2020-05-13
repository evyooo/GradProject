package com.example.famapp


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onStart() {
        super.onStart()

        //  처음엔 접어두기
        var flag = 1
        members_conslay_main.visibility = View.GONE

        //  접었다 펴기
        membercount_textview_main.setOnClickListener {
            if (flag == 0){
                flag = 1
                members_conslay_main.visibility = View.GONE
            }
            else{
                flag = 0
                members_conslay_main.visibility = View.VISIBLE
            }
        }

    }


}
