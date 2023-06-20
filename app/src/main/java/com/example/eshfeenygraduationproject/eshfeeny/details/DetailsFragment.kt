package com.example.eshfeenygraduationproject.eshfeeny.details


import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.*
import com.example.data.local.db.user.model.UserInfo
import com.example.data.repository.ProductRepoImpl
import com.example.domain.entity.patchRequestVar.PatchString
import com.example.domain.entity.pharmacySendRequest.FindNearestPharmacy
import com.example.domain.entity.product.*
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentDetailsBinding
import com.example.eshfeenygraduationproject.eshfeeny.productsAdapter.UseCaseAdapter
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.viewModel.*
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.viewModelFactory.ProductViewModelFactory
import com.example.eshfeenygraduationproject.eshfeeny.util.loadUrl
import com.varunest.sparkbutton.SparkEventListener


class DetailsFragment : Fragment() {

    private var binding: FragmentDetailsBinding? = null
    private lateinit var productViewModel: ProductViewModel
    private lateinit var _favoriteProducts: ProductResponse
    private val args: DetailsFragmentArgs by navArgs()
    private lateinit var userViewModel: UserViewModel
    private var itemCount = 1
    private lateinit var userId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDetailsBinding.inflate(inflater, container, false)

        initializeViewModels()

        userViewModel.userData.observe(viewLifecycleOwner) { userData ->

            userId = userData._id.toString()
            if (userData.state != "guest") {
                userData._id?.let { productViewModel.getFavoriteProducts(it) }
                productViewModel.favoriteProducts.observe(viewLifecycleOwner) { favoriteProducts ->

                    _favoriteProducts = favoriteProducts

                    productViewModel.getProductFromRemote(args.Id)
                    productViewModel.productDetails.observe(viewLifecycleOwner) { productDetails ->

                        stopShimmer()

                        setFavoriteItem(productDetails, favoriteProducts, userData)

                        setDataToViews(productDetails)

                        checkItemInCart(userData)

                        decreaseItemFromCart(userData, productDetails)

                        increaseItemInCart(userData, productDetails)

                        binding?.productAmount?.text = itemCount.toString()

                        shareProduct(productDetails)

                        productDetails?.let { productResponse ->
                            setAdapters(productResponse)
                        }
                    }
                }
            } else {
                productViewModel.getProductFromRemote(args.Id)
                productViewModel.productDetails.observe(viewLifecycleOwner) { productDetails ->
                    shareProduct(productDetails)
                    stopShimmer()
                    binding?.favoriteImgView?.isEnabled = false
                    setDataToViews(productDetails)
                    productDetails?.let { productResponse ->
                        setAdapters(productResponse)
                    }
                }
            }
        }

        binding?.productAlternativeCard?.setOnClickListener {

            val bottomSheet =
                AlternativeFragment(args.Id, productViewModel, userId)
            bottomSheet.show(childFragmentManager, "AlternativeFragment")
        }

        binding?.productLocationCard?.setOnClickListener {
            val listItems: List<String> = listOf(args.Id)
            val action = DetailsFragmentDirections.actionDetailsFragmentToMapsFragment(
                FindNearestPharmacy(listItems), "Details"
            )
            findNavController().navigate(action)
        }

        // Inflate the layout for this fragment
        return binding?.root
    }

    private fun shareProduct(productDetails: ProductResponseItem) {
        binding?.shareProductCard?.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.setType("text/plain")
            intent.putExtra(Intent.EXTRA_SUBJECT, "Check out this product")
            intent.putExtra(
                Intent.EXTRA_TEXT,
                "أسم المنتج: ${productDetails.nameAr}\nوصف المنتج: ${productDetails.description}\nسعر المنتج: ${productDetails.price} جنية\nلينك المنتج: https://eshfeeny.live/product/${args.Id}"
            )
        }
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
        userData: UserInfo, productDetails: ProductResponseItem
    ) {
        binding?.productIncrementBtn?.setOnClickListener {
            itemCount++
            userData._id?.let { it1 ->
                productViewModel.incrementProductNumberInCart(
                    it1,
                    productDetails._id
                )
            }
            binding?.productAmount?.text = itemCount.toString()
        }
    }

    private fun decreaseItemFromCart(
        userData: UserInfo, productDetails: ProductResponseItem
    ) {
        binding?.decrementBtn?.setOnClickListener {
            if (itemCount == 1) {
                binding?.itemFunctionsLayout?.visibility = View.GONE
                binding?.add2CartBtn?.visibility = View.VISIBLE
                userData._id?.let { it1 ->
                    productViewModel.removeProductFromCart(
                        it1, productDetails._id
                    )
                }
            } else {
                itemCount--
                userData._id?.let { it1 ->
                    productViewModel.decrementProductNumberInCart(
                        it1, productDetails._id
                    )
                }
                binding?.productAmount?.text = itemCount.toString()
            }
        }
    }

    private fun checkItemInCart(userData: UserInfo) {
        binding?.add2CartBtn?.setOnClickListener { btn ->
            userData._id?.let { productViewModel.getNumberOfItemInCart(it, args.Id) }
            productViewModel.productNumber.observe(viewLifecycleOwner) { productItemCount ->
                if (productItemCount == 0) {
                    userData._id?.let {
                        productViewModel.addProductToCart(
                            it, PatchString(args.Id)
                        )
                    }
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

        binding?.idTxtPriceDetails?.text = "${productDetails?.price.toString()} جنيه"
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
        if (productDetails in favoriteProducts) binding?.favoriteImgView?.isChecked = true

        binding?.favoriteImgView?.setEventListener(object : SparkEventListener {
            override fun onEvent(button: ImageView?, buttonState: Boolean) {
                if (buttonState) {
                    userData._id?.let {
                        productViewModel.addMedicineToFavorites(
                            it, PatchString(args.Id)
                        )
                    }
                } else {
                    userData._id?.let {
                        productViewModel.deleteFavoriteProduct(
                            it, args.Id
                        )
                    }
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