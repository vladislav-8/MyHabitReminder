package com.practicum.myhabitreminder.presentation.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isNotEmpty
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.practicum.myhabitreminder.R
import com.practicum.myhabitreminder.common.utils.Calculations
import com.practicum.myhabitreminder.data.network.ApiCall
import com.practicum.myhabitreminder.databinding.FragmentCreateHabitBinding
import com.practicum.myhabitreminder.domain.models.Habit
import com.practicum.myhabitreminder.presentation.viewmodels.HabitViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Calendar

class CreateHabitFragment : Fragment(), TimePickerDialog.OnTimeSetListener,
    DatePickerDialog.OnDateSetListener {

    private var title = ""
    private var description = ""
    private var timeStamp = ""

    private var day = 0
    private var month = 0
    private var year = 0
    private var hour = 0
    private var minute = 0
    private var cleanDate = ""
    private var cleanTime = ""

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
            DatePickerDialog(requireContext(), this, year, month, day).show()
        }
        binding.btnPickTime.setOnClickListener {
            getTimeCalendar()
            TimePickerDialog(context, this, hour, minute, true).show()
        }
        binding.btnConfirm.setOnClickListener {
            title = binding.etHabitTitle.text.toString()
            description = binding.etHabitDescription.text.toString()
            timeStamp = "$cleanDate $cleanTime"

            if (!(title.isEmpty() || description.isEmpty() || cleanDate.isBlank() || cleanTime.isBlank())) {
                Log.d("TAG", timeStamp)

                val habit = Habit(0, title, description, timeStamp)
                viewModel.addHabit(habit)
                Toast.makeText(context, R.string.habit_created, Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()

            } else {
                Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onTimeSet(p0: TimePicker?, hour: Int, minute: Int) {
        cleanTime = Calculations.cleanTime(hour, minute)
        binding.tvTimeSelected.text = String.format(getString(R.string.time), cleanTime)
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
        cleanDate = Calculations.cleanDate(day, month, year)
        binding.tvDateSelected.text = String.format(getString(R.string.date), cleanDate)
    }

    //get the current time
    private fun getTimeCalendar() {
        val cal = Calendar.getInstance()
        hour = cal.get(Calendar.HOUR_OF_DAY)
        minute = cal.get(Calendar.MINUTE)
    }

    //get the current date
    private fun getDateCalendar() {
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
    }
}