package com.example.eshfeenygraduationproject.eshfeeny.insurance_card

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentAddPhotoICBinding


class AddPhotoICFragment : Fragment() {
    private var binding:FragmentAddPhotoICBinding?= null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddPhotoICBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding?.root
    }
}