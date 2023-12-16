package com.dexciuq.composition.presentation

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.dexciuq.composition.R
import com.dexciuq.composition.domain.entity.GameResult

interface OnOptionClickListener {
    fun onOptionClick(option: Int)
}

@BindingAdapter("resultEmoji")
fun bindResultEmoji(imageView: ImageView, winner: Boolean) {
    if (winner) {
        imageView.setImageResource(R.drawable.ic_smile)
    } else {
        imageView.setImageResource(R.drawable.ic_sad)
    }
}

@BindingAdapter("requiredAnswers")
fun bindRequiredAnswers(textView: TextView, minCountOfRightAnswers: Int) {
    textView.text = textView.context.getString(
        R.string.required_score,
        minCountOfRightAnswers.toString()
    )
}

@BindingAdapter("scoreAnswers")
fun bindScoreAnswers(textView: TextView, scoreAnswers: Int) {
    textView.text = textView.context.getString(
        R.string.score_answers,
        scoreAnswers.toString()
    )
}

@BindingAdapter("requiredPercent")
fun bindRequiredPercent(textView: TextView, minPercentOfRightAnswers: Int) {
    textView.text = textView.context.getString(
        R.string.required_percentage,
        minPercentOfRightAnswers.toString()
    )
}

@BindingAdapter("scorePercent")
fun bindScorePercent(textView: TextView, gameResult: GameResult) {
    textView.text = textView.context.getString(
        R.string.score_percentage,
        getPercentOfRightAnswers(gameResult).toString()
    )
}

private fun getPercentOfRightAnswers(gameResult: GameResult): Int = with(gameResult) {
    return if (countOfQuestions == 0) {
        0
    } else {
        ((countOfRightAnswers.toDouble() / countOfQuestions) * 100).toInt()
    }
}

@BindingAdapter("enoughCount")
fun bindEnoughCount(textView: TextView, enough: Boolean) {
    textView.setTextColor(getColorByState(textView.context, enough))
}

@BindingAdapter("enoughPercent")
fun bindEnoughPercent(progressBar: ProgressBar, enough: Boolean) {
    val color = getColorByState(progressBar.context, enough)
    progressBar.progressTintList = ColorStateList.valueOf(color)
}

private fun getColorByState(context: Context, goodState: Boolean): Int {
    val colorResId = if (goodState) {
        android.R.color.holo_green_light
    } else {
        android.R.color.holo_red_light
    }
    return ContextCompat.getColor(context, colorResId)
}

@BindingAdapter("numberAsText")
fun bindNumberAsText(textView: TextView, number: Int) {
    textView.text = number.toString()
}

@BindingAdapter("onOptionClickListener")
fun bindOnOptionClickListener(textView: TextView, clickListener: OnOptionClickListener) {
    textView.setOnClickListener {
        clickListener.onOptionClick(textView.text.toString().toInt())
    }
}