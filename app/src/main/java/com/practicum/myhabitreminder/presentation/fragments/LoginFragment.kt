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
import com.practicum.myhabitreminder.databinding.FragmentLoginBinding
import com.practicum.myhabitreminder.presentation.models.AuthFlowScreenState
import com.practicum.myhabitreminder.presentation.viewmodels.AuthorizationViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<AuthorizationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginbtn.setOnClickListener {
            viewModel.signInWithEmailAndPassword(
                binding.username.text.toString(),
                binding.password.text.toString()
            )
        }

        binding.createAccountTv.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_newAccountFragment)
        }

        binding.forgotpass.setOnClickListener {
            ResetPasswordFragment().show(childFragmentManager, TAG_RESETPASS)
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
                findNavController().navigate(R.id.action_loginFragment_to_appFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG_RESETPASS = "ResetpassDialog"
    }

}