package com.example.eshfeenygraduationproject.eshfeeny.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.denzcoskun.imageslider.constants.*
import com.denzcoskun.imageslider.models.SlideModel
import com.example.data.repository.ProductRepoImpl
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentHomeBinding
import com.example.eshfeenygraduationproject.eshfeeny.productsAdapter.ProductHomeAdapter
import com.example.eshfeenygraduationproject.eshfeeny.searchForProducts.*
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.*


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

        val medicineRepo = ProductRepoImpl()
        val viewModelFactory = ProductViewModelFactory(medicineRepo)


        val imgList = ArrayList<SlideModel>()
        imgList.add(SlideModel("https://cdn.discordapp.com/attachments/981587143094845490/1095498318089572432/flip_img1.png"))
        imgList.add(SlideModel("https://cdn.discordapp.com/attachments/981587143094845490/1095498318370586674/flip_img2.png"))
        imgList.add(SlideModel("https://cdn.discordapp.com/attachments/981587143094845490/1095498318664192031/flip_img3.png"))
        imgList.add(SlideModel("https://cdn.discordapp.com/attachments/981587143094845490/1095498318932623361/flip_img4.png"))
        imgList.add(SlideModel("https://cdn.discordapp.com/attachments/981587143094845490/1095498319305912380/flip_img5.png"))

        binding?.imageSLider?.setImageList(imgList, ScaleTypes.FIT)
        binding?.imageSLider?.setSlideAnimation(AnimationTypes.DEPTH_SLIDE)

        //To open Roshta fragment

        //To open Search Medicine Fragment
        binding?.searchForMedsBtn?.setOnClickListener {
            navigateToRightCategory("allMeds", it)
        }

        //To open Dental care Fragment
        binding?.dentalCareBtn?.setOnClickListener {
            navigateToRightCategory("dentalCare", it)
        }

        //To open Men's products Fragment
        binding?.menProductsBtn?.setOnClickListener {
            navigateToRightCategory("menProducts", it)
        }

        //To open Women's products Fragment
        binding?.womenProductsBtn?.setOnClickListener {
            navigateToRightCategory("womenProducts", it)
        }

        //To open Mother and Child Fragment
        binding?.motherAndChildBtn?.setOnClickListener {
            navigateToRightCategory("motherAndChild", it)
        }

        //To open Virus Protection Fragment
        binding?.virusProtectionBtn?.setOnClickListener {
            navigateToRightCategory("virusProtection", it)
        }

        //To open Skin and hair care Fragment
        binding?.skinAndHairCareBtn?.setOnClickListener {
            navigateToRightCategory("skinAndHairCare", it)
        }

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        productViewModel = ViewModelProvider(this, viewModelFactory)[ProductViewModel::class.java]

        userViewModel.userData.observe(viewLifecycleOwner) { userData ->
            val userID = userData._id
            productViewModel.getFavoriteProducts(userID)
            productViewModel.favoriteProducts.observe(viewLifecycleOwner) { favoriteProducts ->

                productViewModel.getUserCartItems(userID)
                productViewModel.cartItems.observe(viewLifecycleOwner) { cartItems ->

                    productViewModel.getProductType("العناية بالبشرة و الشعر")
                    productViewModel.allTypeProducts.observe(viewLifecycleOwner) {

                        val adapter = ProductHomeAdapter(
                            productViewModel,
                            userID,
                            favoriteProducts,
                            cartItems
                        )
                        binding?.summerNeedsRecyclerView?.adapter = adapter

                        adapter.submitList(it)

                        Log.i("home Fragment", it.toString())
                    }

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
                        Log.i("home Fragment", it.body().toString())
                    }

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
                        Log.i("home Fragment", it.body().toString())
                    }
                }
            }
        }
        binding?.addRoshtaPhotoId?.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment2_to_roshtaFragment)
        }

        binding?.insuranceCompanyBtn?.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment2_to_insuranceCardFragment)
        }
        return binding?.root
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