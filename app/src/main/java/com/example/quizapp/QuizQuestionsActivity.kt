package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView

class QuizQuestionsActivity : AppCompatActivity() {
    private var tvQuestion: TextView? = null
    private var ivImage: ImageView? = null
    private var progressBar: ProgressBar? = null
    private var tvProgress: TextView? = null

    private var tvOptionOne: TextView? = null
    private var tvOptionTwo: TextView? = null
    private var tvOptionThree: TextView? = null
    private var tvOptionFour: TextView? = null

    private var btnSubmit: Button? = null

    private var currentQuestion: Int = 0
    private var selectedOption: Int = 0
    private var correctAnswers: Int = 0
    private var userName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        userName = intent.getStringExtra(Constants.userName)

        tvQuestion = findViewById(R.id.tvQuestion)
        ivImage = findViewById(R.id.iv_image)
        progressBar = findViewById(R.id.progressBar)
        tvProgress = findViewById(R.id.tv_progress)

        tvOptionOne = findViewById(R.id.tv_option1)
        tvOptionTwo = findViewById(R.id.tv_option2)
        tvOptionThree = findViewById(R.id.tv_option3)
        tvOptionFour = findViewById(R.id.tv_option4)

        btnSubmit = findViewById(R.id.btn_submit)

        val questionsList = Constants.getQuestions()
        Log.i("", "Questions list size is: ${questionsList.size}")

        setQuestion(questionsList)

        tvOptionOne?.setOnClickListener {
            handleOptionClick(1)
        }

        tvOptionTwo?.setOnClickListener {
            handleOptionClick(2)
        }

        tvOptionThree?.setOnClickListener {
            handleOptionClick(3)
        }

        tvOptionFour?.setOnClickListener {
            handleOptionClick(4)
        }

        btnSubmit?.setOnClickListener {
            btnSubmit?.isEnabled = false
            if (selectedOption != 0) {
                answerView(questionsList[currentQuestion].correctAnswer, R.drawable.correct_option_bg)

                if (selectedOption != questionsList[currentQuestion].correctAnswer) {
                    answerView(selectedOption, R.drawable.incorrect_option_bg)
                } else {
                    correctAnswers += 1
                }

                Handler(Looper.getMainLooper()).postDelayed({
                    currentQuestion += 1
                    if (currentQuestion < questionsList.size) {
                        setQuestion(questionsList)
                        resetOptionState()
                        selectedOption = 0
                    } else {
                        val intent = Intent(this, ResultActivity::class.java)
                        intent.putExtra(Constants.userName, userName)
                        intent.putExtra(Constants.totalQuestions, questionsList.size)
                        intent.putExtra(Constants.correctAnswers, correctAnswers)
                        startActivity(intent)
                        finish()
                    }

                }, 2000)
            }
        }
    }

    private fun setQuestion(questions: List<Question>) {
        tvQuestion?.text = questions[currentQuestion].question
        ivImage?.setImageResource(questions[currentQuestion].image)
        progressBar?.progress = currentQuestion + 1
        "${currentQuestion + 1}/${questions.size}".also { tvProgress?.text = it }

        tvOptionOne?.text = questions[currentQuestion].optionOne
        tvOptionTwo?.text = questions[currentQuestion].optionTwo
        tvOptionThree?.text = questions[currentQuestion].optionThree
        tvOptionFour?.text = questions[currentQuestion].optionFour

        if (currentQuestion == questions.size - 1) {
            btnSubmit?.text = getString(R.string.finish)
        } else {
            btnSubmit?.text = getString(R.string.submit)
        }
        btnSubmit?.isEnabled = true
    }

    private fun resetOptionState() {
        tvOptionOne?.setBackgroundResource(R.drawable.default_option_border_bg)
        tvOptionTwo?.setBackgroundResource(R.drawable.default_option_border_bg)
        tvOptionThree?.setBackgroundResource(R.drawable.default_option_border_bg)
        tvOptionFour?.setBackgroundResource(R.drawable.default_option_border_bg)
    }

    private fun handleOptionClick(selectedOption: Int) {
        this.selectedOption = selectedOption
        resetOptionState()
        answerView(selectedOption, R.drawable.selected_option_border_bg)
    }

    private fun answerView(answer: Int, drawableView: Int) {
        when(answer) {
            1-> tvOptionOne?.setBackgroundResource(drawableView)
            2-> tvOptionTwo?.setBackgroundResource(drawableView)
            3-> tvOptionThree?.setBackgroundResource(drawableView)
            4-> tvOptionFour?.setBackgroundResource(drawableView)
        }
    }
}