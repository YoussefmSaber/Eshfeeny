package com.example.eshfeenygraduationproject.eshfeeny.cart

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.data.repository.ProductRepoImpl
import com.example.eshfeenygraduationproject.databinding.FragmentCartBinding
import com.example.eshfeenygraduationproject.eshfeeny.productsAdapter.ProductCartAdapter
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.ProductViewModel
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.ProductViewModelFactory
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.UserViewModel


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
                if (it.cart.isEmpty()) {
                    binding?.cartImageLayout?.visibility = View.VISIBLE
                    binding?.cartRecyclerView?.visibility = View.GONE
                } else {
                    binding?.cartImageLayout?.visibility = View.GONE
                    binding?.cartRecyclerView?.visibility = View.VISIBLE
                    val adapter = ProductCartAdapter(productViewModel, userDetails._id)
                    binding?.cartRecyclerView?.adapter = adapter
                    Log.i("cart", it.cart.toString())
                    adapter.submitList(it.cart)
                }
            }
        }

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}