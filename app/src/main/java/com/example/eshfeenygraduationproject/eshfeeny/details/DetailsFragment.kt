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
import com.bumptech.glide.Glide
import com.example.data.repository.ProductRepoImpl
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentDetailsBinding
import com.example.eshfeenygraduationproject.eshfeeny.productsAdapter.UseCaseAdapter
import com.example.eshfeenygraduationproject.eshfeeny.viewmodel.DetailsViewModel
import com.example.eshfeenygraduationproject.eshfeeny.viewmodel.DetailsViewModelFactory


class DetailsFragment : Fragment() {
    private var binding: FragmentDetailsBinding? = null
    private lateinit var viewModel: DetailsViewModel
    private val args by navArgs<DetailsFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val repo = ProductRepoImpl()
        val viewModelFactory = DetailsViewModelFactory(repo)
        viewModel = ViewModelProvider(this, viewModelFactory)[DetailsViewModel::class.java]
        binding?.lifecycleOwner = viewLifecycleOwner

        viewModel.setMedicine(args.Id)
        viewModel.medicine.observe(viewLifecycleOwner) { productRespone ->
            binding?.idTxtAmountVolumeDetails?.text =
                "${productRespone.body()?.nameAr} | ${productRespone.body()?.amount} | ${productRespone.body()?.volume}"
            binding?.idTxtPriceDetails?.text = "${productRespone.body()?.price.toString()} جنيه"
            binding?.idTxtDescrection?.text = productRespone.body()?.description

            binding?.root?.context?.let { context ->
                binding?.idImgMedicineDetails?.let { imgView ->
                    Glide.with(context).load(productRespone.body()?.images?.get(0)).into(
                        imgView
                    )
                }
            }
            val useCaseAdapter = UseCaseAdapter(productRespone.body()!!.useCases)
            binding?.idRvUseCaseDetails?.adapter = useCaseAdapter
            Log.i("details", useCaseAdapter.toString())

            val sideEffectAdapter = UseCaseAdapter(productRespone.body()!!.sideEffects)
            binding?.idRvSideEffectDetails?.adapter = sideEffectAdapter
            Log.i("details", sideEffectAdapter.toString())

            val usageAdapter = UseCaseAdapter(productRespone.body()!!.usage)
            binding?.idRvUsageDetails?.adapter = usageAdapter
            Log.i("details", usageAdapter.toString())

            val warningAdapter = UseCaseAdapter(productRespone.body()!!.warning)
            binding?.idRvWarningDetails?.adapter = warningAdapter
            Log.i("details", warningAdapter.toString())

        }

        //Return to Home fragment

        binding?.exit1BtnId?.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_detailsFragment_to_homeFragment2)
        }
        // Inflate the layout for this fragment
        return binding?.root
    }

}