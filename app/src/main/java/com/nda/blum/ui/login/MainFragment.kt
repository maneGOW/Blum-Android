package com.nda.blum.ui.login

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.nda.blum.R
import com.nda.blum.databinding.MainFragmentBinding


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding : MainFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.main_fragment, container, false
        )

        val application = requireNotNull(this.activity).application
        val viewModelFactory = MainViewModelFactory(application)
        val mainViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)

        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel

        binding.button.setOnClickListener {
            if(!binding.editText5.text.isNullOrEmpty() && !binding.editText6.text.isNullOrEmpty()){
                mainViewModel.callLoginService()
            }else{
                Toast.makeText(this.context, "No debe haber campos vacios", Toast.LENGTH_LONG).show()
            }
        }

        mainViewModel.navigateToHub.observe(viewLifecycleOwner, Observer {
            if(it){
                this.findNavController().navigate(MainFragmentDirections.actionMainFragmentToHubAlumnoFragment())
                mainViewModel.onHubNavigated()
            }
        })

        return binding.root
    }
}
