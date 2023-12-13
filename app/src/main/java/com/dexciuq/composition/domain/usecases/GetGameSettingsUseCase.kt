package com.dexciuq.composition.domain.usecases

import com.dexciuq.composition.domain.entity.GameSettings
import com.dexciuq.composition.domain.entity.Level
import com.dexciuq.composition.domain.repository.GameRepository

class GetGameSettingsUseCase(
    private val gameRepository: GameRepository
) {
    operator fun invoke(level: Level): GameSettings {
        return gameRepository.getGameSettings(level = level)
    }
}