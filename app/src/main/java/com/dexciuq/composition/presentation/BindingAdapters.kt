package com.dexciuq.composition.presentation

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.dexciuq.composition.R
import com.dexciuq.composition.domain.entity.GameResult

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