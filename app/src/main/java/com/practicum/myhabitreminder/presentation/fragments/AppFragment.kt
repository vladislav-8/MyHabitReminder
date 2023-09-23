package com.practicum.myhabitreminder.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.practicum.myhabitreminder.R
import com.practicum.myhabitreminder.common.diffutil.HabitAdapter
import com.practicum.myhabitreminder.data.network.ApiCall
import com.practicum.myhabitreminder.databinding.FragmentAppBinding
import com.practicum.myhabitreminder.databinding.FragmentCreateHabitBinding
import com.practicum.myhabitreminder.domain.models.Habit
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

        binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_appFragment_to_creatHabitFragment)
        }
    }

    private fun showLongClickOnHabit(habit: Habit) {
        //zdes ya budu udalyat habits
    }

    private fun showHabits(habit: Habit) {
        //zdes ya budu pokazivat habits
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}