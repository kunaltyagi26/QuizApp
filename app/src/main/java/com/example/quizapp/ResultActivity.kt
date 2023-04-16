package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ResultActivity : AppCompatActivity() {

    private var tvUsername: TextView? = null
    private var tvScore: TextView? = null
    private var btnFinish: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        tvUsername = findViewById(R.id.tv_name)
        tvScore = findViewById(R.id.tv_score)
        btnFinish = findViewById(R.id.btn_finish)

        tvUsername?.text = intent.getStringExtra(Constants.userName)

        val correctAnswers = intent.getIntExtra(Constants.correctAnswers, 0)
        val totalQuestions = intent.getIntExtra(Constants.totalQuestions, 0)
        "Your score is $correctAnswers out of $totalQuestions".also { tvScore?.text = it }

        btnFinish?.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}