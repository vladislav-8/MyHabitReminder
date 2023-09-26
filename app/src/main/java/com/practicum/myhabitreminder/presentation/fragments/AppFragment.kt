package com.practicum.myhabitreminder.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.practicum.myhabitreminder.R
import com.practicum.myhabitreminder.common.diffutil.HabitAdapter
import com.practicum.myhabitreminder.databinding.FragmentAppBinding
import com.practicum.myhabitreminder.domain.models.Habit
import com.practicum.myhabitreminder.presentation.models.HabitState
import com.practicum.myhabitreminder.presentation.viewmodels.HabitViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AppFragment : Fragment() {

    private var _binding: FragmentAppBinding? = null
    private val binding get() = _binding!!

    private val habitsAdapter by lazy {
        HabitAdapter({ showHabits(habit = it) }, { showLongClickOnHabit(habit = it) })
    }
    private val viewModel by viewModel<HabitViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAppBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        initAdapters()
        initObservers()
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
            findNavController().navigate(R.id.action_appFragment_to_creatHabitFragment)
        }
        binding.deleteHabitsButton.setOnClickListener {
            viewModel.deleteAllHabits()
            habitsAdapter.clearHabits()
            binding.tvEmptyView.isVisible = true
        }
    }

    private fun render(state: HabitState) {
        when (state) {
            is HabitState.Content -> {
                habitsAdapter.clearHabits()
                habitsAdapter.habits = state.habits as MutableList<Habit>
                binding.rvHabits.isVisible = true
                binding.tvEmptyView.isVisible = false
            }

            is HabitState.Empty -> {
                binding.rvHabits.isVisible = false
                binding.tvEmptyView.isVisible = true
            }
        }
    }

    private fun showLongClickOnHabit(habit: Habit) {
        showConfirmDialog(habit)
    }

    private fun showConfirmDialog(habit: Habit) {
        context?.let { context ->
            MaterialAlertDialogBuilder(requireContext(),  R.style.MyDialogTheme)
                .setMessage(requireContext().getString(R.string.are_you_sure_to_delete_track))
                .setNegativeButton(R.string.no) { dialog, which -> }
                .setPositiveButton(R.string.yes) { dialog, which ->
                    habit.let { habit ->
                        viewModel.deleteHabit(habit)
                    }
                }.show()
        }
    }

    private fun showHabits(habit: Habit) {
        //zdes ya budu pokazivat habits
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}