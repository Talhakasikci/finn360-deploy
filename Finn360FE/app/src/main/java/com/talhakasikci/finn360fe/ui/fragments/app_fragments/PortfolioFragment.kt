package com.talhakasikci.finn360fe.ui.fragments.app_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import com.talhakasikci.finn360fe.R
import com.talhakasikci.finn360fe.adapter.PortfolioPagerAdapter
import com.talhakasikci.finn360fe.adapter.UserPortfolioAdapter
import com.talhakasikci.finn360fe.databinding.FragmentPortfolioBinding
import com.talhakasikci.finn360fe.models.Portfolio
import com.talhakasikci.finn360fe.viewmodel.PortfolioViewModel
import com.talhakasikci.finn360fe.viewmodel.ViewPagerViewModel


class PortfolioFragment : Fragment() {
    private var _binding: FragmentPortfolioBinding? = null
    private val binding get() = _binding!!
    private var isAmountVisible = true
    private val viewPagerViewModel: ViewPagerViewModel by activityViewModels()
    private val portfolioViewModel: PortfolioViewModel by activityViewModels()
    private lateinit var portfolioListAdapter: UserPortfolioAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPortfolioBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        setupViewPager()
        setupObservers()

        portfolioViewModel.fetchPortfolio()

        binding.addInvestmentButton.setOnClickListener {
            val addSymbolFragment = AddSymbolFragment()
            addSymbolFragment.show(parentFragmentManager, "AddSymbolFragment")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpRecyclerView(){
        portfolioListAdapter = UserPortfolioAdapter(arrayListOf())
        binding.apply {
            investmentsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            investmentsRecyclerView.adapter = portfolioListAdapter
        }
    }

    private fun setupViewPager(){
        val portfolioPagerAdapter = PortfolioPagerAdapter(this)
        val viewPager = binding.viewPager
        viewPager.adapter = portfolioPagerAdapter

        // TabLayout entegrasyonu
        val tabLayout = binding.tablayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            // Tab isimleri (Opsiyonel)
            when(position) {
                0 -> tab.text = "Chart"
                1 -> tab.text = "Stats"
            }
        }.attach()
    }

    private fun setupObservers(){
        portfolioViewModel.portfolioData.observe(viewLifecycleOwner) { portfolioData->
            if (portfolioData != null){
                portfolioListAdapter.submitList(portfolioData.items)

                viewPagerViewModel.updatePortfolioAmount(
                    portfolioData.totalBalance.toString(),
                    portfolioData.items
                )
            }
        }

        portfolioViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            // binding.progressBar.visibility = if(isLoading) View.VISIBLE else View.GONE
        }

        // Hata durumunu dinle
        portfolioViewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            if (!error.isNullOrEmpty()) {
                Toast.makeText(context, error, Toast.LENGTH_LONG).show()
            }
        }
    }


//    private fun getPortfolioInvestmentsList(): ArrayList<Portfolio>{
//        val portfolioList = ArrayList<Portfolio>()
//        // Büyük Dilimler (Muhtemelen %15 eşiğini aşar)
//        portfolioList.add(Portfolio("GOOGL", 30.0, 150.00, "Alphabet INC.", R.drawable.insturment_logo_nasdaq, +0.6))
//        portfolioList.add(Portfolio("MSFT", 20.0, 400.00, "Microsoft Corp.", R.drawable.insturment_logo_nasdaq, +0.8))
//
//        // Orta Dilimler
//        portfolioList.add(Portfolio("TSLA", 20.0, 220.00, "Tesla INC.", R.drawable.insturment_logo_nasdaq, -1.2))
//        portfolioList.add(Portfolio("AMZN", 20.0, 140.00, "Amazon INC.", R.drawable.insturment_logo_nasdaq, +0.2))
//
//        // Küçük Dilimler (Bu üçü muhtemelen birleşip "Other" dilimini oluşturacak)
//        portfolioList.add(Portfolio("BABA", 2.0, 80.00, "Alibaba Group", R.drawable.insturment_logo_nasdaq, -0.5))
//        portfolioList.add(Portfolio("NVDA", 0.5, 500.00, "Nvidia Corp.", R.drawable.insturment_logo_nasdaq, +1.5))
//        portfolioList.add(Portfolio("JPM", 1.0, 160.00, "J.P. Morgan Chase", R.drawable.insturment_logo_nasdaq, +0.1))
//
//        return portfolioList
//    }
//
//    private fun getTotalAmount(portfolioList: ArrayList<Portfolio>): String {
//        var total = 0.0
//        for (investment in portfolioList) {
//            total += investment.Price * investment.Quantity
//        }
//        return total.toString()
//    }


}
