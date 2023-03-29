package com.example.eshfeenygraduationproject.authentication.onBoarding.fragment

import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.viewpager2.widget.ViewPager2
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentOnBoardingBinding
import com.example.eshfeenygraduationproject.authentication.util.BoardingList
import com.example.eshfeenygraduationproject.authentication.onBoarding.viewPagerAdapter.ViewPagerAdapter


class OnBoardingFragment : Fragment() {

    //  Binding adapter for the onBoarding page
    private var binding: FragmentOnBoardingBinding? = null

    //  viewPager variable
    private var viewPager2: ViewPager2? = null

    //  Object to change values and check for the viewPager position
    private val pager2Callback = object : ViewPager2.OnPageChangeCallback() {

        //  function that called when a page is visible
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)

            //  if the viewpager at the last item to the following
            if (position == BoardingList.onBoardingList.size - 1) {

                //  change the text value for the button
                binding?.button2?.setText(R.string.start)

                //  make the text view invisible
                binding?.textView?.isVisible = false

                //  add a click-listener to the button so it can navigate to welcome fragment
                binding?.button2?.setOnClickListener {

                    //   Navigating to the welcome fragment
                    Navigation.findNavController(it)
                        .navigate(R.id.OnBoarding2WelcomeFragment)
                }
                //  if the viewpager at any item except the element
            } else {

                //  change the text for the button to التالي
                binding?.button2?.setText(R.string.next)

                //  make the textview visible
                binding?.textView?.isVisible = true

                // adding a click-listener to the button so it changes the item on viewpager
                binding?.button2?.setOnClickListener {

                    //  going to the next item in the viewpager
                    viewPager2?.currentItem = position + 1
                }

                //  adding a click-listener to the textview so it can skip the onBoarding fragment
                binding?.textView?.setOnClickListener {
                    Navigation.findNavController(it)
                        .navigate(R.id.OnBoarding2WelcomeFragment)
                }
            }
        }
    }

    // onCreateView function to set the data to the fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //  Initializing the binding variable to the onBoardFragment
        binding = FragmentOnBoardingBinding.inflate(inflater)
        //  calling the setupviewPager function
        setupViewPager(binding!!)

        //  setting the data to fragment
        return binding?.root
    }

    //  setupViewPager function to initialize the viewPager
    private fun setupViewPager(binding: FragmentOnBoardingBinding) {

        //  adapter to update the data in the viewpager
        val adapter = ViewPagerAdapter(BoardingList.onBoardingList)

        // initializing the viewpager
        viewPager2 = binding.viewPagerOnboarding
        viewPager2?.adapter = adapter
        viewPager2?.registerOnPageChangeCallback(pager2Callback)

        //  connecting the dotsIndicator to the viewpager
        binding.springDotsIndicator.attachTo(viewPager2!!)
    }

    //  To prevent any memory leak
    override fun onDestroy() {
        super.onDestroy()
        viewPager2?.unregisterOnPageChangeCallback(pager2Callback)
        binding = null
    }
}