package com.dexciuq.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.dexciuq.composition.R
import com.dexciuq.composition.databinding.FragmentGameFinishedBinding
import com.dexciuq.composition.domain.entity.GameResult

class GameFinishedFragment : Fragment() {

    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: error("FragmentGameFinishedBinding is null")

    private lateinit var gameResult: GameResult

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    private fun parseArgs() {
        requireArguments().getParcelable<GameResult>(KEY_GAME_RESULT)?.let {
            gameResult = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        bindViews()
    }

    private fun bindViews() {
        with(binding) {
            if (gameResult.winner) {
                emojiResult.setImageResource(R.drawable.ic_smile)
            } else {
                emojiResult.setImageResource(R.drawable.ic_sad)
            }

            tvRequiredAnswers.text = getString(
                R.string.required_score,
                gameResult.gameSettings.minCountOfRightAnswers.toString()
            )
            tvScoreAnswers.text = getString(
                R.string.score_answers,
                gameResult.countOfRightAnswers.toString()
            )

            tvRequiredPercentage.text = getString(
                R.string.required_percentage,
                gameResult.gameSettings.minPercentOfRightAnswers.toString()
            )
            tvScorePercentage.text = getString(
                R.string.score_percentage,
                getPercentOfRightAnswers().toString()
            )
        }
    }

    private fun getPercentOfRightAnswers(): Int = with(gameResult) {
        return if (countOfQuestions == 0) {
            0
        } else {
            ((countOfRightAnswers.toDouble() / countOfQuestions) * 100).toInt()
        }
    }

    private fun setupListeners() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    retryGame()
                }
            }
        )

        binding.buttonRetry.setOnClickListener {
            retryGame()
        }
    }

    private fun retryGame() {
        requireActivity().supportFragmentManager.popBackStack(
            GameFragment.NAME,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {

        private const val KEY_GAME_RESULT = "game_result"

        fun newInstance(gameResult: GameResult): GameFinishedFragment {
            return GameFinishedFragment().apply {
                arguments = bundleOf(
                    KEY_GAME_RESULT to gameResult
                )
            }
        }
    }
}