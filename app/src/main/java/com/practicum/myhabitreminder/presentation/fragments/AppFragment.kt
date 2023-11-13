package com.practicum.myhabitreminder.presentation.fragments

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.practicum.myhabitreminder.R
import com.practicum.myhabitreminder.common.diffutil.HabitAdapter
import com.practicum.myhabitreminder.databinding.FragmentAppBinding
import com.practicum.myhabitreminder.domain.models.Habit
import com.practicum.myhabitreminder.presentation.models.HabitState
import com.practicum.myhabitreminder.presentation.models.TimerState
import com.practicum.myhabitreminder.presentation.viewmodels.HabitViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AppFragment : Fragment() {

    private var _binding: FragmentAppBinding? = null
    private val binding get() = _binding!!

    private var timer: CountDownTimer? = null
    var secondsRemaining = 0L
    private var timerState = TimerState.STOPPED

    private val habitsAdapter by lazy {
        HabitAdapter({ showHabits(habit = it) }, { showLongClickOnHabit(habit = it) })
    }
    private val viewModel by viewModel<HabitViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAppBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        initAdapters()
        initObservers()
        initButtons()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    viewModel.logOut()
                    findNavController().popBackStack()
                }
            })
    }

    private fun initAdapters() {
        binding.rvHabits.adapter = habitsAdapter
    }

    private fun initObservers() {
        viewModel.getAllHabits()
        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }
    }

    private fun initListeners() {
        binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.createHabitFragment)
        }
        binding.deleteHabitsButton.setOnClickListener {
            viewModel.deleteAllHabits()
            habitsAdapter.clearHabits()
            setNewTimerLength()
            timer?.cancel()
            clearContent()
        }

    }

    private fun initButtons() {
        with(binding) {
            fabStart.setOnClickListener {
                startTimer()
                timerState = TimerState.RUNNING
                updateButtons()
            }
            fabPause.setOnClickListener {
                timer?.cancel()
                timerState = TimerState.PAUSED
                updateButtons()
            }
            fabStop.setOnClickListener {
                timer?.cancel()
                onTimerFinished()
                viewModel.getAllHabits()
            }
        }
    }

    private fun render(state: HabitState) {
        with(binding) {
            when (state) {
                is HabitState.Content -> {
                    habitsAdapter.clearHabits()
                    habitsAdapter.habits = state.habits as MutableList<Habit>
                    rvHabits.isVisible = true
                    tvEmptyView.isVisible = false
                    deleteHabitsButton.isVisible = true
                    fabStart.isVisible = true
                    fabStop.isVisible = true
                    fabPause.isVisible = true
                    progressCountdown.isVisible = true
                    textViewCountdown.isVisible = true
                }

                is HabitState.Empty -> {
                    rvHabits.isVisible = false
                    tvEmptyView.isVisible = true
                    deleteHabitsButton.isVisible = false
                    fabStart.isVisible = false
                    fabStop.isVisible = false
                    fabPause.isVisible = false
                    progressCountdown.isVisible = false
                    textViewCountdown.isVisible = false
                }
            }
        }
    }

    private fun showLongClickOnHabit(habit: Habit) {
        showConfirmDialog(habit)
    }

    private fun showConfirmDialog(habit: Habit) {
        context?.let { context ->
            MaterialAlertDialogBuilder(requireContext(), R.style.MyDialogTheme).setMessage(
                requireContext().getString(R.string.are_you_sure_to_delete_habit)
            ).setNegativeButton(R.string.no) { dialog, which -> }
                .setPositiveButton(R.string.yes) { dialog, which ->
                    habit.let { habit ->
                        viewModel.deleteHabit(habit)
                        val indexToRemove = habitsAdapter.habits.indexOfFirst { it.id == habit.id }
                        if (indexToRemove != DELETE_HABIT) {
                            indexToRemove.let { habitsAdapter.habits.removeAt(it) }
                            indexToRemove.let { habitsAdapter.notifyItemRemoved(it) }
                            viewModel.getAllHabits()
                        }
                    }
                }.show()
        }
    }

    private fun showHabits(habit: Habit) {
        //
    }

    private fun clearContent() {
        with(binding) {
            rvHabits.isVisible = false
            tvEmptyView.isVisible = true
            deleteHabitsButton.isVisible = false
            fabStart.isVisible = false
            fabStop.isVisible = false
            fabPause.isVisible = false
            progressCountdown.isVisible = false
            textViewCountdown.isVisible = false
        }
    }

    private fun initTimer() {
        timerState = viewModel.getTimerState()

        //we don't want to change the length of the timer which is already running
        //if the length was changed in settings while it was backgrounded
        if (timerState == TimerState.STOPPED) setNewTimerLength()
        else setPreviousTimerLength()

        secondsRemaining =
            if (timerState == TimerState.RUNNING || timerState == TimerState.PAUSED) viewModel.getSecondsRemaining()
            else viewModel.timerLengthSeconds

        //TODO: change secondsRemaining according to where the background timer stopped

        //resume where we left off
        if (timerState == TimerState.RUNNING) startTimer()

        updateButtons()
        updateCountdownUI()
    }

    private fun onTimerFinished() {
        timerState = TimerState.STOPPED
        setPreviousTimerLength()

        for (i in habitsAdapter.habits.indices) {
            habitsAdapter.habits[i].daysCounter++
            viewModel.updateHabit(habitsAdapter.habits[i])
        }

        binding.progressCountdown.progress = 0
        viewModel.setSecondsRemaining(viewModel.timerLengthSeconds)
        secondsRemaining = viewModel.timerLengthSeconds
        updateButtons()
        updateCountdownUI()
    }

    private fun startTimer() {
        timerState = TimerState.RUNNING
        timer = object : CountDownTimer(secondsRemaining * 1000, 1000) {
            override fun onTick(seconds: Long) {
                secondsRemaining = seconds / 1000
                updateCountdownUI()
            }

            override fun onFinish() = onTimerFinished()
        }.start()
    }

    private fun setNewTimerLength() {
        viewModel.setNewTimerLength()
        binding.progressCountdown.max = viewModel.timerLengthSeconds.toInt()
    }

    private fun setPreviousTimerLength() {
        viewModel.setPreviousTimerLength()
        binding.progressCountdown.max = viewModel.timerLengthSeconds.toInt()
    }

    private fun updateCountdownUI() {
        val minutesUntilFinished = secondsRemaining / 60
        val secondsUntilFinished = secondsRemaining - minutesUntilFinished * 60
        val secondsStr = secondsUntilFinished.toString()
        binding.textViewCountdown.text =
            "$minutesUntilFinished:${if (secondsStr.length == 2) secondsStr else "0" + secondsStr}"
        binding.progressCountdown.progress =
            (viewModel.timerLengthSeconds - secondsRemaining).toInt()
    }

    private fun updateButtons() {
        with(binding) {
            when (timerState) {
                TimerState.RUNNING -> {
                    fabStart.isEnabled = false
                    fabPause.isEnabled = true
                    fabStop.isEnabled = true
                }

                TimerState.STOPPED -> {
                    fabStart.isEnabled = true
                    fabPause.isEnabled = false
                    fabStop.isEnabled = false
                }

                TimerState.PAUSED -> {
                    fabStart.isEnabled = true
                    fabPause.isEnabled = false
                    fabStop.isEnabled = true
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        initTimer()
    }

    override fun onPause() {
        super.onPause()

        when (timerState) {
            TimerState.RUNNING -> timer?.cancel()
            TimerState.PAUSED -> Unit
            TimerState.STOPPED -> Unit
        }

        viewModel.setPreviousTimerLengthSeconds(viewModel.timerLengthSeconds)
        viewModel.setSecondsRemaining(secondsRemaining)
        viewModel.setTimerState(timerState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val DELETE_HABIT = -1
    }
}