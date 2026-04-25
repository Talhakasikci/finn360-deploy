package com.talhakasikci.finn360fe.ui.fragments.app_fragments

import android.media.AudioTimestamp
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.talhakasikci.finn360fe.R
import com.talhakasikci.finn360fe.databinding.FragmentInstrumentDetailBinding
import com.talhakasikci.finn360fe.models.PriceData

//data class PriceData(val timestamp: String?, val price: Float?)
class InstrumentDetailFragment : Fragment() {
    private var _binding: FragmentInstrumentDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var lineChart: LineChart
    private val args: InstrumentDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val incomingSymbol = args.instrumentSymbol
        binding.apply {
            instrumentTitleTextView.text = incomingSymbol
        }

        lineChart = binding.stockChart

        val mockData = listOf(
            // 17 Kasım 2025 (Pazartesi) Başlangıcı
            PriceData("17/11/2025 00:00", 95469.75f),
            PriceData("17/11/2025 01:00", 95284.35f),
            PriceData("17/11/2025 02:00", 93588.80f),
            PriceData("17/11/2025 03:00", 94710.62f),
            PriceData("17/11/2025 04:00", 94051.61f),
            PriceData("17/11/2025 05:00", 94211.88f),
            PriceData("17/11/2025 06:00", 92693.12f),
            PriceData("17/11/2025 07:00", 92535.85f),
            PriceData("17/11/2025 08:00", 91640.15f),
            PriceData("17/11/2025 09:00", 91833.22f),
            PriceData("17/11/2025 10:00", 91753.35f),
            PriceData("17/11/2025 11:00", 92134.04f),
            PriceData("17/11/2025 12:00", 92036.73f),
            PriceData("17/11/2025 13:00", 92021.63f),
            PriceData("17/11/2025 14:00", 91558.86f),
            PriceData("17/11/2025 15:00", 91092.77f),
            PriceData("17/11/2025 16:00", 90652.61f),
            PriceData("17/11/2025 17:00", 89455.50f),
            PriceData("17/11/2025 18:00", 90004.10f),
            PriceData("17/11/2025 19:00", 89621.24f),
            PriceData("17/11/2025 20:00", 90365.88f),
            PriceData("17/11/2025 21:00", 91196.77f),
            PriceData("17/11/2025 22:00", 91156.08f),
            PriceData("17/11/2025 23:00", 91317.66f),

// 18 Kasım 2025 (Salı)
            PriceData("18/11/2025 00:00", 91530.58f),
            PriceData("18/11/2025 01:00", 91392.18f),
            PriceData("18/11/2025 02:00", 91517.70f),
            PriceData("18/11/2025 03:00", 91339.44f),
            PriceData("18/11/2025 04:00", 92822.48f),
            PriceData("18/11/2025 05:00", 93375.37f),
            PriceData("18/11/2025 06:00", 93158.16f),
            PriceData("18/11/2025 07:00", 93403.71f),
            PriceData("18/11/2025 08:00", 92981.44f),
            PriceData("18/11/2025 09:00", 92777.62f),
            PriceData("18/11/2025 10:00", 92515.37f),
            PriceData("18/11/2025 11:00", 93145.76f),
            PriceData("18/11/2025 12:00", 92819.76f),
            PriceData("18/11/2025 13:00", 92400.18f),
            PriceData("18/11/2025 14:00", 92268.61f),
            PriceData("18/11/2025 15:00", 92658.36f),
            PriceData("18/11/2025 16:00", 91770.49f),
            PriceData("18/11/2025 17:00", 91181.02f),
            PriceData("18/11/2025 18:00", 90291.88f),
            PriceData("18/11/2025 19:00", 90908.60f),
            PriceData("18/11/2025 20:00", 91807.78f),
            PriceData("18/11/2025 21:00", 91376.60f),
            PriceData("18/11/2025 22:00", 91479.71f),
            PriceData("18/11/2025 23:00", 91261.52f),

// 19 Kasım 2025 (Çarşamba)
            PriceData("19/11/2025 00:00", 91350.22f),
            PriceData("19/11/2025 01:00", 91665.73f),
            PriceData("19/11/2025 02:00", 91286.76f),
            PriceData("19/11/2025 03:00", 91639.43f),
            PriceData("19/11/2025 04:00", 90038.01f),
            PriceData("19/11/2025 05:00", 89576.81f),
            PriceData("19/11/2025 06:00", 89177.53f),
            PriceData("19/11/2025 07:00", 89019.22f),
            PriceData("19/11/2025 08:00", 88762.89f),
            PriceData("19/11/2025 09:00", 89413.16f),
            PriceData("19/11/2025 10:00", 90565.05f),
            PriceData("19/11/2025 11:00", 90392.70f),
            PriceData("19/11/2025 12:00", 91408.36f),
            PriceData("19/11/2025 13:00", 91801.78f),
            PriceData("19/11/2025 14:00", 92482.25f),
            PriceData("19/11/2025 15:00", 92531.90f),
            PriceData("19/11/2025 16:00", 92385.46f),
            PriceData("19/11/2025 17:00", 92566.32f),
            PriceData("19/11/2025 18:00", 92570.05f),
            PriceData("19/11/2025 19:00", 91755.99f),
            PriceData("19/11/2025 20:00", 92166.87f),
            PriceData("19/11/2025 21:00", 92032.79f),
            PriceData("19/11/2025 22:00", 91818.19f),
            PriceData("19/11/2025 23:00", 91718.60f),

// 20 Kasım 2025 (Perşembe)
            PriceData("20/11/2025 00:00", 91901.94f),
            PriceData("20/11/2025 01:00", 91798.67f),
            PriceData("20/11/2025 02:00", 91464.54f),
            PriceData("20/11/2025 03:00", 90911.42f),
            PriceData("20/11/2025 04:00", 90092.10f),
            PriceData("20/11/2025 05:00", 87886.34f),
            PriceData("20/11/2025 06:00", 87122.85f),
            PriceData("20/11/2025 07:00", 86381.81f),
            PriceData("20/11/2025 08:00", 86519.07f),
            PriceData("20/11/2025 09:00", 86400.94f),
            PriceData("20/11/2025 10:00", 87438.47f),
            PriceData("20/11/2025 11:00", 87734.70f),
            PriceData("20/11/2025 12:00", 86649.97f),
            PriceData("20/11/2025 13:00", 87272.32f),
            PriceData("20/11/2025 14:00", 86942.82f),
            PriceData("20/11/2025 15:00", 85432.58f),
            PriceData("20/11/2025 16:00", 85723.30f),
            PriceData("20/11/2025 17:00", 85978.39f),
            PriceData("20/11/2025 18:00", 86049.26f),
            PriceData("20/11/2025 19:00", 85514.46f),
            PriceData("20/11/2025 20:00", 83851.28f),
            PriceData("20/11/2025 21:00", 83590.70f),
            PriceData("20/11/2025 22:00", 82175.40f),
            PriceData("20/11/2025 23:00", 82615.08f),

// 21 Kasım 2025 (Cuma)
            PriceData("21/11/2025 00:00", 82221.74f),
            PriceData("21/11/2025 01:00", 83286.12f),
            PriceData("21/11/2025 02:00", 83864.23f),
            PriceData("21/11/2025 03:00", 84511.74f),
            PriceData("21/11/2025 04:00", 82884.31f),
            PriceData("21/11/2025 05:00", 84739.63f),
            PriceData("21/11/2025 06:00", 84913.07f),
            PriceData("21/11/2025 07:00", 84641.61f),
            PriceData("21/11/2025 08:00", 84168.35f),
            PriceData("21/11/2025 09:00", 84446.97f),
            PriceData("21/11/2025 10:00", 85208.56f),
            PriceData("21/11/2025 11:00", 84164.15f),
            PriceData("21/11/2025 12:00", 85087.95f),
            PriceData("21/11/2025 13:00", 84632.33f),
            PriceData("21/11/2025 14:00", 85073.94f),
            PriceData("21/11/2025 15:00", 84411.50f),
            PriceData("21/11/2025 16:00", 84440.46f),
            PriceData("21/11/2025 17:00", 84171.03f),
            PriceData("21/11/2025 18:00", 84183.98f),
            PriceData("21/11/2025 19:00", 84608.18f),
            PriceData("21/11/2025 20:00", 84520.60f),
            PriceData("21/11/2025 21:00", 83955.55f),
            PriceData("21/11/2025 22:00", 83908.27f),
            PriceData("21/11/2025 23:00", 83573.95f),

// 22 Kasım 2025 (Cumartesi)
            PriceData("22/11/2025 00:00", 84136.62f),
            PriceData("22/11/2025 01:00", 83811.12f),
            PriceData("22/11/2025 02:00", 83989.47f),
            PriceData("22/11/2025 03:00", 84424.11f),
            PriceData("22/11/2025 04:00", 84087.08f),
            PriceData("22/11/2025 05:00", 84589.42f),
            PriceData("22/11/2025 06:00", 84475.98f),
            PriceData("22/11/2025 07:00", 84547.89f),
            PriceData("22/11/2025 08:00", 84585.50f),
            PriceData("22/11/2025 09:00", 84392.64f),
            PriceData("22/11/2025 10:00", 84388.39f),
            PriceData("22/11/2025 11:00", 85095.90f),
            PriceData("22/11/2025 12:00", 84675.51f),
            PriceData("22/11/2025 13:00", 84926.95f),
            PriceData("22/11/2025 14:00", 85803.92f),
            PriceData("22/11/2025 15:00", 86048.77f),
            PriceData("22/11/2025 16:00", 86218.87f),
            PriceData("22/11/2025 17:00", 86493.01f),
            PriceData("22/11/2025 18:00", 86205.97f),
            PriceData("22/11/2025 19:00", 85877.59f),
            PriceData("22/11/2025 20:00", 85644.69f),
            PriceData("22/11/2025 21:00", 86100.52f),
            PriceData("22/11/2025 22:00", 86351.28f),
            PriceData("22/11/2025 23:00", 86026.92f),

// 23 Kasım 2025 (Pazar) ve 24 Kasım 2025 (Pazartesi Sabah)
            PriceData("23/11/2025 00:00", 86243.92f),
            PriceData("23/11/2025 01:00", 86586.77f),
            PriceData("23/11/2025 02:00", 86934.68f),
            PriceData("23/11/2025 03:00", 86513.36f),
            PriceData("23/11/2025 04:00", 87226.48f),
            PriceData("23/11/2025 05:00", 87096.89f),
            PriceData("23/11/2025 06:00", 86838.44f),
            PriceData("23/11/2025 07:00", 86981.94f),
            PriceData("23/11/2025 08:00", 87381.62f),
            PriceData("23/11/2025 09:00", 87459.01f),
            PriceData("23/11/2025 10:00", 87856.91f),
            PriceData("23/11/2025 11:00", 87854.92f),
            PriceData("23/11/2025 12:00", 86783.85f),
            PriceData("23/11/2025 13:00", 86628.25f),
            PriceData("23/11/2025 14:00", 86848.55f),
            PriceData("23/11/2025 15:00", 87498.46f),
            PriceData("23/11/2025 16:00", 87280.46f),
            PriceData("23/11/2025 17:00", 86752.70f),
            PriceData("23/11/2025 18:00", 87387.46f),
            PriceData("23/11/2025 19:00", 86844.71f),
            PriceData("23/11/2025 20:00", 87075.75f),
            PriceData("23/11/2025 21:00", 86764.94f),
            PriceData("23/11/2025 22:00", 85953.92f),
            PriceData("23/11/2025 23:00", 86001.79f)

        )

        setupChart(mockData)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentInstrumentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setupChart(dataList: List<PriceData>){

        val entries = ArrayList<Entry>()
        val labels = ArrayList<String>()

        val chartTextColor = ContextCompat.getColor(requireContext(),R.color.colorOnSurface)

        dataList.forEachIndexed { index, data ->
            entries.add(Entry(index.toFloat(), data.price ?: 0f))
            labels.add(data.timestamp ?: "")
        }

        val dataSet = LineDataSet(entries, getString(R.string.price_chart_label))

        dataSet.apply {
            color = ContextCompat.getColor(requireContext(),R.color.colorOnSurfaceVariant)
            valueTextColor = chartTextColor
            lineWidth = 2f
            setDrawCircles(false)
            setDrawValues(false)
            setDrawFilled(true)
            fillColor = ContextCompat.getColor(requireContext(),R.color.colorPrimaryContainer)
            mode = LineDataSet.Mode.CUBIC_BEZIER
        }

        val lineData = LineData(dataSet)
        lineChart.data = lineData

        lineChart.apply {
            description.isEnabled = true
            description.textColor = chartTextColor
            description.text = "deneme"
            axisRight.isEnabled = false
            legend.textColor = chartTextColor
            legend.yEntrySpace = 10f
            xAxis.apply {
                 position = XAxis.XAxisPosition.BOTTOM
                valueFormatter = IndexAxisValueFormatter(labels)
                granularity = 1f
                setDrawGridLines(true)
                textColor = chartTextColor

                labelRotationAngle = -60f


                axisMinimum = 0f
                axisMaximum = (labels.size -1).toFloat()
                setLabelCount(8,true)
            }
            extraBottomOffset = 40f
            extraLeftOffset = 10f
            extraRightOffset = 10f
            axisLeft.apply {
                setDrawGridLines(true)
                textColor = chartTextColor
            }
            animateX(1000)
            invalidate()

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}