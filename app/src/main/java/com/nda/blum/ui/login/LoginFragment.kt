package com.nda.blum.ui.login

import android.app.ProgressDialog
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nda.blum.R
import com.nda.blum.databinding.LoginFragmentBinding
import com.nda.blum.db.BlumDatabase
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val progressDialog = ProgressDialog.show(this.requireContext(), "", "Cargando...", true)

        val binding: LoginFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.login_fragment, container, false
        )

        val navView: BottomNavigationView = this.activity!!.findViewById(R.id.bttm_nav)
        navView.visibility = View.GONE

        val application = requireNotNull(this.activity).application

        val dataSource = BlumDatabase.getInstance(application).userDao()
        val viewModelFactory = LoginViewModelFactory(dataSource, application)
        val loginViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)

        initAnimations(binding)

        binding.lifecycleOwner = this
        binding.loginViewModel = loginViewModel

        binding.btnLogin.setOnClickListener {
            if (!binding.txtEmail.text.isNullOrEmpty() && !binding.editText6.text.isNullOrEmpty()) {
                loginViewModel.callLoginService()
            } else {
                Toast.makeText(this.context, "No debe haber campos vacios", Toast.LENGTH_LONG)
                    .show()
            }
        }

        loginViewModel.rememberMe.observe(viewLifecycleOwner, Observer {
            if(it!!){
                loginViewModel.getRememberme()
            }else{
                loginViewModel.email.value = ""
                loginViewModel.password.value = ""
            }
        })

        binding.cbRememberMe.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                loginViewModel.rememberMe.value = true
                loginViewModel.updateRememberme(isChecked)
                println("checked")
            }else{
                loginViewModel.rememberMe.value = false
                loginViewModel.updateRememberme(isChecked)
                println("NOT CHECKED")
            }
        }

        loginViewModel.userRol.observe(viewLifecycleOwner, Observer {
            if (it == "Coach") {
                if (loginViewModel.firstLaunch.value == true) {
                    this.findNavController()
                        .navigate(LoginFragmentDirections.actionMainFragmentToFelicidadesCouchFragment())
                } else {
                    this.findNavController()
                        .navigate(LoginFragmentDirections.actionMainFragmentToHubAlumnoFragment())
                }
            } else {
                if (loginViewModel.firstLaunch.value == true) {
                    this.findNavController()
                        .navigate(LoginFragmentDirections.actionMainFragmentToSliderHostFragment("loginFragment"))
                } else {
                    this.findNavController()
                        .navigate(LoginFragmentDirections.actionMainFragmentToHubAlumnoFragment())
                }

            }
        })

        loginViewModel.showProgressDialog.observe(viewLifecycleOwner, Observer {
            if (it) {
                progressDialog.show()
            } else {
                progressDialog.dismiss()
            }
        })

        binding.txtRecoverPassword.setOnClickListener {
            this.findNavController()
                .navigate(LoginFragmentDirections.actionMainFragmentToRecuperarPasswordFragment())
        }

        binding.txtCrearCuenta.setOnClickListener {
            this.findNavController()
                .navigate(LoginFragmentDirections.actionMainFragmentToLoginFragment())
        }

        loginViewModel.showErrorMessage.observe(viewLifecycleOwner, Observer {
            if (it) {
                Toast.makeText(this.context, "Usuario o contraseña incorrectos", Toast.LENGTH_LONG)
                    .show()
                loginViewModel.onMessageShowed()
            }
        })

        return binding.root
    }

    private fun initAnimations(binding: LoginFragmentBinding) {
        val animationRightToLeft: Animation =
            AnimationUtils.loadAnimation(this.context, R.anim.blum_animation_righttoleft)
        val animationLeftToRight: Animation =
            AnimationUtils.loadAnimation(this.context, R.anim.blum_animation_lefttoright)

        binding.imgTitle.animation = animationRightToLeft
        binding.txtTitle1.animation = animationLeftToRight
        binding.txtTitle2.animation = animationRightToLeft
        binding.txtTitle3.animation = animationLeftToRight
        binding.layoutEmail.animation = animationRightToLeft
        binding.layoutPassword.animation = animationLeftToRight
        binding.cbRememberMe.animation = animationLeftToRight
        binding.txtRecoverPassword.animation = animationRightToLeft
        binding.btnLogin.animation = animationRightToLeft
        binding.txtAccountQuestion.animation = animationLeftToRight
        binding.txtCrearCuenta.animation = animationRightToLeft
    }
}
