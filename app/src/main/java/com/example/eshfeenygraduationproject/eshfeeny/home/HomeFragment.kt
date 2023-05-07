package com.example.eshfeenygraduationproject.eshfeeny.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.denzcoskun.imageslider.constants.*
import com.denzcoskun.imageslider.models.SlideModel
import com.example.data.repository.ProductRepoImpl
import com.example.domain.entity.cart.CartResponse
import com.example.domain.entity.product.ProductResponse
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentHomeBinding
import com.example.eshfeenygraduationproject.eshfeeny.productsAdapter.ProductHomeAdapter
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.*
import com.example.eshfeenygraduationproject.eshfeeny.searchForProducts.*
import com.example.eshfeenygraduationproject.eshfeeny.util.loadUrl

class HomeFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var productViewModel: ProductViewModel
    private var binding: FragmentHomeBinding? = null

    @SuppressLint("LongLogTag")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)

        initializingFragment()

        return binding?.root
    }

    private fun initializingFragment() {
        settingImagesForImageSlider()
        fragmentNavigation()
        viewModelsInitlization()
        getUserId()
    }

    private fun fragmentNavigation() {
        navigateToCategories()
        navigate2InsuranceCompany()
        navigateToRoshtaFragment()
    }

    private fun navigateToRoshtaFragment() {
        binding?.addRoshtaPhotoId?.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment2_to_roshtaFragment)
        }
    }

    private fun getUserId() {
        userViewModel.userData.observe(viewLifecycleOwner) { userData ->
            val userID = userData._id
            getFavoriteProducts(userID)
        }
    }

    private fun getFavoriteProducts(userID: String) {
        productViewModel.getFavoriteProducts(userID)
        productViewModel.favoriteProducts.observe(viewLifecycleOwner) { favoriteProducts ->

            getCartItems(userID, favoriteProducts)
        }
    }

    private fun getCartItems(
        userID: String,
        favoriteProducts: ProductResponse
    ) {
        productViewModel.getUserCartItems(userID)
        productViewModel.cartItems.observe(viewLifecycleOwner) { cartItems ->

            setSummerNeedsRecyclerView(userID, favoriteProducts, cartItems)

            setForBetterHealthRecyclerView(userID, favoriteProducts, cartItems)

            setSugarAlternativeRecyclerView(userID, favoriteProducts, cartItems)
        }
    }

    private fun setSugarAlternativeRecyclerView(
        userID: String,
        favoriteProducts: ProductResponse,
        cartItems: CartResponse
    ) {
        productViewModel.getProductsFromRemoteAlt(getString(R.string.sugarAlternitave))
        productViewModel.remoteProductsAlt.observe(viewLifecycleOwner) {

            val adapter2 = ProductHomeAdapter(
                productViewModel,
                userID,
                favoriteProducts,
                cartItems
            )
            binding?.sugarAlternativeRecyclerView?.adapter = adapter2
            adapter2.submitList(it.body())
        }
    }

    private fun setForBetterHealthRecyclerView(
        userID: String,
        favoriteProducts: ProductResponse,
        cartItems: CartResponse
    ) {
        productViewModel.getProductsFromRemote(getString(R.string.vitaminsAndNutritionalSupplements))
        productViewModel.remoteProducts.observe(viewLifecycleOwner) {

            val adapter1 = ProductHomeAdapter(
                productViewModel,
                userID,
                favoriteProducts,
                cartItems
            )
            binding?.forBetterHealthRecyclerView?.adapter = adapter1

            adapter1.submitList(it.body())
        }
    }

    private fun setSummerNeedsRecyclerView(
        userID: String,
        favoriteProducts: ProductResponse,
        cartItems: CartResponse
    ) {
        productViewModel.getProductType("العناية بالبشرة و الشعر")
        productViewModel.allTypeProducts.observe(viewLifecycleOwner) {

            stopShimmerLayout()
            val adapter = ProductHomeAdapter(
                productViewModel,
                userID,
                favoriteProducts,
                cartItems
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
            navigateToRightCategory("allMeds", it)
        }

        binding?.dentalCareBtn?.setOnClickListener {
            navigateToRightCategory("dentalCare", it)
        }

        binding?.menProductsBtn?.setOnClickListener {
            navigateToRightCategory("menProducts", it)
        }

        binding?.womenProductsBtn?.setOnClickListener {
            navigateToRightCategory("womenProducts", it)
        }

        binding?.motherAndChildBtn?.setOnClickListener {
            navigateToRightCategory("motherAndChild", it)
        }

        binding?.virusProtectionBtn?.setOnClickListener {
            navigateToRightCategory("virusProtection", it)
        }

        binding?.skinAndHairCareBtn?.setOnClickListener {
            navigateToRightCategory("skinAndHairCare", it)
        }
    }

    private fun settingImagesForImageSlider() {
        val imgList = ArrayList<SlideModel>()
        imgList.add(SlideModel("https://cdn.discordapp.com/attachments/981587143094845490/1095498318089572432/flip_img1.png"))
        imgList.add(SlideModel("https://cdn.discordapp.com/attachments/981587143094845490/1095498318370586674/flip_img2.png"))
        imgList.add(SlideModel("https://cdn.discordapp.com/attachments/981587143094845490/1095498318664192031/flip_img3.png"))
        imgList.add(SlideModel("https://cdn.discordapp.com/attachments/981587143094845490/1095498318932623361/flip_img4.png"))
        imgList.add(SlideModel("https://cdn.discordapp.com/attachments/981587143094845490/1095498319305912380/flip_img5.png"))

        binding?.imageSLider?.setImageList(imgList, ScaleTypes.FIT)
        binding?.imageSLider?.setSlideAnimation(AnimationTypes.DEPTH_SLIDE)

        binding?.dentalCareImage?.loadUrl("https://cdn.discordapp.com/attachments/981587143094845490/1104449020455288832/Dental_Care.png")
        binding?.menProductsImage?.loadUrl("https://cdn.discordapp.com/attachments/981587143094845490/1104450147393482822/Men_Products.png")
        binding?.womenProductsImage?.loadUrl("https://cdn.discordapp.com/attachments/981587143094845490/1104429226066710538/women_products.png")
        binding?.motherAndChildImage?.loadUrl("https://cdn.discordapp.com/attachments/981587143094845490/1104430798427394098/mother_and_child.png")
        binding?.virusProtectionImage?.loadUrl("https://cdn.discordapp.com/attachments/981587143094845490/1104442581686943845/virus_protection.png")
        binding?.skinAndHairCareImage?.loadUrl("https://cdn.discordapp.com/attachments/981587143094845490/1104444095985893416/Skin_And_Hair_Care.png")
    }

    private fun viewModelsInitlization() {
        val medicineRepo = ProductRepoImpl()
        val viewModelFactory = ProductViewModelFactory(medicineRepo)
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        productViewModel = ViewModelProvider(this, viewModelFactory)[ProductViewModel::class.java]
    }

    private fun stopShimmerLayout() {
        binding?.shimmerLayoutHome?.stopShimmer()
        binding?.shimmerLayoutHome?.visibility = View.GONE
        binding?.homePage?.visibility = View.VISIBLE
    }

    private fun navigateToRightCategory(categoryName: String, view: View) {
        val action =
            HomeFragmentDirections.actionHomeFragment2ToMedicineCategoryFragment(categoryName)
        Navigation.findNavController(view).navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.summerNeedsRecyclerView?.adapter = null
        binding?.forBetterHealthRecyclerView?.adapter = null
        binding?.sugarAlternativeRecyclerView?.adapter = null
    }
}