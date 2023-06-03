package com.example.pomodoro_app

import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import androidx.appcompat.app.AppCompatActivity

class CreateSharedPref : AppCompatActivity(){

    fun createShared(): SharedPreferences {
        var shared = getSharedPreferences("info", MODE_PRIVATE)
        return shared
    }

    fun createEditor():Editor{
        var shared = getSharedPreferences("info", MODE_PRIVATE)
        var editor = shared.edit()
        return editor
    }
}