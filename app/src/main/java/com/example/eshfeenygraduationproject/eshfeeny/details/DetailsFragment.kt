package com.example.eshfeenygraduationproject.eshfeeny.details


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.data.local.db.user.model.UserInfo
import com.example.data.repository.ProductRepoImpl
import com.example.domain.entity.patchRequestVar.PatchProductId
import com.example.domain.entity.product.ProductResponse
import com.example.domain.entity.product.ProductResponseItem
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentDetailsBinding
import com.example.eshfeenygraduationproject.eshfeeny.productsAdapter.UseCaseAdapter
import com.example.eshfeenygraduationproject.eshfeeny.util.loadUrl
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.ProductViewModel
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.ProductViewModelFactory
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.UserViewModel


class DetailsFragment : Fragment() {

    private var binding: FragmentDetailsBinding? = null
    private lateinit var viewModel: ProductViewModel

    private val args by navArgs<DetailsFragmentArgs>()
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

            viewModel.getFavoriteProducts(userData._id)
            viewModel.favoriteProducts.observe(viewLifecycleOwner) { favoriteProducts ->

                viewModel.getProductFromRemote(args.Id)
                viewModel.productDetails.observe(viewLifecycleOwner) { productDetails ->

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

    private fun initializeViewModels() {
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        val repo = ProductRepoImpl()
        val productViewModelFactory = ProductViewModelFactory(repo)

        viewModel = ViewModelProvider(this, productViewModelFactory)[ProductViewModel::class.java]
    }

    private fun increaseItemInCart(
        userData: UserInfo,
        productDetails: ProductResponseItem
    ) {
        binding?.productIncrementBtn?.setOnClickListener {
            itemCount++
            viewModel.incrementProductNumberInCart(userData._id, productDetails._id)
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
                viewModel.removeProductFromCart(
                    userData._id,
                    PatchProductId(productDetails._id)
                )
            } else {
                itemCount--
                viewModel.decrementProductNumberInCart(
                    userData._id,
                    productDetails._id
                )
                binding?.productAmount?.text = itemCount.toString()
            }
        }
    }

    private fun checkItemInCart(userData: UserInfo) {
        binding?.add2CartBtn?.setOnClickListener { btn ->
            viewModel.getNumberOfItemInCart(userData._id, args.Id)
            viewModel.productNumber.observe(viewLifecycleOwner) { productItemCount ->
                Log.i(
                    "Details Fragment",
                    "product id: ${args.Id} and the count is: $productItemCount"
                )
                if (productItemCount == 0) {
                    viewModel.addProductToCart(
                        userData._id,
                        PatchProductId(args.Id)
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
        if (productDetails in favoriteProducts) {
            isFavorite = true
            binding?.favoriteImgView?.setImageResource(R.drawable.favorite_fill)
        }
        binding?.favoriteImgCard?.setOnClickListener {
            if (isFavorite) {
                viewModel.deleteFavoriteProduct(
                    userData._id,
                    args.Id
                )
                binding?.favoriteImgView?.setImageResource(R.drawable.favorite_notfill)
            } else {
                viewModel.addMedicineToFavorites(
                    userData._id,
                    PatchProductId(args.Id)
                )
                binding?.favoriteImgView?.setImageResource(R.drawable.favorite_fill)
            }
            isFavorite = !isFavorite
        }
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