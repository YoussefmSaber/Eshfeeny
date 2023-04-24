package com.example.eshfeenygraduationproject.eshfeeny.details


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.data.repository.ProductRepoImpl
import com.example.domain.entity.product.ProductResponseItem
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentDetailsBinding
import com.example.eshfeenygraduationproject.eshfeeny.productsAdapter.UseCaseAdapter
import com.example.eshfeenygraduationproject.eshfeeny.util.loadUrl
import com.example.eshfeenygraduationproject.eshfeeny.viewmodel.ProductViewModel
import com.example.eshfeenygraduationproject.eshfeeny.viewmodel.ProductViewModelFactory
import com.example.eshfeenygraduationproject.eshfeeny.viewmodel.UserViewModel


class DetailsFragment : Fragment() {

    private var binding: FragmentDetailsBinding? = null
    private lateinit var viewModel: ProductViewModel
    private val args by navArgs<DetailsFragmentArgs>()
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDetailsBinding.inflate(inflater, container, false)

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        val repo = ProductRepoImpl()
        val productViewModelFactory = ProductViewModelFactory(repo)

        viewModel = ViewModelProvider(this, productViewModelFactory)[ProductViewModel::class.java]

        userViewModel.userData.observe(viewLifecycleOwner) { userData ->

            viewModel.getFavoriteProducts(userData._id)
            viewModel.favoriteProducts.observe(viewLifecycleOwner) { favoriteProducts ->

                viewModel.getProductFromRemote(args.Id)
                viewModel.productDetails.observe(viewLifecycleOwner) { productDetails ->
                    if (productDetails in favoriteProducts) {
                        binding?.favoriteImgView?.setImageResource(R.drawable.details_favorite_selected)
                    }

                    binding?.idTxtAmountVolumeDetails?.text =
                        "${productDetails?.nameAr} | ${productDetails?.amount} | ${productDetails?.volume}"

                    binding?.idTxtPriceDetails?.text = "${productDetails?.price.toString()} جنيه"
                    binding?.idTxtDescrection?.text = productDetails?.description

                    productDetails?.images?.get(0)?.let {
                        binding?.idImgMedicineDetails?.loadUrl(it)
                    }

                    productDetails?.let { productResponse ->
                        setAdapters(productResponse)
                    }
                }
            }
        }

        // Inflate the layout for this fragment
        return binding?.root
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