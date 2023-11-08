package com.practicum.myhabitreminder.presentation.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.practicum.myhabitreminder.R
import com.practicum.myhabitreminder.common.utils.Calculations
import com.practicum.myhabitreminder.databinding.FragmentCreateHabitBinding
import com.practicum.myhabitreminder.domain.models.Habit
import com.practicum.myhabitreminder.presentation.viewmodels.HabitViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Calendar

class CreateHabitFragment : Fragment(),
    DatePickerDialog.OnDateSetListener {

    private var mSelectedCategory: String = "animal"

    private var _binding: FragmentCreateHabitBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<HabitViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateHabitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()

        /*ApiCall().getRandomJoke(mSelectedCategory) { joke ->
            binding.btnPickDate.text = "${joke?.setup}\n${joke?.punchline}"
        }*/ // tut shutki pribautki
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initListeners() {
        binding.btnPickDate.setOnClickListener {
            getDateCalendar()
            DatePickerDialog(requireContext(), this, viewModel.year, viewModel.month, viewModel.day).show()
        }

        binding.btnConfirm.setOnClickListener {
            viewModel.title = binding.etHabitTitle.text.toString()
            viewModel.description = binding.etHabitDescription.text.toString()
            viewModel.timeStamp = viewModel.cleanDate

            if (!(viewModel.title.isEmpty() || viewModel.description.isEmpty() || viewModel.cleanDate.isBlank())) {
                val habit = Habit(0, viewModel.title, viewModel.description, viewModel.timeStamp)
                viewModel.addHabit(habit)
                Toast.makeText(context, R.string.habit_created, Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()

            } else {
                Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
        viewModel.cleanDate = Calculations.cleanDate(day, month, year)
        binding.tvDateSelected.text = String.format(getString(R.string.date), viewModel.cleanDate)
    }

    //get the current date
    private fun getDateCalendar() {
        val cal = Calendar.getInstance()
        viewModel.day = cal.get(Calendar.DAY_OF_MONTH)
        viewModel.month = cal.get(Calendar.MONTH)
        viewModel.year = cal.get(Calendar.YEAR)
    }
}