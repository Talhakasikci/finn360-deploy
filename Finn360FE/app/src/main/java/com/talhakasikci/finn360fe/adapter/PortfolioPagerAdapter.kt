package com.talhakasikci.finn360fe.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.talhakasikci.finn360fe.ui.viewPager.PortfolioCakeChartFragment
import com.talhakasikci.finn360fe.ui.viewPager.PortfolioTotalAmountSummaryFragment

class PortfolioPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun createFragment(position: Int): Fragment {
        return when (position){
            0 -> PortfolioTotalAmountSummaryFragment()
            1 -> PortfolioCakeChartFragment()
            else -> PortfolioTotalAmountSummaryFragment()
        }
    }

    override fun getItemCount(): Int {
        return 2
    }
}