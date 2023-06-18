package com.example.eshfeenygraduationproject.eshfeeny.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.data.repository.ProductRepoImpl
import com.example.domain.entity.pharmacySendRequest.FindNearestPharmacy
import com.example.eshfeenygraduationproject.databinding.FragmentCartBinding
import com.example.eshfeenygraduationproject.eshfeeny.productsAdapter.ProductCartAdapter
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.viewModel.ProductViewModel
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.viewModel.UserViewModel
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.viewModelFactory.ProductViewModelFactory


class CartFragment : Fragment() {

    private var binding: FragmentCartBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater)

        val userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        val repo = ProductRepoImpl()

        val productViewModelFactory = ProductViewModelFactory(repo)
        val productViewModel =
            ViewModelProvider(this, productViewModelFactory)[ProductViewModel::class.java]

        userViewModel.userData.observe(viewLifecycleOwner) { userDetails ->

            productViewModel.getUserCartItems(userDetails._id)

            productViewModel.cartItems.observe(viewLifecycleOwner) { cartResponse ->

                stopShimmer()

                if (cartResponse.cart.isEmpty()) {
                    binding?.cartImageLayout?.visibility = View.VISIBLE
                    binding?.cartRecyclerView?.visibility = View.GONE
                } else {
                    binding?.cartImageLayout?.visibility = View.GONE
                    binding?.cartRecyclerView?.visibility = View.VISIBLE
                    val adapter =
                        ProductCartAdapter(productViewModel, userDetails._id, viewLifecycleOwner)
                    binding?.cartRecyclerView?.adapter = adapter
                    adapter.submitList(cartResponse.cart)
                    binding?.findNearestPharmacyButton?.setOnClickListener {
                        val listItems: MutableList<String> = mutableListOf()
                        cartResponse.cart.forEach {
                            listItems.add(it.product._id)
                        }
                        val action = CartFragmentDirections.actionCartFragment2ToMapsFragment(
                            FindNearestPharmacy(listItems), "Cart"
                        )
                        findNavController().navigate(action)
                    }
                }
            }
        }

        return binding?.root
    }

    private fun stopShimmer() {
        binding?.shimmerLayoutCart?.stopShimmer()
        binding?.shimmerLayoutCart?.visibility = View.GONE
        binding?.cartPage?.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}