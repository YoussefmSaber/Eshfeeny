package com.example.eshfeenygraduationproject.eshfeeny.searchForProducts

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.data.repository.ProductRepoImpl
import com.example.domain.entity.cart.CartResponse
import com.example.domain.entity.product.ProductResponse
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentProductCategoryBinding
import com.example.eshfeenygraduationproject.eshfeeny.productsAdapter.ProductCategoryAdapter
import com.example.eshfeenygraduationproject.eshfeeny.util.MedicinsCategories
import com.example.eshfeenygraduationproject.eshfeeny.viewmodel.ProductViewModel
import com.example.eshfeenygraduationproject.eshfeeny.viewmodel.ProductViewModelFactory
import com.example.eshfeenygraduationproject.eshfeeny.viewmodel.UserViewModel
import com.google.android.material.chip.Chip

class ProductCategoryFragment : Fragment() {

    private var selectedChip: Chip? = null

    private var binding: FragmentProductCategoryBinding? = null

    private val args: ProductCategoryFragmentArgs by navArgs()

    private lateinit var productViewModel: ProductViewModel
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProductCategoryBinding.inflate(inflater)

        binding?.medicineRecyclerView?.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        val repo = ProductRepoImpl()
        val viewModelFactory = ProductViewModelFactory(repo)
        productViewModel = ViewModelProvider(this, viewModelFactory)[ProductViewModel::class.java]
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        binding?.backBtn?.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_medicineCategoryFragment_to_homeFragment2)
        }

        var cartProducts: CartResponse
        var favoriteProducts: ProductResponse

        userViewModel.userData.observe(viewLifecycleOwner) { userData ->

            productViewModel.getUserCartItems(userData._id)
            productViewModel.cartItems.observe(viewLifecycleOwner) { cartProductsResponse ->
                cartProductsResponse?.let { notNullCartProducts ->
                    cartProducts = notNullCartProducts

                    productViewModel.getFavoriteProducts(userData._id)
                    productViewModel.favoriteProducts.observe(viewLifecycleOwner) { favoriteProductsResponse ->
                        favoriteProductsResponse?.let { notNullFavoriteProducts ->
                            favoriteProducts = notNullFavoriteProducts

                            when (args.category) {
                                "allMeds" -> {
                                    binding?.categoryTitle?.text = "كل الادوية"
                                    setAllMeds(userData._id, favoriteProducts, cartProducts)
                                }

                                "virusProtection" -> {
                                    binding?.categoryTitle?.text = "الحماية من الفيروسات"
                                    setVirusProtection(userData._id, favoriteProducts, cartProducts)
                                }

                                "motherAndChild" -> {

                                }

                                "womenProducts" -> {

                                }

                                "skinAndHairCare" -> {

                                }

                                "dentalCareBtn" -> {

                                }

                                "menProducts" -> {

                                }
                            }
                        }
                    }
                }
            }
        }

        return binding?.root
    }

    private fun setVirusProtection(
        userId: String,
        favoriteProducts: ProductResponse,
        cartProducts: CartResponse
    ) {
        for (med in MedicinsCategories.virusProtection) {
            val newChip = createChip(getString(med), userId, favoriteProducts, cartProducts)
            binding?.medicineChipGroup?.addView(newChip)
        }
    }

    private fun setAllMeds(
        userId: String,
        favoriteProducts: ProductResponse,
        cartProducts: CartResponse
    ) {
        var isFirstChip = true
        for (med in MedicinsCategories.allMedicines) {
            val newChip = createChip(getString(med), userId, favoriteProducts, cartProducts)
            binding?.medicineChipGroup?.addView(newChip)

            if (isFirstChip) {
                newChip.isSelected = true
                setChipColors(newChip)

                selectedChip = newChip
                isFirstChip = false

                productViewModel.getMedicineForAllMedicines()
                productViewModel.categoriesAllProducts.observe(viewLifecycleOwner) { response ->

                    val adapter = ProductCategoryAdapter(
                        productViewModel,
                        userId,
                        favoriteProducts,
                        cartProducts
                    )

                    adapter.submitList(response)
                    binding?.medicineRecyclerView?.adapter = adapter
                }
            }
        }
    }

    private fun createChip(
        name: String,
        userId: String,
        favoriteProducts: ProductResponse,
        cartProducts: CartResponse
    ): Chip {
        val chip = Chip(context)
        chip.text = name

        setChipColors(chip)

        chip.setOnClickListener {
            if (selectedChip != it) {
                // Deselect the previously selected chip
                selectedChip?.let { previousSelectedChip ->

                    previousSelectedChip.isSelected = false
                    setChipColors(previousSelectedChip)
                }

                chip.isSelected = true
                setChipColors(chip)

                selectedChip = chip

                productViewModel.getProductsFromRemote(name)
                productViewModel.remoteProducts.observe(viewLifecycleOwner) { response ->

                    val adapter = ProductCategoryAdapter(
                        productViewModel,
                        userId,
                        favoriteProducts,
                        cartProducts
                    )
                    adapter.submitList(response.body())

                    binding?.medicineRecyclerView?.adapter = adapter
                }
                if (chip.text == getString(R.string.allMedicines)) {
                    productViewModel.getMedicineForAllMedicines()
                    productViewModel.categoriesAllProducts.observe(viewLifecycleOwner) { response ->
                        Log.i("chip Test", response.toString())
                        val adapter = ProductCategoryAdapter(
                            productViewModel,
                            userId,
                            favoriteProducts,
                            cartProducts
                        )
                        adapter.submitList(response)
                        binding?.medicineRecyclerView?.adapter = adapter
                    }
                }
            }
        }

        return chip
    }

    private fun setChipColors(chip: Chip) {
        val textColor = if (chip.isSelected) R.color.blue_main else R.color.text_color
        val bgColor = if (chip.isSelected) R.color.chips_color2 else R.color.chips_color
        val strokeColor =
            if (chip.isSelected) R.color.chip_stroke_selected else R.color.chip_stroke_notSelected

        chip.setTextColor(ContextCompat.getColor(requireContext(), textColor))
        chip.chipBackgroundColor =
            ColorStateList.valueOf(ContextCompat.getColor(requireContext(), bgColor))
        chip.chipStrokeColor =
            ColorStateList.valueOf(ContextCompat.getColor(requireContext(), strokeColor))

        chip.chipStrokeWidth = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            1f,
            context?.resources?.displayMetrics
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}