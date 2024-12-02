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
    private val scoreViewModel: ScoreViewModel by viewModels()

    private lateinit var btnReset: Button
    private lateinit var imgTennisBall: ImageView

    private var setTeamOne = 0
    private var setTeamTwo = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvTeamOneScore = findViewById(R.id.tvTeamOneScore)
        tvTeamTwoScore = findViewById(R.id.tvTeamTwoScore)
        tvSet = findViewById(R.id.tvSet)
        tvResult = findViewById(R.id.tvResult)
        btnReset = findViewById(R.id.btnReset)
        imgTennisBall = findViewById(R.id.imgTennisBall)


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
        if(tvTeamOneScore.text.toString().toInt() > 9){
            setIncrease(1)
            scoreViewModel.bothZero()
        }
        else if(tvTeamTwoScore.text.toString().toInt() > 9){
            setIncrease(2)
            scoreViewModel.bothZero()
        }
    }
    private fun setIncrease(score:Int){
        if(score == 1){
            scoreViewModel.addSetToTeam1()
//            animateBall(false)
        }
        else{
            scoreViewModel.addSetToTeam2()
//            animateBall(true)
        }

        val teamOneSetObserver = Observer<Int> { newValue ->
            setTeamOne = newValue
        }

        val teamTwoSetObserver = Observer<Int> { newValue ->
            setTeamTwo = newValue
        }

        scoreViewModel._t1set.observe(this, teamOneSetObserver)
        scoreViewModel._t2set.observe(this, teamTwoSetObserver)
//        setTeamOne = scoreViewModel._t1set.value!!
//        setTeamTwo = scoreViewModel._t2set.value!!
        tvSet.text = "$setTeamOne:$setTeamTwo"
        checkWinner()

    }
    private fun checkWinner(){
        if(setTeamOne > 2){
            tvResult.setText("Federer Wins")
//            setResultVisible()
//            setBtnVisible()
            btnReset.visibility = View.VISIBLE
        }
        else if(setTeamTwo > 2){
            tvResult.setText( "Nadal Wins" )
//            setResultVisible()
//            setBtnVisible()
            btnReset.visibility = View.VISIBLE

        }
    }
    private fun reset(){
        scoreViewModel.bothZero()
//        setTeamOne = 0
//        setTeamTwo = 0
        tvSet.text = "0:0"
        btnReset.visibility = View.INVISIBLE
        tvResult.visibility = View.INVISIBLE
    }
    private fun setResultVisible() {
//        val animation = ObjectAnimator.ofFloat(tvResult, View.ALPHA, 0f, 1f)
//        animation.duration = 1000
//        animation.start()
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
//        val animation = AlphaAnimation(0f, 1f)
//        animation.duration = 1000
//        btnReset.visibility = View.INVISIBLE
//        btnReset.startAnimation(animation)
    }
}