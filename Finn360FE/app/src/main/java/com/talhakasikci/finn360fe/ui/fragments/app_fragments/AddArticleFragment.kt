package com.talhakasikci.finn360fe.ui.fragments.app_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.talhakasikci.finn360fe.R
import com.talhakasikci.finn360fe.databinding.FragmentAddArticleBinding

class AddArticleFragment : Fragment() {

    private var _binding: FragmentAddArticleBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCategoryDropdown()
        setupClickListeners()
    }

    private fun setupCategoryDropdown() {
        val categories = arrayOf(
            "Hisse Analizi",
            "Kripto Para",
            "Forex",
            "Emtia",
            "Ekonomi Haberleri",
            "Yatırım Stratejileri",
            "Diğer"
        )

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            categories
        )

        binding.categoryAutoComplete.setAdapter(adapter)
    }

    private fun setupClickListeners() {

        binding.cancelButton.setOnClickListener {
            // Geri dön
            findNavController().navigateUp()
        }

        binding.publishButton.setOnClickListener {
            validateAndPublish()
        }
    }

    private fun validateAndPublish() {
        val title = binding.titleEditText.text.toString().trim()
        val category = binding.categoryAutoComplete.text.toString().trim()
        val content = binding.contentEditText.text.toString().trim()

        when {
            title.isEmpty() -> {
                binding.titleInputLayout.error = "Başlık gereklidir"
                return
            }
            category.isEmpty() -> {
                binding.categoryInputLayout.error = "Kategori seçiniz"
                return
            }
            content.isEmpty() -> {
                binding.contentInputLayout.error = "İçerik gereklidir"
                return
            }
            else -> {
                // Hataları temizle
                binding.titleInputLayout.error = null
                binding.categoryInputLayout.error = null
                binding.contentInputLayout.error = null

                // TODO: API'ye gönderme işlemi yapılacak
                // Article object: title, category, content, tags
                Toast.makeText(
                    requireContext(),
                    "Makale yayınlanıyor...\nBaşlık: $title\nKategori: $category\n",
                    Toast.LENGTH_SHORT
                ).show()

                // Başarılı olunca geri dön
                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


