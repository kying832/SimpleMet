package com.example.simplemet.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.simplemet.databinding.MainFragmentBinding
import com.example.simplemet.databinding.MainFragmentBinding.inflate


class MainFragment : Fragment() {
    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // TODO: Use the ViewModel
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        _binding = inflate(inflater, container, false)

        val minValue = 40
        val maxValue = 220

        val initTempoPicker: Array<String> = Array((maxValue - minValue) / 4 + 1) { (it * 4 + 40).toString() }
        binding.tempoPicker.minValue = 0
        binding.tempoPicker.maxValue = initTempoPicker.size - 1
        binding.tempoPicker.setDisplayedValues(initTempoPicker)

        binding.tempoPicker.wrapSelectorWheel = true
        //viewModel.tempo.value?.let { binding.tempoPicker.value = it }
        
        return binding.root
        //return inflater.inflate(R.layout.main_fragment, container, false)
    }



}