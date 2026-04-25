package com.talhakasikci.finn360fe.ui.viewPager

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.core.Easing
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.talhakasikci.finn360fe.R
import com.talhakasikci.finn360fe.databinding.FragmentPortfolioCakeChartBinding
import com.talhakasikci.finn360fe.models.Portfolio
import com.talhakasikci.finn360fe.viewmodel.ViewPagerViewModel

class PortfolioCakeChartFragment : Fragment() {

    private var _binding: FragmentPortfolioCakeChartBinding? = null
    private val binding get() = _binding!!

    private lateinit var pieChart: PieChart
    private val viewPagerViewModel: ViewPagerViewModel by activityViewModels()
    private lateinit var mockData: ArrayList<Portfolio>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPortfolioCakeChartBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pieChart = binding.portfolioPieChart

        viewPagerViewModel.portfolioArraylist.observe(viewLifecycleOwner){ portfolios ->
            mockData = portfolios
            if(!mockData.isNullOrEmpty()){
                val adjustedList = adjustValuesForChart(mockData)
                setupPieChart(adjustedList)
            }

        }


    }


    private fun adjustValuesForChart(data: ArrayList<Portfolio>): ArrayList<Portfolio> {
        // 1. Toplam Portföy Değerini Hesapla
        val totalAmount = data.sumOf { sumData ->
            sumData.quantity * sumData.currentPrice
        }

        // Yüzdesel eşik: %15. Bu değerin altındakiler Other olacak.
        val thresholdPercentage = 15.0

        val bigPortfolios = mutableListOf<Portfolio>()
        var otherTotalValue = 0.0 // Diğerlerinin toplam parasal değerini tutacak değişken

        data.forEach { portfolio ->
            val currentAmount = portfolio.quantity * portfolio.currentPrice
            val percentage = (currentAmount / totalAmount) * 100

            if (percentage >= thresholdPercentage) {
                bigPortfolios.add(portfolio)
            } else {
                otherTotalValue += currentAmount
            }
        }

        if (otherTotalValue > 0.0) {

            val otherPortfolio = Portfolio(
                id = "other_generated_id",
                iconUrl = null,
                assetType = "MIXED",
                symbol = "Other",
                description = "Other Assets",
                quantity = otherTotalValue,
                currentPrice = 1.0,
                averagePrice = 0.0,
                totalValue = otherTotalValue,
                profitLoss = 0.0,
                profitLossPercentage = 0.0,
                changePercentage = 0.0
            )
            bigPortfolios.add(otherPortfolio)
        }

        // MutableList'i ArrayList'e dönüştürerek geri döndür
        return ArrayList(bigPortfolios)



    }
    private fun setupPieChart(data: ArrayList<Portfolio>) {
       val pieChart = binding.portfolioPieChart
        val entries = ArrayList<PieEntry>()
        val colors = ArrayList<Int>()

        val colorPrimary = ContextCompat.getColor(requireContext(), R.color.colorPrimary)
        val colorPrimaryContainer = ContextCompat.getColor(requireContext(), R.color.colorPrimaryContainer)
        val colorTertinary = ContextCompat.getColor(requireContext(), R.color.colorTertiary)
        val colorWarning = ContextCompat.getColor(requireContext(), R.color.colorWarning)
        val colorError = ContextCompat.getColor(requireContext(), R.color.colorError)
        val colorInfo = ContextCompat.getColor(requireContext(), R.color.colorInfo)

        val otherColor = ContextCompat.getColor(requireContext(), R.color.colorSurfaceInverse)
        val availableColorList = listOf(
            colorPrimary,
            colorPrimaryContainer,
            colorTertinary,
            colorWarning,
            colorError,
            colorInfo
        )
        var colorIndex = 0
        data.forEachIndexed {index, pair ->
            entries.add(PieEntry((pair.quantity.toFloat()*pair.currentPrice.toFloat()),pair.symbol))

            if (pair.symbol == "Other") {
                colors.add(otherColor)
            }else {
                colors.add(availableColorList[index % availableColorList.size])
                colorIndex++
            }



        }

        val dataSet = PieDataSet(entries,"")
        dataSet.colors = colors
        dataSet.sliceSpace = 2f
        dataSet.setDrawValues(true)
        dataSet.valueTextSize = 12f
        dataSet.valueTextColor = ContextCompat.getColor(requireContext(), R.color.colorOnSurfaceVariant)


        //verileri grafiğe ata
        val pieData = PieData(dataSet)
        pieChart.data = pieData

        //grafik özellikleri
        pieChart.isDrawHoleEnabled = true
        pieChart.setHoleColor(Color.TRANSPARENT)
        pieChart.description.isEnabled = false
        pieChart.legend.isEnabled = true
        pieChart.legend.textColor = ContextCompat.getColor(requireContext(),R.color.colorOnSurface)
        pieChart.setDrawEntryLabels(false)
        pieChart.setUsePercentValues(true)
        pieChart.setDrawEntryLabels(false)


        pieChart.animateY(1400, com.github.mikephil.charting.animation.Easing.EaseInQuad) // Animasyon ekle
        pieChart.invalidate() // Grafiği yeniden çiz


    }
}