package com.shawonshagor0.mvvm

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.media.Image
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.BounceInterpolator
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.color.utilities.Score
import kotlin.getValue

class MainActivity : AppCompatActivity() {
    private lateinit var tvTeamOneScore: TextView
    private lateinit var tvTeamTwoScore: TextView
    private lateinit var tvResult: TextView
    private lateinit var tvSet: TextView
    private lateinit var tvTeamOne: TextView
    private lateinit var tvTeamTwo: TextView
    private val scoreViewModel: ScoreViewModel by viewModels()
    private val nameViewModel: NameViewModel by viewModels()

    private lateinit var btnReset: Button
    private lateinit var imgTennisBall: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvTeamOneScore = findViewById(R.id.tvTeamOneScore)
        tvTeamTwoScore = findViewById(R.id.tvTeamTwoScore)
        tvSet = findViewById(R.id.tvSet)
        tvResult = findViewById(R.id.tvResult)
        btnReset = findViewById(R.id.btnReset)
        imgTennisBall = findViewById(R.id.imgTennisBall)
        tvTeamOne = findViewById(R.id.tvTeamOne)
        tvTeamTwo = findViewById(R.id.tvTeamTwo)

        tvTeamOne.text = nameViewModel.name1.value!!.toString()
        tvTeamTwo.text = nameViewModel.name2.value!!.toString()


        findViewById<Button>(R.id.btnTeamOneScore).setOnClickListener {
            scoreViewModel.addScoreToTeam1()
            checkSet()
        }
        findViewById<Button>(R.id.btnTeamTwoScore).setOnClickListener {
            scoreViewModel.addScoreToTeam2()
            checkSet()
        }

        val teamOneScoreObserver = Observer<Int> { newValue ->
            tvTeamOneScore.text = newValue.toString()
        }

        val teamTwoScoreObserver = Observer<Int> { newValue ->
            tvTeamTwoScore.text = newValue.toString()
        }

        scoreViewModel._t1score.observe(this, teamOneScoreObserver)
        scoreViewModel._t2score.observe(this, teamTwoScoreObserver)

        btnReset.setOnClickListener{
            reset()
        }

    }
    private fun checkSet(){
        if(scoreViewModel._t1score.value!! > 9){
            scoreViewModel.addSetToTeam1()
            scoreViewModel.bothZero()
        }
        else if(scoreViewModel._t2score.value!! > 9){
            scoreViewModel.addSetToTeam2()
            scoreViewModel.bothZero()
        }
        tvSet.text = "${scoreViewModel._t1set.value}:${scoreViewModel._t2set.value}"
        checkWinner()
    }

    private fun checkWinner(){
        if(scoreViewModel._t1set.value!! > 2){
            tvResult.setText("Federer Wins")
            btnReset.visibility = View.VISIBLE
        }
        else if(scoreViewModel._t2set.value!! > 2){
            tvResult.setText( "Nadal Wins" )
            btnReset.visibility = View.VISIBLE
        }
    }
    private fun reset(){
        scoreViewModel.bothZero()
        tvSet.text = "0:0"
        btnReset.visibility = View.INVISIBLE
        tvResult.visibility = View.INVISIBLE
    }
    private fun setResultVisible() {
        val animation = ObjectAnimator.ofFloat(tvResult, View.ALPHA, 0f, 1f)
        animation.duration = 1000
        animation.start()
    }
    private fun animateBall(direction: Boolean) {
        imgTennisBall.visibility = View.VISIBLE

        var ballMove = ObjectAnimator.ofFloat(imgTennisBall, View.TRANSLATION_X, 0f, 1000f)
        if(direction){
            ballMove = ObjectAnimator.ofFloat(imgTennisBall, View.TRANSLATION_X, 1000f, 0f)
        }
        val ballRotate = ObjectAnimator.ofFloat(imgTennisBall, View.ROTATION, 0f, 360f)
        val ballFadeIn = ObjectAnimator.ofFloat(imgTennisBall, View.ALPHA, 0f, 0.5f)
        val ballFadeOut = ObjectAnimator.ofFloat(imgTennisBall, View.ALPHA, 0.5f, 0f)
        ballMove.duration = 300
//        ballMove.interpolator = BounceInterpolator
        ballRotate.duration = 300
        ballFadeIn.duration = 150
        ballFadeOut.duration = 150


        val set = AnimatorSet()
        val alpha = AnimatorSet()
        alpha.playSequentially(ballFadeIn,ballFadeOut)
        set.playTogether(ballMove, ballRotate)
        ObjectAnimator.ofFloat(imgTennisBall, View.TRANSLATION_X, 0f, 1000f)
//        animator.duration = 300
//        animator.repeatCount=2
//        animator.repeatMode = ObjectAnimator.REVERSE
        if(direction)
            ballMove.repeatMode = ObjectAnimator.REVERSE
        set.start()
        alpha.start()
    }
    private fun setBtnVisible() {
        val animation = AlphaAnimation(0f, 1f)
        animation.duration = 1000
        btnReset.visibility = View.INVISIBLE
        btnReset.startAnimation(animation)
    }
}