package com.talhakasikci.finn360fe.ui.fragments.app_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.talhakasikci.finn360fe.R
import com.talhakasikci.finn360fe.adapter.SearchSymbolAdapter
import com.talhakasikci.finn360fe.databinding.FragmentAddSymbolBinding
import com.talhakasikci.finn360fe.models.Instrument
import com.talhakasikci.finn360fe.models.Search
import com.talhakasikci.finn360fe.viewmodel.SearchViewModel
import kotlin.io.encoding.Base64


class AddSymbolFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentAddSymbolBinding? = null
    private val binding get() = _binding!!
    private val searchViewModel: SearchViewModel by viewModels()
    private var searchList: ArrayList<Search>? = null
    private var searchAdapter: SearchSymbolAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddSymbolBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
//        searchAdapter = SearchSymbolAdapter(getDefaultSearchList())


        observeViewModels()
        binding.apply {




            searchbarSymbolET.doOnTextChanged { inputText, _, _, _ ->
                val query = inputText.toString().orEmpty()
                if (query.length >= 3) {

                    searchViewModel.getSearchResults(query)

                }else{
                   searchAdapter?.updateList(getDefaultSearchList())
                }
            }


        }
    }

    private fun setupRecyclerView() {
        searchAdapter = SearchSymbolAdapter(getDefaultSearchList(), onStockClick = {selectedInstrument ->
            dismiss()
                val action = AddSymbolFragmentDirections.actionAddSymbolFragmentToInstrumentDetailFragment(selectedInstrument.symbol, selectedInstrument.id)
                findNavController().navigate(action)
        })
        binding.addSymbolRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = searchAdapter
        }
    }

    private fun observeViewModels() {
        searchViewModel.searchResults.observe(viewLifecycleOwner) { searchData ->
            searchData?.let { response ->

                searchAdapter?.updateList(response)
            }
        }
    }

    override fun onStart() {
        super.onStart()

        val dialog = dialog as? BottomSheetDialog
        val bottomSheet = dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)


        bottomSheet?.let { sheet->
            val behavior = BottomSheetBehavior.from(sheet)

            //ekran yüksekliği alındı
            val displayMetrics = resources.displayMetrics
            val height = displayMetrics.heightPixels

            //max height bulundu
            val maxheight = (height * 0.75).toInt()

            //layout'un max height'i ayarlandı
            sheet.layoutParams.height = maxheight
            sheet.requestLayout()
            //tamamen aç
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            //kullanıcı aşağı kaydırdığında kapan
            behavior.isDraggable = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getDefaultSearchList(): ArrayList<Search> {
        val DefaultSearchList = ArrayList<Search>().apply {
            add(Search("AAPL","Apple Inc.","AAPL","STOCK","https://assets.coincap.io/assets/icons/xrp@2x.png" ))
            add(Search("MSFT","Microsoft Corp.","MSFT","STOCK","https://assets.coincap.io/assets/icons/xrp@2x.png" ))
            add(Search("AMZN","Amazon.com Inc.","AMZN","STOCK","https://assets.coincap.io/assets/icons/xrp@2x.png" ))
            add(Search("TSLA","Tesla Inc.","TSLA", "STOCK","https://assets.coincap.io/assets/icons/xrp@2x.png"))
            add(Search("AAPL","Apple Inc.","AAPL", "STOCK","https://assets.coincap.io/assets/icons/xrp@2x.png"))
            add(Search("MSFT","Microsoft Corp.","MSFT", "STOCK","https://assets.coincap.io/assets/icons/xrp@2x.png"))
            add(Search("AMZN","Amazon.com Inc.","AMZN", "STOCK","https://assets.coincap.io/assets/icons/xrp@2x.png"))
            add(Search("TSLA","Tesla Inc.","TSLA", "STOCK","https://assets.coincap.io/assets/icons/xrp@2x.png"))
        }
        DefaultSearchList.sortBy { it.symbol  }
        return DefaultSearchList
    }





}