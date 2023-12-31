package com.practicum.myhabitreminder.presentation.fragments

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
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

const val CHANNEL_ID = "channel id"
const val NOTIFICATION_ID = 101

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

        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notification Title"
            val descriptionText = "Notification Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification() {
        val builder = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notifications)
            .setContentTitle("30 minutes is expired")
            .setContentText("Congratulations! You get one more day to your goal")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(requireContext())) {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            notify(NOTIFICATION_ID, builder.build())
        }
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

        if (secondsRemaining <= 0)
            onTimerFinished()

        //resume where we left off
        else if (timerState == TimerState.RUNNING) startTimer()

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
        sendNotification()
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
        viewModel.setAlarmSetTime(0)

    }

    override fun onPause() {
        super.onPause()

        if (timerState == TimerState.RUNNING) {
            timer?.cancel()
        } else if (timerState == TimerState.PAUSED) {

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