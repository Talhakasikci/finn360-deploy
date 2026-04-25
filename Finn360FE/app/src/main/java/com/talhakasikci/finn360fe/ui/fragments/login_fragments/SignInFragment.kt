package com.talhakasikci.finn360fe.ui.fragments.login_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import clearErrorOnTextChange
import com.talhakasikci.finn360fe.R
import com.talhakasikci.finn360fe.databinding.FragmentSignInBinding
import com.talhakasikci.finn360fe.models.Auth.LoginRequest
import com.talhakasikci.finn360fe.models.Auth.RegisterRequest
import com.talhakasikci.finn360fe.utils.TokenManager
import com.talhakasikci.finn360fe.utils.isValidEmail
import com.talhakasikci.finn360fe.viewmodel.AuthViewModel


class SignInFragment : Fragment() {
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignInBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupTextWatchers()
        observeViewModel()

        binding.signInButton.setOnClickListener {
            if(!emptyInputController()){
                //giriş işlemleri yapılacak
//                findNavController().navigate(R.id.action_signInFragment_to_main_fragment)

                val request = LoginRequest(
                    email = binding.singInEmailET.text.toString().trim(),
                    password = binding.singInPasswordET.text.toString().trim()
                )
                viewModel.login(request)
//                clearTexts()
            }
            return@setOnClickListener
        }

        //register butonuna tıklanınca register sayfasına git
        binding.registerFragmentButton.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_registerFragment)
            clearTexts()
        }
    }

    private fun observeViewModel() {
        viewModel.loginResult.observe(viewLifecycleOwner) { loginResponse ->
            loginResponse?.let { response ->
                response.token?.let { token  ->
                    TokenManager(requireContext()).saveToken(token)
                }
                findNavController().navigate(R.id.action_signInFragment_to_main_fragment)
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            message?.let {

            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {isLoading ->
            //loading durumunu yönet
        }
    }

    fun emptyInputController(): Boolean {
        val email: String
        val password: String
        val isValidEmail: Boolean

        binding.apply {
            email = singInEmailET.text.toString().trim()
            password = singInPasswordET.text.toString().trim()
            isValidEmail = isValidEmail(email)

            if (email.isEmpty()){
                singInEmailTIL.error = getString(R.string.empty_email_error)
            } else {
                if (!isValidEmail){
                    singInEmailTIL.error = getString(R.string.invalid_email_error)
                } else {
                    singInEmailTIL.error = null
                }
            }

            if (password.isEmpty()){
                singInPasswordTIL.error = getString(R.string.empty_password_error)
            } else {
                singInPasswordTIL.error = null
            }
        }

        return (email.isEmpty() && isValidEmail) || password.isEmpty()
    }

    private fun setupTextWatchers(){
        binding.apply {
            singInEmailTIL.clearErrorOnTextChange()
            singInPasswordTIL.clearErrorOnTextChange()
        }
    }

    private fun clearTexts(){
        binding.singInEmailET.text?.clear()
        binding.singInPasswordET.text?.clear()
    }



}