package com.dexciuq.composition.domain.usecases

import com.dexciuq.composition.domain.entity.Question
import com.dexciuq.composition.domain.repository.GameRepository

class GenerateQuestionUseCase(
    private val gameRepository: GameRepository
) {
    operator fun invoke(
        maxSumValue: Int,
        countOfOptions: Int = COUNT_OF_OPTIONS
    ): Question {
        return gameRepository.generateQuestion(
            maxSumValue = maxSumValue,
            countOfOptions = countOfOptions
        )
    }

    private companion object {
        private const val COUNT_OF_OPTIONS = 6
    }
}