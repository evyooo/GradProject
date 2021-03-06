package com.example.famapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.FragmentTransaction
import com.example.famapp.Board.BoardFragment
import com.example.famapp.Calendar.CalendarFragment
import com.example.famapp.Global.Companion.density
import com.example.famapp.Home.HomeFragment
import com.example.famapp.Stats.StatsFragment
import com.example.famapp.todo.TodoFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var homeFragment : HomeFragment
    lateinit var calendarFragment: CalendarFragment
    lateinit var boardFragment: BoardFragment
    lateinit var statsFragement: StatsFragment
    lateinit var todoFragment: TodoFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        density = displayMetrics.density


        val bottomNavigation : BottomNavigationView = findViewById(R.id.btmnav_main)

//        bottomNavigation.findViewById<>()

        //  처음 홈 프래그먼트로 띄우기 (디폴트)
        homeFragment = HomeFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.framelayout, homeFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()


        //  bottomNavigation 전환
        bottomNavigation.setOnNavigationItemSelectedListener { item ->

            when (item.itemId){

                R.id.home_menu -> {
                    homeFragment = HomeFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.framelayout, homeFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()


                }

                R.id.calendar_menu -> {
                    calendarFragment = CalendarFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.framelayout, calendarFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }

                R.id.board_menu -> {
                    boardFragment = BoardFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.framelayout, boardFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }

                R.id.todo_menu -> {
                    todoFragment = TodoFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.framelayout, todoFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }

                R.id.stats_menu -> {
                    statsFragement = StatsFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.framelayout, statsFragement)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
            }

            true

        }





    }
}
