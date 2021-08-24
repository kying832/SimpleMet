package com.example.simplemet.ui.main

import android.media.SoundPool
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.simplemet.databinding.MainFragmentBinding
import com.example.simplemet.databinding.MainFragmentBinding.inflate
import kotlinx.coroutines.*


const val MIN: Int = 0
const val MAX: Int = 45
const val TEMPO_INCREMENT: Int = 4
const val MIN_TEMPO: Int = 40

class MainFragment : Fragment() {
    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var soundpool: SoundPool
    val scope = CoroutineScope(Dispatchers.Default)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // TODO: Use the ViewModel
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        _binding = inflate(inflater, container, false)

        setNumberPicker()

        setSwitch()
        return binding.root

    }

    /*
    * The NumberPicker is essentially an array that displays values or strings
    * stored at / calculated from certain indices.
    *
    * Can calculate the actual tempo used and displayed by using the method getTempoFromIndex
    * and converting it to string for display purposes.
    *
    * */

    private fun setNumberPicker() {
        binding.tempoPicker.setFormatter { getTempoFromIndex(it).toString() }

        binding.tempoPicker.minValue = MIN
        binding.tempoPicker.maxValue = MAX //should be 45 values total from 40 - 220

        binding.tempoPicker.wrapSelectorWheel = true

        binding.tempoPicker.setOnValueChangedListener { _, _, newVal ->
            viewModel.setTempo(getTempoFromIndex(newVal))
   //         Log.d("New Tempo: ", "${viewModel.tempo.value}")
        }

    }

    private fun setSwitch() {
        lateinit var job: Job
        binding.onOffSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(activity, "Switch ON", Toast.LENGTH_LONG).show()
                job = scope.launch {
                    while (isChecked) {
                        tempoLog()
                    }
                }
            } else {
                Toast.makeText(activity, "Switch OFF", Toast.LENGTH_LONG).show()
                job.cancel()
            }
        }
    }


    /*
    * let x = the index in the background of the tempoPicker.
    * Return value is the actual tempo to be used for the metronome (i.e. 96 bpm)
    * formula is y = tempo_increment * x + minValue
    * */
    private fun getTempoFromIndex(x: Int): Int {
        return x * TEMPO_INCREMENT + MIN_TEMPO
    }


    private suspend fun tempoLog() {
        delay(viewModel.getFrequency().toLong())
        Log.d("New Tempo: ", "${viewModel.tempo.value}")
    }

}