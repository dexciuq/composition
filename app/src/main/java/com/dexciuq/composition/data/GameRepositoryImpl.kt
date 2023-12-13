package com.dexciuq.composition.data

import com.dexciuq.composition.domain.entity.GameSettings
import com.dexciuq.composition.domain.entity.Level
import com.dexciuq.composition.domain.entity.Question
import com.dexciuq.composition.domain.repository.GameRepository
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

object GameRepositoryImpl : GameRepository {

    private const val MIN_SUM_VALUE = 2
    private const val MIN_VISIBLE_NUMBER = 1

    override fun generateQuestion(maxSumValue: Int, countOfOptions: Int): Question {
        val sum = Random.nextInt(from = MIN_SUM_VALUE, until = maxSumValue + 1)
        val visibleNumber = sum - Random.nextInt(from = MIN_VISIBLE_NUMBER, until = sum)
        val rightAnswer = sum - visibleNumber
        val options = mutableSetOf(rightAnswer)

        val from = max(rightAnswer - countOfOptions, MIN_VISIBLE_NUMBER)
        val until = min(rightAnswer + countOfOptions, maxSumValue)

        while (options.size < countOfOptions) {
            options.add(Random.nextInt(from = from, until = until))
        }

        return Question(
            sum = sum,
            visibleNumber = visibleNumber,
            options = options.toList(),
        )
    }

    override fun getGameSettings(level: Level): GameSettings {
        return when (level) {
            Level.TEST -> {
                GameSettings(
                    maxSumValue = 10,
                    minCountOfRightAnswers = 3,
                    minPercentOfRightAnswers = 50,
                    gameTimeInSeconds = 10
                )
            }

            Level.EASY -> {
                GameSettings(
                    maxSumValue = 10,
                    minCountOfRightAnswers = 10,
                    minPercentOfRightAnswers = 70,
                    gameTimeInSeconds = 60
                )
            }

            Level.MEDIUM -> {
                GameSettings(
                    maxSumValue = 20,
                    minCountOfRightAnswers = 20,
                    minPercentOfRightAnswers = 80,
                    gameTimeInSeconds = 40
                )
            }

            Level.HARD -> {
                GameSettings(
                    maxSumValue = 30,
                    minCountOfRightAnswers = 30,
                    minPercentOfRightAnswers = 90,
                    gameTimeInSeconds = 40
                )
            }
        }
    }
}