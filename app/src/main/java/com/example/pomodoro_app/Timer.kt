package com.example.pomodoro_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


import android.os.CountDownTimer

import android.widget.Button
import android.widget.TextView

class Timer : AppCompatActivity() {
    private lateinit var tvTimer: TextView
    private lateinit var btnStart: Button
    private lateinit var btnStop: Button
    private lateinit var countDownTimer: CountDownTimer

    private var seconds: Long = 0
    private var minutes: Long = 0
    private var hours: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        tvTimer = findViewById(R.id.tvTimer)
        btnStart = findViewById(R.id.btnStart)
        btnStop = findViewById(R.id.btnStop)

        btnStart.setOnClickListener {
            startTimer()
        }

        btnStop.setOnClickListener {
            stopTimer()
        }
    }

    private fun startTimer() {
        countDownTimer = object : CountDownTimer(Long.MAX_VALUE, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                seconds++
                if (seconds == 60L) {
                    seconds = 0
                    minutes++
                    if (minutes == 60L) {
                        minutes = 0
                        hours++
                    }
                }
                updateTimer()
            }

            override fun onFinish() {
                // Geri sayım tamamlandığında yapılacak işlemler
            }
        }
        countDownTimer.start()
    }

    private fun stopTimer() {
        countDownTimer.cancel()
    }

    private fun updateTimer() {
        val formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds)
        tvTimer.text = formattedTime
    }
}