package com.dexciuq.composition.domain.repository

import com.dexciuq.composition.domain.entity.GameSettings
import com.dexciuq.composition.domain.entity.Level
import com.dexciuq.composition.domain.entity.Question

interface GameRepository {
    fun generateQuestion(maxSumValue: Int, countOfOptions: Int): Question
    fun getGameSettings(level: Level): GameSettings
}