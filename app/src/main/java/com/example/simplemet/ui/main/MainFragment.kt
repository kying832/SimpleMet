package com.example.simplemet.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.simplemet.databinding.MainFragmentBinding
import com.example.simplemet.databinding.MainFragmentBinding.inflate


const val MIN: Int =  0
const val MAX: Int = 45
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

        setNumberPicker()

        
        return binding.root

    }

    private fun setNumberPicker(){
        binding.tempoPicker.setFormatter { getTempoFromIndex(it).toString() }

        binding.tempoPicker.minValue = MIN
        binding.tempoPicker.maxValue = MAX //should be 45 values total from 40 - 220

        binding.tempoPicker.wrapSelectorWheel = true

        binding.tempoPicker.setOnValueChangedListener { _, _, newVal ->
            viewModel.setTempo(getTempoFromIndex(newVal))
            Log.d("New Tempo: ", "${viewModel.tempo.value}")
        }

    }
        /*
        * let x = the index in the background of the tempoPicker.
        * Return value is the actual tempo to be used for the metronome (i.e. 96 bpm)
        * formula is y = (x+1) * 4 + minValue
        * */
        fun getTempoFromIndex(x: Int): Int{
            return x * 4 + 40
        }



}