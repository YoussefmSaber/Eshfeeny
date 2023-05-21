package com.example.eshfeenygraduationproject.eshfeeny.details


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.data.local.db.user.model.UserInfo
import com.example.data.repository.ProductRepoImpl
import com.example.domain.entity.patchRequestVar.PatchString
import com.example.domain.entity.product.ProductResponse
import com.example.domain.entity.product.ProductResponseItem
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentDetailsBinding
import com.example.eshfeenygraduationproject.eshfeeny.productsAdapter.UseCaseAdapter
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.ProductViewModel
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.ProductViewModelFactory
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.UserViewModel
import com.example.eshfeenygraduationproject.eshfeeny.util.loadUrl
import com.varunest.sparkbutton.SparkEventListener


class DetailsFragment : Fragment() {

    private var binding: FragmentDetailsBinding? = null
    private lateinit var productViewModel: ProductViewModel

    private val args: DetailsFragmentArgs by navArgs()
    private lateinit var userViewModel: UserViewModel

    private var isFavorite = false
    private var itemCount = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDetailsBinding.inflate(inflater, container, false)


        initializeViewModels()

        userViewModel.userData.observe(viewLifecycleOwner) { userData ->

            productViewModel.getFavoriteProducts(userData._id)
            productViewModel.favoriteProducts.observe(viewLifecycleOwner) { favoriteProducts ->

                productViewModel.getProductFromRemote(args.Id)
                productViewModel.productDetails.observe(viewLifecycleOwner) { productDetails ->

                    stopShimmer()

                    setFavoriteItem(productDetails, favoriteProducts, userData)

                    setDataToViews(productDetails)

                    checkItemInCart(userData)

                    decreaseItemFromCart(userData, productDetails)

                    increaseItemInCart(userData, productDetails)

                    binding?.productAmount?.text = itemCount.toString()

                    productDetails?.let { productResponse ->
                        setAdapters(productResponse)
                    }
                }
            }
        }

        // Inflate the layout for this fragment
        return binding?.root
    }

    private fun stopShimmer() {
        binding?.shimmerLayout?.stopShimmer()
        binding?.shimmerLayout?.visibility = View.GONE
        binding?.page?.visibility = View.VISIBLE
    }

    private fun initializeViewModels() {
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        val repo = ProductRepoImpl()
        val productViewModelFactory = ProductViewModelFactory(repo)

        productViewModel =
            ViewModelProvider(this, productViewModelFactory)[ProductViewModel::class.java]
    }

    private fun increaseItemInCart(
        userData: UserInfo,
        productDetails: ProductResponseItem
    ) {
        binding?.productIncrementBtn?.setOnClickListener {
            itemCount++
            productViewModel.incrementProductNumberInCart(userData._id, productDetails._id)
            binding?.productAmount?.text = itemCount.toString()
        }
    }

    private fun decreaseItemFromCart(
        userData: UserInfo,
        productDetails: ProductResponseItem
    ) {
        binding?.decrementBtn?.setOnClickListener {
            if (itemCount == 1) {
                binding?.itemFunctionsLayout?.visibility = View.GONE
                binding?.add2CartBtn?.visibility = View.VISIBLE
                productViewModel.removeProductFromCart(
                    userData._id,
                    productDetails._id
                )
            } else {
                itemCount--
                productViewModel.decrementProductNumberInCart(
                    userData._id,
                    productDetails._id
                )
                binding?.productAmount?.text = itemCount.toString()
            }
        }
    }

    private fun checkItemInCart(userData: UserInfo) {
        binding?.add2CartBtn?.setOnClickListener { btn ->
            productViewModel.getNumberOfItemInCart(userData._id, args.Id)
            productViewModel.productNumber.observe(viewLifecycleOwner) { productItemCount ->
                if (productItemCount == 0) {
                    productViewModel.addProductToCart(
                        userData._id,
                        PatchString(args.Id)
                    )
                } else {
                    itemCount = productItemCount
                    binding?.productAmount?.text = itemCount.toString()
                }
            }
            btn.visibility = View.GONE
            binding?.itemFunctionsLayout?.visibility = View.VISIBLE
        }
    }

    private fun setDataToViews(productDetails: ProductResponseItem?) {
        binding?.idTxtAmountVolumeDetails?.text =
            "${productDetails?.nameAr} | ${productDetails?.amount} | ${productDetails?.volume}"

        binding?.idTxtPriceDetails?.text =
            "${productDetails?.price.toString()} جنيه"
        binding?.idTxtDescrection?.text = productDetails?.description

        productDetails?.images?.get(0)?.let {
            binding?.idImgMedicineDetails?.loadUrl(it)
        }
    }

    private fun setFavoriteItem(
        productDetails: ProductResponseItem,
        favoriteProducts: ProductResponse,
        userData: UserInfo
    ) {
        if (productDetails in favoriteProducts)
            binding?.favoriteImgView?.isChecked = true

        binding?.favoriteImgView?.setEventListener(object : SparkEventListener {
            override fun onEvent(button: ImageView?, buttonState: Boolean) {
                if (buttonState) {
                    productViewModel.addMedicineToFavorites(
                        userData._id,
                        PatchString(args.Id)
                    )
                } else {
                    productViewModel.deleteFavoriteProduct(
                        userData._id,
                        args.Id
                    )
                }
            }

            override fun onEventAnimationEnd(button: ImageView?, buttonState: Boolean) {

            }

            override fun onEventAnimationStart(button: ImageView?, buttonState: Boolean) {

            }

        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupExitButton()
    }

    private fun setupExitButton() {
        binding?.exit1BtnId?.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_detailsFragment_to_homeFragment2)
        }
    }

    private fun setAdapters(productResponse: ProductResponseItem) {
        binding?.idRvUseCaseDetails?.adapter = UseCaseAdapter(productResponse.useCases)
        binding?.idRvSideEffectDetails?.adapter = UseCaseAdapter(productResponse.sideEffects)
        binding?.idRvUsageDetails?.adapter = UseCaseAdapter(productResponse.usage)
        binding?.idRvWarningDetails?.adapter = UseCaseAdapter(productResponse.warning)
    }
}