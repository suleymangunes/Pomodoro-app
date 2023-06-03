package com.example.pomodoro_app

import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import java.time.Duration

// TODOS
// setTime fonksiyonu ekleyecez
// bu fonksiyon girilen dakikayi saate cevirip guncelleyecek
// ayni zamanda onfinished icerisindeki kisim duzenlenip settime icerisine eklenecek
// her state degistiginden uyari sesi calinsin
// anasayfada tarihe gore toplam calisilan süre ve tarihe gore kac round
// pomodoro yapıldıgı gosteriliri

// advanced
// takvim sayfasi olur
// takvim icerigine gore gelisimini takip eder
// gun icerisinde yaptıgı pomodoroların saat bilgisini de tutar
// kisiye ozel veri isleme tarzi bir grafik sistemi olabilir

// notification bildirim gonderilir

// uygulama arka plana alininca mobil cihazın bildirim kısmında state ve bilgiler gorunur



class Pomodoro : AppCompatActivity() {

//    lateinit var btnStop :Button
    lateinit var barPr :ProgressBar
    lateinit var txtState :TextView
    lateinit var btnBack :ImageButton

    var work = 1
    var workString = "Work"
    val breakStr = "Break"
    val completedStr = "Completed"

    var roundKey = "round"
    var workKey = "work"
    var breakKey = "break"

    var roundDefault = 4
    var workDefault = 25
    var breakDefault = 5


    var state = 1

    // 1 work
    // 0 completed
    // -1 break

    var timer = 1

    var four = 0
    var round = 0

    var countCompleted = 0

    var breakValue = 0


//    var pomodoroKeys = PomodoroKeys()
//    var  = (sharedPrefInfo)

    private lateinit var tvTimer: TextView
    private lateinit var btnStart: Button
    private lateinit var btnStop: Button
    private lateinit var countDownTimer: CountDownTimer

    private var seconds: Long = 0
    private var minutes: Long = 1
    private var hours: Long = 0

    private lateinit var mainLayout: ConstraintLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pomodoro)

        mainLayout = findViewById(R.id.mainLayout)



//        btnStop = findViewById(R.id.btnStop)
        barPr = findViewById(R.id.barPr)
        txtState = findViewById(R.id.txtState)
        btnBack = findViewById(R.id.btnBack)

        tvTimer = findViewById(R.id.tvTimer)
        btnStart = findViewById(R.id.btnStart)
        btnStop = findViewById(R.id.btnStop)


        changeState(state)

        btnBack.setOnClickListener(){
            finish()
        }

        var shared = createShared()

        round = shared.getInt(roundKey, roundDefault)
        work = shared.getInt(workKey, workDefault)
        breakValue = shared.getInt(breakKey, breakDefault)
        timer = work

        minutes = work.toLong()

        btnStart.setOnClickListener {
            startTimer()
        }

        btnStop.setOnClickListener {
            stopTimer()
        }
    }

    fun createEditor(): SharedPreferences.Editor {
        var shared = getSharedPreferences("info", MODE_PRIVATE)
        var editor = shared.edit()
        return editor
    }

    fun createShared(): SharedPreferences {
        var shared = getSharedPreferences("info", MODE_PRIVATE)
        return shared
    }

    private fun startTimer() {
        countDownTimer = object : CountDownTimer((hours * 3600 + minutes * 60 + seconds) * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                if (seconds > 0) {
                    seconds--
                } else {
                    seconds = 59
                    if (minutes > 0) {
                        minutes--
                    } else {
                        minutes = 59
                        if (hours > 0) {
                            hours--
                        }
                    }
                }
                updateTimer()
            }

            override fun onFinish() {
                // TODO bu kisimla ilgilen
                countCompleted++  // TODO four == 0 && else durumlarinda olacak
                four ++
                if(four == 7){
                    four = -1
                    state = state * -1
                    changeState(state)
                    minutes = breakValue.toLong() * 3
                    startTimer()

                } else if(round * 2 == countCompleted){
                    changeState(0)
                } else {
                    state = state * -1
                    changeState(state)
                    if(state == -1){
                        minutes = breakValue.toLong()
                        startTimer()
                    } else {
                        minutes = work.toLong()
                        startTimer()
                    }
                }
            }
        }
        countDownTimer.start()
    }

    private fun stopTimer() {
        countDownTimer.cancel()
    }

    private fun updateTimer() {

        // TODO bu formul
        var progressSecond = 100 * ((timer * 60) - (seconds + minutes * 60 + hours * 360)) / (timer * 60)
        barPr.setProgress(progressSecond.toInt())
        val formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds)
        tvTimer.text = formattedTime

    }

    fun changeState(state:Int){
        if(state == 1){
            mainLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.MintGreen))
            txtState.setText(workString)
        } else if(state == 0){
            mainLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.MoonMist))
            txtState.setText(completedStr)
        } else {
            mainLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.MonaLisa))
            txtState.setText(breakStr)
        }

    }

}