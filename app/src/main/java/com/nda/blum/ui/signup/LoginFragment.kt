package com.nda.blum.ui.signup

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.nda.blum.R
import com.nda.blum.databinding.LoginFragmentBinding


class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bindingLogin: LoginFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.login_fragment,
            container,
            false
        )
        val application = requireNotNull(this.activity).application
        val viewModelFactory =
            LoginViewModelFactory(application)
        val loginViewModel = ViewModelProviders.of(this,viewModelFactory).get(LoginViewModel::class.java)

        bindingLogin.lifecycleOwner = this

        bindingLogin.loginViewModel = loginViewModel

        bindingLogin.imgLoginBack.setOnClickListener {
            this.findNavController().popBackStack()
        }

        bindingLogin.button2.setOnClickListener {
            if(!bindingLogin.editText.text.isNullOrBlank() && !bindingLogin.editText2.text.isNullOrBlank()
                && !bindingLogin.editText3.text.isNullOrEmpty() && !bindingLogin.editText4.text.isNullOrEmpty()){
                loginViewModel.saveUserData()
            }else{
                Toast.makeText(this.context, "No debe haber campos vacios", Toast.LENGTH_LONG).show()
            }
        }

        return bindingLogin.root
    }
}
