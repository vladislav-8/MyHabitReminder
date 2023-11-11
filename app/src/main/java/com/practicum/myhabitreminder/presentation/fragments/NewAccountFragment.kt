package com.practicum.myhabitreminder.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.practicum.myhabitreminder.R
import com.practicum.myhabitreminder.common.utils.getDetails
import com.practicum.myhabitreminder.common.utils.toUiText
import com.practicum.myhabitreminder.databinding.FragmentNewAccountBinding
import com.practicum.myhabitreminder.presentation.models.AuthFlowScreenState
import com.practicum.myhabitreminder.presentation.viewmodels.RegistrationViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewAccountFragment : Fragment() {

    private var _binding: FragmentNewAccountBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<RegistrationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentNewAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnConfirm.setOnClickListener {
            viewModel.signUpWithEmailAndPassword(
                binding.emailEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )
        }

        viewModel.screenState.observe(viewLifecycleOwner) { state ->
            render(state)
        }
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
                findNavController().navigate(R.id.action_newAccountFragment_to_loginFragment)
                Toast.makeText(
                    requireContext(), "Account is created",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}