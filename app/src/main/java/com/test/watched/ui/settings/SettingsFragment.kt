package com.test.watched.ui.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.test.watched.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        viewModel.isFilterMoviesEnabled.observe(viewLifecycleOwner) {
            binding.filterAdultMoviesSwitch.isChecked = it
        }

        viewModel.isNotificationsAllowd.observe(viewLifecycleOwner) {
            binding.enableNotificationsSwitch.isChecked = it
        }

        binding.filterAdultMoviesSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.isFilterMoviesEnabled.postValue(isChecked)
        }

        binding.enableNotificationsSwitch.setOnCheckedChangeListener{ buttonView, isChecked ->
            viewModel.isNotificationsAllowd.postValue(isChecked)
        }

        return binding.root
    }
}