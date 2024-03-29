package com.example.eshfeenygraduationproject.eshfeeny.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.denzcoskun.imageslider.constants.AnimationTypes
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.data.repository.ProductRepoImpl
import com.example.domain.entity.cart.CartResponse
import com.example.domain.entity.product.ProductResponse
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentHomeBinding
import com.example.eshfeenygraduationproject.eshfeeny.cameraBottomSheet.ImageBottomSheetFragment
import com.example.eshfeenygraduationproject.eshfeeny.productsAdapter.ProductHomeAdapter
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.viewModel.ProductViewModel
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.viewModel.UserViewModel
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.viewModelFactory.ProductViewModelFactory
import com.example.eshfeenygraduationproject.eshfeeny.search.SearchAdapter
import com.example.eshfeenygraduationproject.eshfeeny.util.loadUrl

class HomeFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var productViewModel: ProductViewModel
    private var binding: FragmentHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)

        initializingFragment()
        binding!!.searchBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.searchUsingCamera -> {
                    Log.i("image Capture", "Item Clicked")
                    val bottomSheet =
                        ImageBottomSheetFragment("home")
                    bottomSheet.show(childFragmentManager, "ImageBottomSheetFragment")
                    true
                }

                else -> false
            }
        }
        return binding?.root
    }

    private fun initializingFragment() {
        loadingImages()
        fragmentNavigation()
        viewModelsInitialization()
        getUserId()
        settingSearch()
    }

    private fun fragmentNavigation() {
        navigateToCategories()
        navigate2InsuranceCompany()
        navigateToRoshtaFragment()
        navigateToBrandsFragment()
    }

    private fun navigateToBrandsFragment() {
        binding?.showAllBrands?.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment2_to_brandsFragment)
        }
    }

    private fun navigateToRoshtaFragment() {
        binding?.addRoshtaPhotoId?.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment2_to_roshtaFragment)
        }
    }

    private fun getUserId() {
        userViewModel.userData.observe(viewLifecycleOwner) { userData ->
            val userID = userData._id
            getFavoriteProducts(userID, userData.state)
        }
    }

    private fun getFavoriteProducts(userID: String?, state: String) {
        if (state != "guest") {
            Log.d("HomeFragment", userID.toString())
            if (userID != null) {
                productViewModel.getFavoriteProducts(userID)
                productViewModel.favoriteProducts.observe(viewLifecycleOwner) { favoriteProducts ->
                    getCartItems(userID, favoriteProducts, state)
                }
            }

        } else {
            Log.d("HomeFragment", userID.toString())
            getCartItems(null, null, state)
        }

    }

    private fun getCartItems(
        userID: String?,
        favoriteProducts: ProductResponse?,
        state: String
    ) {
        if (state != "guest") {
            Log.d("HomeFragment", userID.toString())
            if (userID != null) {
                productViewModel.getUserCartItems(userID)
            }
            productViewModel.cartItems.observe(viewLifecycleOwner) { cartItems ->

                if (favoriteProducts != null) {
                    if (userID != null) {
                        setSummerNeedsRecyclerView(userID, favoriteProducts, cartItems, state)
                        setForBetterHealthRecyclerView(userID, favoriteProducts, cartItems, state)
                        setSugarAlternativeRecyclerView(userID, favoriteProducts, cartItems, state)
                    }
                }
            }
        } else {
            Log.d("HomeFragment", userID.toString())
            setSummerNeedsRecyclerView(null, null, null, state)
            setForBetterHealthRecyclerView(null, null, null, state)
            setSugarAlternativeRecyclerView(null, null, null, state)
        }

    }

    private fun setSugarAlternativeRecyclerView(
        userID: String?,
        favoriteProducts: ProductResponse?,
        cartItems: CartResponse?,
        state: String
    ) {
        productViewModel.getProductsFromRemoteAlt(getString(R.string.sugarAlternitave))
        productViewModel.remoteProductsAlt.observe(viewLifecycleOwner) {

            val adapter2 = ProductHomeAdapter(
                productViewModel,
                userID,
                favoriteProducts,
                cartItems,
                state
            )
            binding?.sugarAlternativeRecyclerView?.adapter = adapter2
            adapter2.submitList(it.body())
        }
    }

    private fun setForBetterHealthRecyclerView(
        userID: String?,
        favoriteProducts: ProductResponse?,
        cartItems: CartResponse?,
        state: String
    ) {
        productViewModel.getProductsFromRemote(getString(R.string.vitaminsAndNutritionalSupplements))
        productViewModel.remoteProducts.observe(viewLifecycleOwner) {

            val adapter1 = ProductHomeAdapter(
                productViewModel,
                userID,
                favoriteProducts,
                cartItems,
                state
            )
            binding?.forBetterHealthRecyclerView?.adapter = adapter1

            adapter1.submitList(it.body())
        }
    }

    private fun setSummerNeedsRecyclerView(
        userID: String?,
        favoriteProducts: ProductResponse?,
        cartItems: CartResponse?,
        state: String
    ) {
        Log.d("HomeFragment", userID.toString())
        productViewModel.getProductType("العناية بالبشرة و الشعر")
        productViewModel.allTypeProducts.observe(viewLifecycleOwner) {
            Log.d("HomeFragment", it.toString())
            stopShimmerLayout()
            val adapter = ProductHomeAdapter(
                productViewModel,
                userID,
                favoriteProducts,
                cartItems,
                state
            )
            binding?.summerNeedsRecyclerView?.adapter = adapter

            adapter.submitList(it.take(12))
        }
    }

    private fun navigate2InsuranceCompany() {
        binding?.chooseInsuranceCompany?.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment2_to_insuranceCardFragment)
        }

        binding?.insuranceCompanyBtn?.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment2_to_insuranceCardFragment)
        }
    }

    private fun navigateToCategories() {
        binding?.searchForMedsBtn?.setOnClickListener {
            navigateToRightCategory("allMeds", "default", it)
        }

        binding?.dentalCareBtn?.setOnClickListener {
            navigateToRightCategory("dentalCare", "default", it)
        }

        binding?.menProductsBtn?.setOnClickListener {
            navigateToRightCategory("menProducts", "default", it)
        }

        binding?.womenProductsBtn?.setOnClickListener {
            navigateToRightCategory("womenProducts", "default", it)
        }

        binding?.motherAndChildBtn?.setOnClickListener {
            navigateToRightCategory("motherAndChild", "default", it)
        }

        binding?.virusProtectionBtn?.setOnClickListener {
            navigateToRightCategory("virusProtection", "default", it)
        }

        binding?.skinAndHairCareBtn?.setOnClickListener {
            navigateToRightCategory("skinAndHairCare", "default", it)
        }

        binding?.selectAllSummerNeedsId?.setOnClickListener {
            navigateToRightCategory("skinAndHairCare", "default", it)
        }

        binding?.selectAllAlternativeToSugarId?.setOnClickListener {
            navigateToRightCategory("allMeds", "sugar", it)
        }

        binding?.selectAllForBetterHealthId?.setOnClickListener {
            navigateToRightCategory("allMeds", "vitamins", it)
        }
    }

    private fun loadingImages() {
        loadingSliderImages()
        loadingImagesForCategorires()
        loadingImagesForBrands()
    }

    private fun loadingImagesForBrands() {
        val brands = listOf(
            Pair(binding?.niveaImage, "Nivea"),
            Pair(binding?.doveImage, "Dove"),
            Pair(binding?.beeslineImage, "Beesline"),
            Pair(binding?.garenierImage, "Garnier"),
            Pair(binding?.larochImage, "LA ROCHE POSAY"),
            Pair(binding?.signalImage, "Signal")
        )

        for ((imageView, brand) in brands) {
            imageView?.apply {
                setOnClickListener {
                    val action =
                        HomeFragmentDirections.actionHomeFragment2ToBrandItemsFragment(brand)
                    findNavController().navigate(action)
                }
            }

            val imageUrl = when (brand) {
                "Nivea" -> "https://media.discordapp.net/attachments/1104897811494993960/1105278415440978041/Nivea.png?width=670&height=670"
                "Dove" -> "https://cdn.discordapp.com/attachments/1104897811494993960/1118528203112333322/1280px-Dove_wordmark.png"
                "Beesline" -> "https://cdn.discordapp.com/attachments/1104897811494993960/1118528791703212102/bee_logo.png"
                "Garnier" -> "https://cdn.discordapp.com/attachments/1104897811494993960/1118528465201799320/2560px-Garnier-Logo.png"
                "LA ROCHE POSAY" -> "https://media.discordapp.net/attachments/1104897811494993960/1105278353889558589/LA_ROCHE_POSAY.png?width=1151&height=494"
                "Signal" -> "https://media.discordapp.net/attachments/1104897811494993960/1105278416619577344/Signal.png?width=1439&height=627"
                else -> null
            }

            if (imageUrl != null) {
                imageView?.loadUrl(imageUrl)
            }
        }
    }

    private fun loadingSliderImages() {
        val imgList = ArrayList<SlideModel>()
        imgList.add(SlideModel("https://cdn.discordapp.com/attachments/981587143094845490/1095498318089572432/flip_img1.png"))
        imgList.add(SlideModel("https://cdn.discordapp.com/attachments/981587143094845490/1095498318370586674/flip_img2.png"))
        imgList.add(SlideModel("https://cdn.discordapp.com/attachments/981587143094845490/1095498318664192031/flip_img3.png"))
        imgList.add(SlideModel("https://cdn.discordapp.com/attachments/981587143094845490/1095498318932623361/flip_img4.png"))
        imgList.add(SlideModel("https://cdn.discordapp.com/attachments/981587143094845490/1095498319305912380/flip_img5.png"))

        binding?.imageSLider?.setImageList(imgList, ScaleTypes.FIT)
        binding?.imageSLider?.setSlideAnimation(AnimationTypes.DEPTH_SLIDE)
    }

    private fun loadingImagesForCategorires() {
        binding?.dentalCareImage?.loadUrl("https://cdn.discordapp.com/attachments/981587143094845490/1104449020455288832/Dental_Care.png")
        binding?.menProductsImage?.loadUrl("https://cdn.discordapp.com/attachments/981587143094845490/1104450147393482822/Men_Products.png")
        binding?.womenProductsImage?.loadUrl("https://cdn.discordapp.com/attachments/981587143094845490/1104429226066710538/women_products.png")
        binding?.motherAndChildImage?.loadUrl("https://cdn.discordapp.com/attachments/981587143094845490/1104430798427394098/mother_and_child.png")
        binding?.virusProtectionImage?.loadUrl("https://cdn.discordapp.com/attachments/981587143094845490/1104442581686943845/virus_protection.png")
        binding?.skinAndHairCareImage?.loadUrl("https://cdn.discordapp.com/attachments/981587143094845490/1104444095985893416/Skin_And_Hair_Care.png")
    }

    private fun viewModelsInitialization() {
        val medicineRepo = ProductRepoImpl()
        val viewModelFactory = ProductViewModelFactory(medicineRepo)
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        productViewModel = ViewModelProvider(this, viewModelFactory)[ProductViewModel::class.java]
    }

    private fun stopShimmerLayout() {
        binding?.shimmerLayoutHome?.stopShimmer()
        binding?.shimmerLayoutHome?.visibility = View.GONE
        binding?.homePage?.visibility = View.VISIBLE
        binding?.searchBar?.visibility = View.VISIBLE
    }

    private fun navigateToRightCategory(categoryName: String, displayType: String, view: View) {
        val action =
            HomeFragmentDirections.actionHomeFragment2ToMedicineCategoryFragment(
                categoryName,
                displayType
            )
        Navigation.findNavController(view).navigate(action)
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
        binding?.summerNeedsRecyclerView?.adapter = null
        binding?.forBetterHealthRecyclerView?.adapter = null
        binding?.sugarAlternativeRecyclerView?.adapter = null
    }
}