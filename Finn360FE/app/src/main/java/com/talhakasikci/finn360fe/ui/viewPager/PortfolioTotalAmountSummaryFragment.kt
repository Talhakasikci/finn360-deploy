package com.talhakasikci.finn360fe.ui.viewPager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.talhakasikci.finn360fe.R
import com.talhakasikci.finn360fe.databinding.FragmentPortfolioTotalAmountSummaryBinding
import com.talhakasikci.finn360fe.viewmodel.ViewPagerViewModel
import kotlin.getValue


class PortfolioTotalAmountSummaryFragment : Fragment() {
    private var _binding: FragmentPortfolioTotalAmountSummaryBinding ? = null
    private val binding get() = _binding!!

    private val viewPagerViewModel: ViewPagerViewModel by activityViewModels()
    private var isAmountVisible = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPortfolioTotalAmountSummaryBinding.inflate(inflater,container,false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPagerViewModel.totalAmount.observe(viewLifecycleOwner) { totalAmount ->
            binding.totalAmountValueTextView.text = "$${totalAmount}"
            binding.totalAmountVisibilityButton.setOnClickListener {
                binding.apply {
                    // Toggle the boolean and update the icon using ContextCompat
                    if (isAmountVisible) {
                        isAmountVisible = false
                        totalAmountValueTextView.text = "****"
                        totalAmountVisibilityButton.icon = ContextCompat.getDrawable(requireContext(), R.drawable.visibility_off_icon)
                    } else {
                        isAmountVisible = true
                        totalAmountValueTextView.text = "$${totalAmount}"
                        totalAmountVisibilityButton.icon = ContextCompat.getDrawable(requireContext(), R.drawable.visibility_icon)
                    }
                }

            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}