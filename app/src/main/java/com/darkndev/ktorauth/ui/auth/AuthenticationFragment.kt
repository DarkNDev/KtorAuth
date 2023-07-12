package com.darkndev.ktorauth.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.darkndev.ktorauth.api.AuthResult
import com.darkndev.ktorauth.databinding.FragmentAuthenticationBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthenticationFragment : Fragment() {

    private var _binding: FragmentAuthenticationBinding? = null
    private val binding get() = _binding!!

    //comes from navigation dependency
    private val viewModel: AuthenticationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthenticationBinding.inflate(inflater, container, false)

        binding.apply {
            signUpUsername.doAfterTextChanged {
                viewModel.signUpUsername = it.toString()
            }
            signUpPassword.doAfterTextChanged {
                viewModel.signUpPassword = it.toString()
            }
            signInUsername.doAfterTextChanged {
                viewModel.signInUsername = it.toString()
            }

            signInPassword.doAfterTextChanged {
                viewModel.signInPassword = it.toString()
            }

            signUp.setOnClickListener {
                viewModel.signUpClicked()
            }
            signIn.setOnClickListener {
                viewModel.signInClicked()
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.status.collectLatest {
                    when (it) {
                        true -> progress.show()
                        false -> progress.hide()
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.authResults.collectLatest {
                when (it) {
                    is AuthResult.Authorized -> {
                        val action =
                            AuthenticationFragmentDirections.actionAuthenticationFragmentToContentFragment(it.data!!)
                        findNavController().navigate(action)
                    }

                    is AuthResult.Unauthorized -> {
                        it.data?.let { message ->
                            Snackbar.make(
                                binding.root,
                                message,
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }

                    is AuthResult.UnknownError -> {
                        it.data?.let { message ->
                            Snackbar.make(
                                binding.root,
                                message,
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}