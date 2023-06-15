package com.example.eshfeenygraduationproject.eshfeeny.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.data.repository.ProductRepoImpl
import com.example.eshfeenygraduationproject.databinding.FragmentCartBinding
import com.example.eshfeenygraduationproject.eshfeeny.productsAdapter.ProductCartAdapter
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.viewModel.ProductViewModel
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.viewModelFactory.ProductViewModelFactory
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.viewModel.UserViewModel


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

            productViewModel.cartItems.observe(viewLifecycleOwner) {

                stopShimmer()

                if (it.cart.isEmpty()) {
                    binding?.cartImageLayout?.visibility = View.VISIBLE
                    binding?.cartRecyclerView?.visibility = View.GONE
                } else {
                    binding?.cartImageLayout?.visibility = View.GONE
                    binding?.cartRecyclerView?.visibility = View.VISIBLE
                    val adapter =
                        ProductCartAdapter(productViewModel, userDetails._id, viewLifecycleOwner)
                    binding?.cartRecyclerView?.adapter = adapter
                    adapter.submitList(it.cart)
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