package com.talhakasikci.finn360fe.ui.fragments.login_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import clearErrorOnTextChange
import com.talhakasikci.finn360fe.R
import com.talhakasikci.finn360fe.databinding.FragmentRegisterBinding
import com.talhakasikci.finn360fe.models.Auth.RegisterRequest
import com.talhakasikci.finn360fe.utils.TokenManager
import com.talhakasikci.finn360fe.utils.isValidEmail
import com.talhakasikci.finn360fe.viewmodel.AuthViewModel


class RegisterFragment : Fragment() {
    private var  _binding: FragmentRegisterBinding? = null
    private val binding get () = _binding!!

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupTextWatchers()

        observeViewModel()

        binding.signUpEmailET.onFocusChangeListener = View.OnFocusChangeListener {_, hasFocus ->
            if (hasFocus.toString().isEmpty()){
                if (binding.signUpEmailET.text.toString().trim().isEmpty()){
                    binding.signUpEmailTIL.error = getString(R.string.empty_email_error)
                } else {
                    binding.signUpEmailTIL.error = null
                }
            }
        }
        binding.RegisterButton.setOnClickListener {
            if(!emptyInputController()){
                //request modeli oluştur
                val request = RegisterRequest(
                    email = binding.signUpEmailET.text.toString().trim(),
                    password = binding.signUpPasswordET.text.toString().trim(),
                    name = binding.signUpNameET.text.toString().trim(),
                    surname = binding.signUpSurnameET.text.toString().trim()
                )

                viewModel.register(request)
            }
            return@setOnClickListener
        }


    }

    private fun emptyInputController(): Boolean {
        val email: String
        val password: String
        val name: String
        val surname: String

        val isEmailValid : Boolean

        binding.apply {
            email = signUpEmailET.text.toString().trim()
            password = signUpPasswordET.text.toString().trim()
            name = signUpNameET.text.toString().trim()
            surname = signUpSurnameET.text.toString().trim()
            isEmailValid = isValidEmail(email)

            if (email.isEmpty()) {
                signUpEmailTIL.error = getString(R.string.empty_email_error)
            }else{
                if (!isEmailValid) {
                    signUpEmailTIL.error = getString(R.string.invalid_email_error)
                }
            }
            if (password.isEmpty()) {
                signUpPasswordTIL.error = getString(R.string.empty_password_error)
            }
            if (name.isEmpty()) {
                singUpNameTIL.error = getString(R.string.empty_name_error)
            }
            if (surname.isEmpty()) {
                signUpSurnameTIL.error = getString(R.string.empty_surname_error)
            }
        }

        return (email.isEmpty() && isEmailValid) || password.isEmpty() || name.isEmpty() || surname.isEmpty()
    }

    private fun observeViewModel() {
        viewModel.registerResult.observe(viewLifecycleOwner) {response ->
            response?.let {it ->
                //kayıt başarılı
                Toast.makeText(requireContext(),getString(R.string.register_success_message), Toast.LENGTH_SHORT).show()
                it.token?.let { token ->
                    TokenManager(requireContext()).saveToken(token)
                }

                findNavController().navigate(R.id.action_registerFragment_to_main_fragment)
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            message?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            //process bar koy
        }
    }

    private fun setupTextWatchers() {
        binding.apply {
            signUpEmailTIL.clearErrorOnTextChange()
            signUpPasswordTIL.clearErrorOnTextChange()
            singUpNameTIL.clearErrorOnTextChange()
            signUpSurnameTIL.clearErrorOnTextChange()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    private fun observeViewModel() {
//        viewModel.registerResult.observe(viewLifecycleOwner) {response ->
//            response?.let {it ->
//                //kayıt başarılı
//                Toast.makeText(requireContext(),getString(R.string.register_success_message), Toast.LENGTH_SHORT).show()
//                it.token?.let { token ->
//                    TokenManager(requireContext()).saveToken(token)
//                }
//
//                findNavController().navigate(R.id.action_registerFragment_to_main_fragment)
//            }
//        }
//
//        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
//            message?.let {
//                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
//            }
//        }
//
//        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
//            //process bar koy
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }


}