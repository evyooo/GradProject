package com.example.famapp.settings

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.famapp.LoginRegister.ChooseRoom
import com.example.famapp.R
import kotlinx.android.synthetic.main.activity_settings.*

class Settings : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)


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

            val dialog = AlertDialog.Builder(this)
            val edialog : LayoutInflater = LayoutInflater.from(this)
            val mView : View = edialog.inflate(R.layout.dialog_leave,null)

            val no : Button = mView.findViewById(R.id.no_button_dl)
            val yes : Button = mView.findViewById(R.id.yes_button_dl)

            no.setOnClickListener{
                Log.d("dialogcheck", "no")
            }

            yes.setOnClickListener{
                Log.d("dialogcheck", "yes")
            }

            dialog.setView(mView)
            dialog.create()
            dialog.show()
        }

        //  다른 방 이동
        switch_imageview_settings.setOnClickListener {
            var intent = Intent(this, ChooseRoom::class.java)
            startActivity(intent)
        }

    }
}
