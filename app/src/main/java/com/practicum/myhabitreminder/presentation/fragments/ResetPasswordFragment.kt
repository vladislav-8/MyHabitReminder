package com.practicum.myhabitreminder.presentation.fragments

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.practicum.myhabitreminder.common.utils.getDetails
import com.practicum.myhabitreminder.common.utils.toUiText
import com.practicum.myhabitreminder.databinding.FragmentResetPasswordBinding
import com.practicum.myhabitreminder.presentation.models.AuthFlowScreenState
import com.practicum.myhabitreminder.presentation.viewmodels.ResetPassViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ResetPasswordFragment : DialogFragment() {

    private var _binding: FragmentResetPasswordBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<ResetPassViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentResetPasswordBinding.inflate(inflater, container, false)
        dialog?.window?.let {
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            it.requestFeature(Window.FEATURE_NO_TITLE)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.sendNewPassword.setOnClickListener {
            viewModel.sendNewPassword(binding.editTextDialogResetpass.text.toString())
        }

        viewModel.screenState.observe(viewLifecycleOwner) { state ->
            render(state)
        }
    }

    override fun onResume() {
        super.onResume()

        val window = requireDialog().window
        window!!.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        window.setGravity(Gravity.CENTER)
    }

    private fun render(state: AuthFlowScreenState) {
        when (state) {
            is AuthFlowScreenState.Loading -> {}

            is AuthFlowScreenState.Error -> {
                Toast.makeText(
                    requireContext(),
                    state.errorType.getDetails().toUiText(requireContext()),
                    Toast.LENGTH_LONG
                ).show()
            }

            is AuthFlowScreenState.Success -> {
                Toast.makeText(
                    requireContext(),
                    "Email send successfully",
                    Toast.LENGTH_SHORT
                ).show()

                this.dismiss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}