package com.practicum.myhabitreminder.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.practicum.myhabitreminder.R
import com.practicum.myhabitreminder.databinding.FragmentLoginBinding
import com.practicum.myhabitreminder.presentation.viewmodels.HabitViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<HabitViewModel>()

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
            findNavController().navigate(R.id.action_loginFragment_to_appFragment)
        }
        binding.createAccountTv.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_newAccountFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}