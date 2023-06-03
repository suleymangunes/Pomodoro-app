package com.example.pomodoro_app

import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    lateinit var btnStart: Button
    lateinit var txtRound: EditText
    lateinit var txtWork: EditText
    lateinit var txtBreak: EditText

    var roundKey = "round"
    var workKey = "work"
    var breakKey = "break"

    var roundDefault = 4
    var workDefault = 25
    var breakDefault = 5

    var sharedPrefInfo = "info"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 4, 25, 5

        btnStart = findViewById(R.id.btnStart)
        txtRound = findViewById(R.id.txtRound)
        txtWork = findViewById(R.id.txtWork)
        txtBreak = findViewById(R.id.txtBreak)


        var editor = createEditor()
//        var shared = createShared()


        btnStart.setOnClickListener(){

            var intent = Intent(this, Pomodoro::class.java)
            var round = txtRound.text.toString().toIntOrNull()
            var work = txtWork.text.toString().toIntOrNull()
            var breakTime = txtBreak.text.toString().toIntOrNull()


            if(round != null && round > 1){
                editor.putInt(roundKey, round)
            }
            if(work != null && work > 15){
                editor.putInt(workKey, work)
            }
            if(breakTime != null && breakTime > 2){
                editor.putInt(breakKey, breakTime)
            }
            editor.commit()
            startActivity(intent)
        }
    }

    fun createEditor():Editor{
        var shared = getSharedPreferences("info", MODE_PRIVATE)
        var editor = shared.edit()
        return editor
    }

//    fun createShared(): SharedPreferences {
//        var shared = getSharedPreferences("info", MODE_PRIVATE)
//        return shared
//    }
}

