package com.nda.blum.ui.signup

import android.app.ProgressDialog
import android.graphics.Color
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
import com.nda.blum.databinding.SignupFragmentBinding
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class SignupFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val progressDialog = ProgressDialog.show(this.requireContext(), "", "Cargando...", true)

        val bindingLogin: SignupFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.signup_fragment,
            container,
            false
        )
        val application = requireNotNull(this.activity).application

        val viewModelFactory =
            SignupViewModelFactory(application)
        val signupViewModel = ViewModelProviders.of(this,viewModelFactory).get(SignupViewModel::class.java)

        bindingLogin.lifecycleOwner = this

        bindingLogin.signupViewModel = signupViewModel

        bindingLogin.btnCreateAccount.isEnabled = false
        bindingLogin.btnCreateAccount.background = resources.getDrawable(R.drawable.gray_shape_button)
        bindingLogin.btnCreateAccount.setTextColor(Color.parseColor("#000000"))

        val navView: BottomNavigationView = this.activity!!.findViewById(R.id.bttm_nav)
        navView.visibility = View.GONE

        initAnimations(bindingLogin)


        bindingLogin.cbAcceptTermnAndCond.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                bindingLogin.btnCreateAccount.isEnabled = true
                bindingLogin.btnCreateAccount.background = resources.getDrawable(R.drawable.pink_shape_button)
                bindingLogin.btnCreateAccount.setTextColor(Color.parseColor("#FFFFFF"))
            }else{
                bindingLogin.btnCreateAccount.isEnabled = false
                bindingLogin.btnCreateAccount.background =
                    resources.getDrawable(R.drawable.gray_shape_button)
                bindingLogin.btnCreateAccount.setTextColor(Color.parseColor("#000000"))
            }
        }

        signupViewModel._showProgressDialog.observe(viewLifecycleOwner, Observer {
            if(it){
                progressDialog.show()
            }else{
                progressDialog.dismiss()
            }
        })

        bindingLogin.imgLoginBack.setOnClickListener {
            this.findNavController().popBackStack()
        }

        signupViewModel.navigateToLogin.observe(viewLifecycleOwner, Observer {
            if(it){
                this.findNavController().popBackStack()
                signupViewModel.onNavigated()
            }
        })

        bindingLogin.btnCreateAccount.setOnClickListener {
            if(!bindingLogin.editText.text.isNullOrBlank() && !bindingLogin.editText2.text.isNullOrBlank()
                && !bindingLogin.editText3.text.isNullOrEmpty() && !bindingLogin.editText4.text.isNullOrEmpty()){
                signupViewModel.saveUserData()
            }else{
                Toast.makeText(this.context, "No debe haber campos vacios", Toast.LENGTH_LONG).show()
            }
        }
        return bindingLogin.root
    }

    private fun initAnimations(binding: SignupFragmentBinding){
        val animationRightToLeft: Animation =
            AnimationUtils.loadAnimation(this.context, R.anim.blum_animation_righttoleft)
        val animationLeftToRight: Animation =
            AnimationUtils.loadAnimation(this.context, R.anim.blum_animation_lefttoright)

        //binding.imgLoginBack.animation = animationRightToLeft
        binding.imgSignupTitle.animation = animationLeftToRight
        binding.txtSignupTitle.animation = animationRightToLeft
        binding.layoutSignupName.animation = animationLeftToRight
        binding.layoutSignupEmail.animation = animationRightToLeft
        binding.layoutSignupPassword.animation = animationLeftToRight
        binding.layoutSignupMobile.animation = animationRightToLeft
        binding.btnCreateAccount.animation = animationLeftToRight
        binding.cbAcceptTermnAndCond.animation = animationRightToLeft
    }
}
