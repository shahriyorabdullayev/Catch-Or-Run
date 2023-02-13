package com.Catch.OrRunOr.fragment

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.Catch.OrRunOr.R
import com.Catch.OrRunOr.databinding.FragmentStartBinding
import com.Catch.OrRunOr.model.Player
import com.Catch.OrRunOr.model.Position
import com.Catch.OrRunOr.utils.*
import com.Catch.OrRunOr.utils.Constants.BEST_SCORE
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlin.math.abs
import kotlin.random.Random


class StartFragment : Fragment(R.layout.fragment_start) {


    private var width: Int = 0
    private var height: Int = 0

    private val binding by viewBinding { FragmentStartBinding.bind(it) }
    private var jobPlayerCreator: Job? = null
    private var jobCheckResult: Job? = null
    private var jobChangeBallPosition: Job? = null
    private var jobCheckPlayerPosition: Job? = null
    private var jobCalculateScore: Job? = null
    private var jobCalculatePlayerDirection: Job? = null

    var changeBallPosition = false

    var ballXDown: Float = 0f
    var ballYDown: Float = 0f

    private lateinit var playerList: ArrayList<Player>

    private lateinit var leftPlayersList: ArrayList<Player>
    private lateinit var rightPlayersList: ArrayList<Player>

    var index = 0
    var leftOrRight = false

    private lateinit var imageView: ImageView
    private lateinit var leftAnimationX: ValueAnimator
    private lateinit var leftAnimationY: ValueAnimator
    private lateinit var rightAnimationX: ValueAnimator
    private lateinit var rightAnimationY: ValueAnimator
    private lateinit var leftImageViewList: ArrayList<ImageView>
    private lateinit var rightImageViewList: ArrayList<ImageView>


    private lateinit var scoreSet: HashSet<ImageView>
    private lateinit var gameOverSet: HashSet<Boolean>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requireActivity().window.statusBarColor = requireActivity().getColor(R.color.game)
            }
        }
        leftAnimationX = ValueAnimator()
        leftAnimationY = ValueAnimator()
        rightAnimationX = ValueAnimator()
        rightAnimationY = ValueAnimator()
        scoreSet = HashSet()
        gameOverSet = HashSet()

        leftImageViewList = ArrayList()
        rightImageViewList = ArrayList()

        leftPlayersList = ArrayList()
        rightPlayersList = ArrayList()

        playerList = ArrayList()

        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
        width = displayMetrics.widthPixels
        height = displayMetrics.heightPixels

        setScoreTextSpan(binding.tvScore, "${scoreSet.size} ")

        setScoreTextSpan(binding.tvScoreText, "${getString(R.string.score)} ")


        manageBall()


    }


    private fun setScoreTextSpan(textView: TextView, text: String) {
        val outlineSpan = OutlineSpan(
            strokeColor = Color.WHITE,
            strokeWidth = 10F
        )
        val spannable = SpannableString(text)
        spannable.setSpan(outlineSpan, 0, text.length - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        textView.text = spannable
    }

    private fun leftPlayers() {
        imageView = ImageView(requireContext())
        binding.parentLayout.addView(imageView)
        imageView.layoutParams.width = ConstraintLayout.LayoutParams.WRAP_CONTENT
        imageView.layoutParams.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
        imageView.x = 0f
        imageView.y = (height / 2 - Random.nextInt(-400, 800)).toFloat()
        leftImageViewList.add(imageView)
        playerList.add(Player(R.drawable.player_left, Position(0f, 0f)))
        imageView.setImageResource(playerList[index].image!!)
        index++
        startLeftAnimation(imageView)
    }

    private fun rightPlayers() {
        imageView = ImageView(requireContext())
        binding.parentLayout.addView(imageView)
        imageView.layoutParams.width = ConstraintLayout.LayoutParams.WRAP_CONTENT
        imageView.layoutParams.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
        imageView.x = (width - 100).toFloat()
        imageView.y = (height / 2 - Random.nextInt(-400, 800)).toFloat()
        rightImageViewList.add(imageView)
        playerList.add(Player(R.drawable.player_right, Position(0f, 0f)))
        imageView.setImageResource(playerList[index].image!!)
        index++
        startRightAnimation(imageView)
    }

    private fun startLeftAnimation(imageView: ImageView) {
        leftAnimationX = ValueAnimator.ofFloat(imageView.x, binding.imgBall.absX())
        leftAnimationY = ValueAnimator.ofFloat(imageView.y, binding.imgBall.absY() - 100f)
        leftAnimationX.addUpdateListener {
            val va = it.animatedValue as Float
            imageView.x = va
        }
        leftAnimationY.addUpdateListener {
            val va = it.animatedValue as Float
            imageView.y = va
        }
        leftAnimationX.apply {
            duration = 2500
            interpolator = LinearInterpolator()
            start()
        }
        leftAnimationY.apply {
            duration = 2500
            interpolator = LinearInterpolator()
            start()
        }
    }

    private fun startRightAnimation(imageView: ImageView) {
        rightAnimationX = ValueAnimator.ofFloat(imageView.x, binding.imgBall.absX())
        rightAnimationY = ValueAnimator.ofFloat(imageView.y, binding.imgBall.absY() - 100f)
        rightAnimationX.addUpdateListener {
            val va = it.animatedValue as Float
            imageView.x = va
        }
        rightAnimationY.addUpdateListener {
            val va = it.animatedValue as Float
            imageView.y = va
        }
        rightAnimationX.apply {
            duration = 2500
            interpolator = LinearInterpolator()
            start()
        }
        rightAnimationY.apply {
            duration = 2500
            interpolator = LinearInterpolator()
            start()
        }
    }

    private fun jobTimer() {
        jobPlayerCreator = viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            while (isActive) {
                delay(1800)
                leftOrRight = if (leftOrRight) {
                    leftPlayers()
                    false
                } else {
                    rightPlayers()
                    true
                }

            }
        }

        jobChangeBallPosition = viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            while (isActive) {
                delay(100)
                if (changeBallPosition) {
                    if (leftImageViewList.isNotEmpty()) {
                        for (i in 0 until leftImageViewList.size) {
                            startLeftAnimation(leftImageViewList[i])
                        }
                    }
                    if (rightImageViewList.isNotEmpty()) {
                        for (i in 0 until rightImageViewList.size) {
                            startRightAnimation(rightImageViewList[i])
                        }
                    }
                    changeBallPosition = false
                }
            }
        }

        jobCheckResult = viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            while (isActive) {
                delay(500)
                for (i in 0 until leftImageViewList.size) {
                    if (abs(leftImageViewList[i].absX() - binding.imgBall.absX()) < 100 && abs(
                            leftImageViewList[i].absY() - binding.imgBall.absY()) < 100
                    ) {
                        gameOverSet.add(true)

                    }
                }
                for (i in 0 until rightImageViewList.size) {
                    if (abs(rightImageViewList[i].absX() - binding.imgBall.absX()) < 100 && abs(
                            rightImageViewList[i].absY() - binding.imgBall.absY()) < 100
                    ) {
                        gameOverSet.add(true)
                    }
                }
            }
        }


        jobCheckPlayerPosition = viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            while (isActive) {
                delay(100)
                if (rightImageViewList.isNotEmpty() && leftImageViewList.isNotEmpty()) {
                    for (i in 0 until leftImageViewList.size) {
                        if (abs(rightImageViewList[i].absX() - leftImageViewList[i].absX()) < 150 && abs(
                                rightImageViewList[i].absY() - leftImageViewList[i].absY()) < 150
                        ) {
                            leftImageViewList[i].gone()
                            scoreSet.add(leftImageViewList[i])
                            rightImageViewList[i].apply {
                                setImageResource(R.drawable.ic_bang)
                                delay(150)
                                gone()
                            }
                        }
                    }
                }
            }
        }


        jobCalculateScore = viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            while (isActive) {
                delay(200)
                setScoreTextSpan(binding.tvScore, "${scoreSet.size} ")
                if (gameOverSet.isNotEmpty()) {
                    showResultDialog(scoreSet.size)
                    gameOverSet.clear()
                }
            }
        }

        jobCalculatePlayerDirection = viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            while (isActive) {
                delay(100)
                if (rightImageViewList.isNotEmpty() && leftImageViewList.isNotEmpty()) {
                    for (i in 0 until leftImageViewList.size) {
                        if (leftImageViewList[i].absX() - binding.imgBall.absX() > 0 && leftImageViewList[i].absY() - binding.imgBall.absY() > 0) {
                            leftImageViewList[i].setImageResource(R.drawable.player_right)
                        }
                        if (leftImageViewList[i].absX() - binding.imgBall.absX() < 0 && leftImageViewList[i].absY() - binding.imgBall.absY() < 0) {
                            leftImageViewList[i].setImageResource(R.drawable.player_left)
                        }

                        if (rightImageViewList[i].absX() - binding.imgBall.absX() > 0 && rightImageViewList[i].absY() - binding.imgBall.absY() > 0) {
                            rightImageViewList[i].setImageResource(R.drawable.player_right)
                        }
                        if (rightImageViewList[i].absX() - binding.imgBall.absX() < 0 && rightImageViewList[i].absY() - binding.imgBall.absY() < 0) {
                            rightImageViewList[i].setImageResource(R.drawable.player_left)
                        }
                    }
                }
            }
        }
    }

    private fun showResultDialog(currentScore: Int) {
        val dialog = Dialog(requireContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen)

        dialog.apply {
            dialog.setContentView(R.layout.dilaog_result)
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)
            jobPlayerCreator?.cancel()
            jobCheckPlayerPosition?.cancel()
            jobCheckResult?.cancel()
            jobChangeBallPosition?.cancel()
            jobCalculateScore?.cancel()
            jobCalculatePlayerDirection?.cancel()
            leftAnimationX.cancel()
            leftAnimationY.cancel()
            rightAnimationX.cancel()
            rightAnimationY.cancel()


            if (getString(BEST_SCORE).toInt() < currentScore) {
                saveString(BEST_SCORE, currentScore.toString())
            }

            setScoreTextSpan(findViewById(R.id.tv_best_score), "${getString(BEST_SCORE)} ")
            setScoreTextSpan(findViewById(R.id.tv_current_score), "$currentScore ")

            setScoreTextSpan(findViewById(R.id.tv_best_score_text),
                "${getString(R.string.best_score)} ")
            setScoreTextSpan(findViewById(R.id.tv_current_score_text),
                "${getString(R.string.current_score)} ")

            findViewById<TextView>(R.id.btn_menu).setOnClickListener {
                jobPlayerCreator?.cancel()
                jobCheckPlayerPosition?.cancel()
                jobCheckResult?.cancel()
                jobChangeBallPosition?.cancel()
                jobCalculateScore?.cancel()
                jobCalculatePlayerDirection?.cancel()
                leftAnimationX.cancel()
                leftAnimationY.cancel()
                rightAnimationX.cancel()
                rightAnimationY.cancel()
                dismiss()
                requireActivity().onBackPressed()

            }



            show()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun manageBall() {
        jobTimer()
        binding.imgBall.setOnTouchListener { view, event ->
            changeBallPosition = true
            when (event?.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    ballXDown = event.x
                    ballYDown = event.y

                }

                MotionEvent.ACTION_MOVE -> {
                    var movedX: Float
                    var movedY: Float

                    movedX = event.x
                    movedY = event.y

                    val distanceX = movedX - ballXDown
                    val distanceY = movedY - ballYDown

                    with(binding) {
                        imgBall.x = imgBall.x + distanceX
                        imgBall.y = imgBall.y + distanceY
                    }
                }
            }
            true
        }
    }

    private fun View.absX(): Float {
        val location = IntArray(2)
        this.getLocationOnScreen(location)
        return location[0].toFloat()
    }

    private fun View.absY(): Float {
        val location = IntArray(2)
        this.getLocationOnScreen(location)
        return location[1].toFloat()
    }

    override fun onDestroy() {
        super.onDestroy()
        jobPlayerCreator?.cancel()
        jobCheckPlayerPosition?.cancel()
        jobCheckResult?.cancel()
        jobChangeBallPosition?.cancel()
        jobCalculateScore?.cancel()
        jobCalculatePlayerDirection?.cancel()
        leftAnimationX.cancel()
        leftAnimationY.cancel()
        rightAnimationX.cancel()
        rightAnimationY.cancel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        jobPlayerCreator?.cancel()
        jobCheckPlayerPosition?.cancel()
        jobCheckResult?.cancel()
        jobChangeBallPosition?.cancel()
        jobCalculateScore?.cancel()
        jobCalculatePlayerDirection?.cancel()
        leftAnimationX.cancel()
        leftAnimationY.cancel()
        rightAnimationX.cancel()
        rightAnimationY.cancel()
    }

    override fun onDetach() {
        super.onDetach()
        jobPlayerCreator?.cancel()
        jobCheckPlayerPosition?.cancel()
        jobCheckResult?.cancel()
        jobChangeBallPosition?.cancel()
        jobCalculateScore?.cancel()
        jobCalculatePlayerDirection?.cancel()
        leftAnimationX.cancel()
        leftAnimationY.cancel()
        rightAnimationX.cancel()
        rightAnimationY.cancel()
    }
}

