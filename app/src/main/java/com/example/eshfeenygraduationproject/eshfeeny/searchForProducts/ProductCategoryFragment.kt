package com.example.eshfeenygraduationproject.eshfeeny.searchForProducts

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.data.local.db.user.model.UserInfo
import com.example.data.repository.ProductRepoImpl
import com.example.domain.entity.cart.CartResponse
import com.example.domain.entity.product.ProductResponse
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentProductCategoryBinding
import com.example.eshfeenygraduationproject.eshfeeny.cameraBottomSheet.ImageBottomSheetFragment
import com.example.eshfeenygraduationproject.eshfeeny.productsAdapter.ProductCategoryAdapter
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.viewModel.ProductViewModel
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.viewModel.UserViewModel
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.viewModelFactory.ProductViewModelFactory
import com.example.eshfeenygraduationproject.eshfeeny.search.SearchAdapter
import com.example.eshfeenygraduationproject.eshfeeny.util.MedicinsCategories
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
        setCategoryTitle(args.category)

        binding?.searchBar?.setOnMenuItemClickListener{
            when (it.itemId) {
                R.id.searchUsingCamera -> {
                    Log.i("image Capture", "Item Clicked")
                    val bottomSheet =
                        ImageBottomSheetFragment("category")
                    bottomSheet.show(childFragmentManager, "ImageBottomSheetFragment")
                    true
                }

                else -> false
            }
        }

        binding?.medicineRecyclerView?.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        setupViewModel()
        navigate2HomeFragment()
        settingSearch()
        var cartProducts: CartResponse
        var favoriteProducts: ProductResponse

        userViewModel.userData.observe(viewLifecycleOwner) { userData ->
            userData._id?.let { productViewModel.getUserCartItems(it) }
            productViewModel.cartItems.observe(viewLifecycleOwner) { cartProductsResponse ->
                cartProductsResponse?.let { notNullCartProducts ->
                    cartProducts = notNullCartProducts

                    userData._id?.let { productViewModel.getFavoriteProducts(it) }
                    productViewModel.favoriteProducts.observe(viewLifecycleOwner) { favoriteProductsResponse ->
                        favoriteProductsResponse?.let { notNullFavoriteProducts ->
                            favoriteProducts = notNullFavoriteProducts

                            setCategory(userData, favoriteProducts, cartProducts)
                        }
                    }
                }
            }
        }

        return binding?.root
    }

    private fun setCategoryTitle(categoryType: String) {
        when (categoryType) {
            "allMeds" ->
                binding?.categoryTitle?.text = "كل الادوية"

            "dentalCare" ->
                binding?.categoryTitle?.text = "العناية بالاسنان"

            "menProducts" ->
                binding?.categoryTitle?.text = "منتجات الرجال"

            "womenProducts" ->
                binding?.categoryTitle?.text = "منتجات المرأة"

            "motherAndChild" ->
                binding?.categoryTitle?.text = "الأم و الطفل"

            "virusProtection" ->
                binding?.categoryTitle?.text = "الحماية من الفيروسات"

            "skinAndHairCare" ->
                binding?.categoryTitle?.text = "العناية بالبشرة و الشعر"
        }
    }

    private fun setCategory(
        userData: UserInfo,
        favoriteProducts: ProductResponse,
        cartProducts: CartResponse
    ) {
        when (args.category) {
            "allMeds" -> {
                when (args.chipName) {
                    "default" -> userData._id?.let {
                        setChipSearch(
                            it,
                            favoriteProducts,
                            cartProducts,
                            MedicinsCategories.allMedicines,
                            "الأدوية",
                            "default"
                        )
                    }
                    "sugar" -> userData._id?.let {
                        setChipSearch(
                            it,
                            favoriteProducts,
                            cartProducts,
                            MedicinsCategories.allMedicines,
                            "الأدوية",
                            "sugar"
                        )
                    }
                    "vitamins" -> userData._id?.let {
                        setChipSearch(
                            it,
                            favoriteProducts,
                            cartProducts,
                            MedicinsCategories.allMedicines,
                            "الأدوية",
                            "vitamins"
                        )
                    }
                }
            }

            "dentalCare" -> {
                userData._id?.let {
                    setChipSearch(
                        it,
                        favoriteProducts,
                        cartProducts,
                        MedicinsCategories.dentalCare,
                        "العناية بالاسنان",
                        "default"
                    )
                }
            }

            "menProducts" -> {
                userData._id?.let {
                    setChipSearch(
                        it,
                        favoriteProducts,
                        cartProducts,
                        MedicinsCategories.menProducts,
                        "منتجات الرجال",
                        "default"
                    )
                }
            }

            "womenProducts" -> {
                userData._id?.let {
                    setChipSearch(
                        it,
                        favoriteProducts,
                        cartProducts,
                        MedicinsCategories.womenProducts,
                        "منتجات المرأة",
                        "default"
                    )
                }
            }

            "motherAndChild" -> {
                userData._id?.let {
                    setChipSearch(
                        it,
                        favoriteProducts,
                        cartProducts,
                        MedicinsCategories.motherAndChild,
                        "الأم و الطفل",
                        "default"
                    )
                }
            }

            "virusProtection" -> {
                userData._id?.let {
                    setChipSearch(
                        it,
                        favoriteProducts,
                        cartProducts,
                        MedicinsCategories.virusProtection,
                        "الحمايه من الفيروسات",
                        "default"
                    )
                }
            }

            "skinAndHairCare" -> {
                userData._id?.let {
                    setChipSearch(
                        it,
                        favoriteProducts,
                        cartProducts,
                        MedicinsCategories.skinAndHair,
                        "العناية بالبشرة و الشعر",
                        "default"
                    )
                }
            }
        }
    }

    private fun navigate2HomeFragment() {
        binding?.backBtn?.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_medicineCategoryFragment_to_homeFragment2)
        }
    }

    private fun setupViewModel() {
        val repo = ProductRepoImpl()
        val viewModelFactory = ProductViewModelFactory(repo)
        productViewModel =
            ViewModelProvider(this, viewModelFactory)[ProductViewModel::class.java]
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
    }

    private fun setChipSearch(
        userId: String,
        favoriteProducts: ProductResponse,
        cartProducts: CartResponse,
        productCategories: List<Int>,
        productType: String,
        chipName: String
    ) {
        for (med in productCategories) {
            val newChip = createChip(getString(med), userId, favoriteProducts, cartProducts)
            binding?.medicineChipGroup?.addView(newChip)

            when (chipName) {
                "default" -> {
                    if (med == productCategories.first()) {
                        newChip.isSelected = true
                        setChipColors(newChip)

                        selectedChip = newChip

                        productViewModel.getProductType(productType)
                        productViewModel.allTypeProducts.observe(viewLifecycleOwner) { response ->
                            stopShimmerLoading()
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
                "sugar" -> {
                    if (getString(med) == getString(R.string.sugarAlternitave)) {
                        newChip.isSelected = true
                        setChipColors(newChip)

                        selectedChip = newChip

                        val adapter = ProductCategoryAdapter(
                            productViewModel,
                            userId,
                            favoriteProducts,
                            cartProducts
                        )

                        productViewModel.getProductsFromRemote(getString(med))
                        productViewModel.remoteProducts.observe(viewLifecycleOwner) { response ->
                            stopShimmerLoading()
                            adapter.submitList(response.body())
                            binding?.medicineRecyclerView?.adapter = adapter
                        }
                    }
                }
                "vitamins" -> {
                    if (getString(med) == getString(R.string.vitaminsAndNutritionalSupplements)) {
                        newChip.isSelected = true
                        setChipColors(newChip)

                        selectedChip = newChip

                        val adapter = ProductCategoryAdapter(
                            productViewModel,
                            userId,
                            favoriteProducts,
                            cartProducts
                        )

                        productViewModel.getProductsFromRemote(getString(med))
                        productViewModel.remoteProducts.observe(viewLifecycleOwner) { response ->
                            stopShimmerLoading()
                            adapter.submitList(response.body())
                            binding?.medicineRecyclerView?.adapter = adapter
                        }
                    }
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
        chip.chipCornerRadius = 50.0f
        chip.setTextAppearance(R.style.defaultTextStyle)

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

                val adapter = ProductCategoryAdapter(
                    productViewModel,
                    userId,
                    favoriteProducts,
                    cartProducts
                )

                val chipTypes = setOf(
                    getString(R.string.allMedicines),
                    getString(R.string.allDentalCare),
                    getString(R.string.allMenProducts),
                    getString(R.string.allWomenProducts),
                    getString(R.string.allMotherAndChild),
                    getString(R.string.allVirusProtection),
                    getString(R.string.allSkinAndHairProducts)
                )

                if (isChipNameType(name, chipTypes)) {
                    val typeName: String = setTypeName(name)
                    productViewModel.getProductType(typeName)
                    productViewModel.allTypeProducts.observe(viewLifecycleOwner) { response ->
                        stopShimmerLoading()
                        adapter.submitList(response)
                        binding?.medicineRecyclerView?.adapter = adapter
                    }
                } else {

                    productViewModel.getProductsFromRemote(name)
                    productViewModel.remoteProducts.observe(viewLifecycleOwner) { response ->
                        stopShimmerLoading()
                        adapter.submitList(response.body())
                        binding?.medicineRecyclerView?.adapter = adapter
                    }
                }
            }
        }
        return chip
    }

    private fun stopShimmerLoading() {
        binding?.shimmerProductCategoryChip?.stopShimmer()
        binding?.shimmerProductCategoryChip?.visibility = View.GONE
        binding?.pageCategory?.visibility = View.VISIBLE
    }

    private fun setTypeName(name: String)
            : String {
        return when (name) {
            getString(R.string.allMedicines) -> "الأدوية"
            getString(R.string.allDentalCare) -> "العناية بالاسنان"
            getString(R.string.allMenProducts) -> "منتجات الرجال"
            getString(R.string.allWomenProducts) -> "منتجات المرأة"
            getString(R.string.allMotherAndChild) -> "الأم و الطفل"
            getString(R.string.allVirusProtection) -> "الحمايه من الفيروسات"

            else -> "العناية بالبشرة و الشعر"
        }
    }

    private fun isChipNameType(
        searchName: String, chipTypes: Set<String>
    )
            : Boolean {
        return chipTypes.contains(searchName)
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

    private fun settingSearch() {
        binding?.searchViewText?.editText?.addTextChangedListener(
            object : TextWatcher {

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    // do nothing
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // perform search using the new text
                    val searchText = s.toString()

                    productViewModel.getSearchResults(searchText)
                    productViewModel.searchResults.observe(viewLifecycleOwner) {

                        val adapter = SearchAdapter("home")
                        adapter.submitList(it)
                        binding?.searchResultsRecyclerView?.adapter = adapter
                    }
                }

                override fun afterTextChanged(s: Editable?) {

                }
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}