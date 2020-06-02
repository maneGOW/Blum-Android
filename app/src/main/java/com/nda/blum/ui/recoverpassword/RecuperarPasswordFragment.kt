package com.nda.blum.ui.recoverpassword

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.nda.blum.R
import com.nda.blum.databinding.RecuperarPasswordFragmentBinding
import com.nda.blum.ui.signup.LoginViewModelFactory

class RecuperarPasswordFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bindingRecoverPassword: RecuperarPasswordFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.recuperar_password_fragment,
            container,
            false
        )

        val application = requireNotNull(this.activity).application
        val viewModelFactory = RecuperarPasswordViewModelFactory(application)
        val loginViewModel = ViewModelProviders.of(this, viewModelFactory).get(RecuperarPasswordViewModel::class.java)

        bindingRecoverPassword.lifecycleOwner = this

        bindingRecoverPassword.btnRecoverPwdBack.setOnClickListener {
            this.findNavController().popBackStack()
        }

        bindingRecoverPassword.webView.loadUrl("https://retosalvatucasa.com/ws_app_nda/recuperapw.html")
        bindingRecoverPassword.webView.settings.javaScriptEnabled = true

        return bindingRecoverPassword.root
    }
}
