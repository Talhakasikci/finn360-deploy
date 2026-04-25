package com.talhakasikci.finn360fe.ui.fragments.app_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.talhakasikci.finn360fe.R
import com.talhakasikci.finn360fe.adapter.ArticlesAdapter
import com.talhakasikci.finn360fe.databinding.FragmentArticleBinding
import com.talhakasikci.finn360fe.models.Article


class ArticleFragment : Fragment() {
    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentArticleBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            articleFragmentRV.layoutManager = LinearLayoutManager(requireContext())
            articleFragmentRV.adapter = ArticlesAdapter(getArticleList())

            articleToggleGroup.check(followingButton.id)
            addPostFAB.setOnClickListener {
                findNavController().navigate(R.id.addArticleFragment)
            }
        }
    }

    private fun getArticleList(): ArrayList<Article>{
        val articleList = ArrayList<Article>().apply {
            add(
                Article(
                    userID = 101,
                    userName = "Can",
                    userSurname = "Aydın",
                    userEmail = "can.aydin@mail.com",
                    id = 1,
                    title = "Kotlin Scope Fonksiyonları",
                    body = "apply, let, run, with, also gibi scope fonksiyonlarının kullanım alanları ve farkları."
                )
            )
            add(
                Article(
                    userID = 102,
                    userName = "Deniz",
                    userSurname = "Kara",
                    userEmail = "deniz.kara@mail.com",
                    id = 2,
                    title = "Jetpack Compose: Listeler",
                    body = "LazyColumn ve LazyRow kullanarak büyük veri setlerini verimli bir şekilde görüntüleme rehberi."
                )
            )
            add(
                Article(
                    userID = 103,
                    userName = "Efe",
                    userSurname = "Güler",
                    userEmail = "efe.guler@mail.com",
                    id = 3,
                    title = "Versiyon Kontrolü ile Yaşamak",
                    body = "Git ve GitHub kullanarak ekip içinde temiz ve düzenli kod yönetimi stratejileri."
                )
            )
            add(
                Article(
                    userID = 101, // Aynı kullanıcıya ait ikinci makale
                    userName = "Can",
                    userSurname = "Aydın",
                    userEmail = "can.aydin@mail.com",
                    id = 4,
                    title = "Kotlin Coroutines Temelleri",
                    body = "Suspending fonksiyonlar, Dispatcher'lar ve yapılandırılmış eşzamanlılık kavramları."
                )
            )
            add(
                Article(
                    userID = 101, // Aynı kullanıcıya ait ikinci makale
                    userName = "Can",
                    userSurname = "Aydın",
                    userEmail = "can.aydin@mail.com",
                    id = 4,
                    title = "Kotlin Coroutines Temelleri",
                    body = "Suspending fonksiyonlar, Dispatcher'lar ve yapılandırılmış eşzamanlılık kavramları."
                )
            )
            add(
                Article(
                    userID = 101, // Aynı kullanıcıya ait ikinci makale
                    userName = "Can",
                    userSurname = "Aydın",
                    userEmail = "can.aydin@mail.com",
                    id = 4,
                    title = "Kotlin Coroutines Temelleri",
                    body = "Suspending fonksiyonlar, Dispatcher'lar ve yapılandırılmış eşzamanlılık kavramları."
                )
            )
            add(
                Article(
                    userID = 101, // Aynı kullanıcıya ait ikinci makale
                    userName = "Can",
                    userSurname = "Aydın",
                    userEmail = "can.aydin@mail.com",
                    id = 4,
                    title = "Kotlin Coroutines Temelleri",
                    body = "Suspending fonksiyonlar, Dispatcher'lar ve yapılandırılmış eşzamanlılık kavramları."
                )
            )
        }
        return articleList

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}