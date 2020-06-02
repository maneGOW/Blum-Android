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
import com.nda.blum.db.BlumDatabase
import kotlinx.coroutines.InternalCoroutinesApi


@InternalCoroutinesApi
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

        val dataSource = BlumDatabase.getInstance(application).userDao()
        val viewModelFactory = MainViewModelFactory(dataSource, application)
        val mainViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)

        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel

        binding.btnLogin.setOnClickListener {
            if(!binding.txtEmail.text.isNullOrEmpty() && !binding.editText6.text.isNullOrEmpty()){
                mainViewModel.callLoginService()
            }else{
                Toast.makeText(this.context, "No debe haber campos vacios", Toast.LENGTH_LONG).show()
            }
        }

        binding.txtRecoverPassword.setOnClickListener {
            this.findNavController().navigate(MainFragmentDirections.actionMainFragmentToRecuperarPasswordFragment())
        }

        binding.txtCrearCuenta.setOnClickListener {
            this.findNavController().navigate(MainFragmentDirections.actionMainFragmentToLoginFragment())
        }

        mainViewModel.showErrorMessage.observe(viewLifecycleOwner, Observer {
            if(it){
                Toast.makeText(this.context, "Usuario o contraseña incorrectos", Toast.LENGTH_LONG).show()
                mainViewModel.onMessageShowed()
            }
        })

        mainViewModel.navigateToHub.observe(viewLifecycleOwner, Observer {
            if(it){
                this.findNavController().navigate(MainFragmentDirections.actionMainFragmentToHubAlumnoFragment())
                mainViewModel.onHubNavigated()
            }
        })

        return binding.root
    }
}
