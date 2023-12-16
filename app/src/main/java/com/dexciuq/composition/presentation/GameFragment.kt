package com.dexciuq.composition.presentation

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dexciuq.composition.databinding.FragmentGameBinding
import com.dexciuq.composition.domain.entity.GameResult

class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: error("FragmentGameBinding is null")

    private val args: GameFragmentArgs by navArgs()

    private val viewModelFactory by lazy {
        GameViewModelFactory(args.level, requireActivity().application)
    }

    private val viewModel: GameViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[GameViewModel::class.java]
    }

    private val tvOptions by lazy {
        listOf(
            binding.tvOption1,
            binding.tvOption2,
            binding.tvOption3,
            binding.tvOption4,
            binding.tvOption5,
            binding.tvOption6
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        setListeners()
    }

    private fun setListeners() {
        tvOptions.forEachIndexed { _, textView ->
            textView.setOnClickListener {
                viewModel.chooseAnswer(textView.text.toString().toInt())
            }
        }
    }

    private fun setObservers() {
        with(binding) {
            viewModel.question.observe(viewLifecycleOwner) {
                tvSum.text = it.sum.toString()
                tvLeftNumber.text = it.visibleNumber.toString()
                tvOptions.forEachIndexed { i, textView ->
                    textView.text = it.options[i].toString()
                }
            }

            viewModel.percentOfRightAnswers.observe(viewLifecycleOwner) {
                progressBar.setProgress(it, true)
            }

            viewModel.progressAnswers.observe(viewLifecycleOwner) {
                tvAnswersProgress.text = it
            }

            viewModel.enoughCountOfRightAnswers.observe(viewLifecycleOwner) {
                val colorId = if (it) {
                    android.R.color.holo_green_light
                } else {
                    android.R.color.holo_red_light
                }
                val color = ContextCompat.getColor(requireContext(), colorId)
                tvAnswersProgress.setTextColor(color)
            }

            viewModel.enoughPercentOfRightAnswers.observe(viewLifecycleOwner) {
                val colorId = if (it) {
                    android.R.color.holo_green_light
                } else {
                    android.R.color.holo_red_light
                }
                val color = ContextCompat.getColor(requireContext(), colorId)
                progressBar.progressTintList = ColorStateList.valueOf(color)
            }

            viewModel.formattedTime.observe(viewLifecycleOwner) {
                tvTimer.text = it
            }

            viewModel.minPercent.observe(viewLifecycleOwner) {
                progressBar.secondaryProgress = it
            }

            viewModel.gameResult.observe(viewLifecycleOwner) {
                launchGameFinishedFragment(it)
            }
        }
    }

    private fun launchGameFinishedFragment(gameResult: GameResult) {
        findNavController().navigate(
            GameFragmentDirections.actionGameFragmentToGameFinishedFragment(gameResult)
        )
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}