package com.talhakasikci.finn360fe.ui.fragments.app_fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.talhakasikci.finn360fe.R
import com.talhakasikci.finn360fe.adapter.StockMarketCVAdapter
import com.talhakasikci.finn360fe.adapter.WatchListAdapter
import com.talhakasikci.finn360fe.databinding.FragmentMainFragmentBinding
import com.talhakasikci.finn360fe.models.DashboardResponse
import com.talhakasikci.finn360fe.models.Instrument
import com.talhakasikci.finn360fe.models.watchlist.WatchList
import com.talhakasikci.finn360fe.viewmodel.DashboardViewModel
import com.talhakasikci.finn360fe.viewmodel.WatchListViewModel


class main_fragment : Fragment() {

    private var _binding: FragmentMainFragmentBinding? = null
    private val binding get() = _binding!!
    private val dashboardViewModel: DashboardViewModel by activityViewModels()
    private val watchListViewModel: WatchListViewModel by activityViewModels()
    private var marketSummaryList: ArrayList<Instrument>? = null
    private lateinit var marketAdapter : StockMarketCVAdapter
    private lateinit var watchlistAdapter : WatchListAdapter
    private lateinit var symbol: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMainFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        marketAdapter = StockMarketCVAdapter(){ clicked ->
            symbol = clicked.symbol
            val action = main_fragmentDirections.actionMainFragmentToInstrumentDetailFragment(symbol, null)
            findNavController().navigate(action)
        }
        watchlistAdapter = WatchListAdapter(){ clicked ->
            symbol = clicked.symbol

            val action = main_fragmentDirections.actionMainFragmentToInstrumentDetailFragment(symbol, null)
            findNavController().navigate(action)
        }


        binding.apply {
            stockMarketsRV.layoutManager = LinearLayoutManager(requireContext())
            stockMarketsRV.adapter = marketAdapter


            watchListRV.layoutManager = LinearLayoutManager(requireContext())
//            watchlistAdapter.submitList()
            watchListRV.adapter = watchlistAdapter
        }




        binding.addSymbolButton.setOnClickListener {
            val addSymbolFragment = AddSymbolFragment()
            addSymbolFragment.show(parentFragmentManager, "AddSymbolFragment")
        }

        observeViewModels()
        if (watchListViewModel.WLresults.value == null) {
            // Varsayılan sıralama parametreleri ile çağır
            watchListViewModel.getUserWatchList("symbol", "asc")
        }
        if (dashboardViewModel.dashboardResult.value == null){
            dashboardViewModel.getDashboardData()
        } else {
            Log.e("MainFragment", "Using cached dashboard data")
        }

    }

    private fun observeViewModels() {
        dashboardViewModel.dashboardResult.observe(viewLifecycleOwner) { dashboardResponse->
            Log.e("MainFragment", "Dashboard Response: $dashboardResponse")
            dashboardResponse?.let { data ->
                binding.apply {
                    welcomeTextView.text = "${data.header.greeting} ${data.header.userName}"
                    dateTextView.text = data.header.date
                    marketSummaryList = data.marketSummary
                    marketSummaryList?.let {
                        marketAdapter.submitList(data.marketSummary.toList())
                    }

                }
            }

        }

        watchListViewModel.WLresults.observe(viewLifecycleOwner) { watchlistResponse->
            watchlistResponse?.let {data ->
                binding.apply {
                    watchlistAdapter.submitList(data.toList())
                }
            }
        }
    }

//    private fun getInstrumentsList(): ArrayList<Instrument>{
//        val instrumentsList = ArrayList<Instrument>()
//        instrumentsList.add(Instrument("Nasdaq - 100","NSDQ", 249993.46, +1.5, "https://assets.coingecko.com/coins/images/1/small/bitcoin.png"))
//        instrumentsList.add(Instrument("Bitcoin","BTC", 100240.80, -0.8, "https://assets.coingecko.com/coins/images/1/small/bitcoin.png"))
//
//        return instrumentsList
//    }
//
//    private fun getWatchList(): ArrayList<Instrument>{
//        val watchList = ArrayList<WatchList>()
//        watchList.add(Instrument("Apple Inc.","AAPL", 175.05, +0.6, "https://assets.coingecko.com/coins/images/1/small/bitcoin.png"))
//        watchList.add(Instrument("Microsoft Corp.","MSFT", 310.98, -1.2, "https://assets.coingecko.com/coins/images/1/small/bitcoin.png"))
//        watchList.add(Instrument("Amazon.com Inc.","AMZN", 135.45, +2.3, "https://assets.coingecko.com/coins/images/1/small/bitcoin.png"))
//        watchList.add(Instrument("Tesla Inc.","TSLA", 720.22, -0.5, "https://assets.coingecko.com/coins/images/1/small/bitcoin.png"))
//        watchList.add(Instrument("Apple Inc.","AAPL", 175.05, +0.6, "https://assets.coingecko.com/coins/images/1/small/bitcoin.png"))
//        watchList.add(Instrument("Microsoft Corp.","MSFT", 310.98, -1.2, "https://assets.coingecko.com/coins/images/1/small/bitcoin.png"))
//        watchList.add(Instrument("Amazon.com Inc.","AMZN", 135.45, +2.3, "https://assets.coingecko.com/coins/images/1/small/bitcoin.png"))
//        watchList.add(Instrument("Tesla Inc.","TSLA", 720.22, -0.5, "https://assets.coingecko.com/coins/images/1/small/bitcoin.png"))
//
//        return watchList
//    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}