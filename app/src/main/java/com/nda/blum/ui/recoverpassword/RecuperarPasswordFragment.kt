package com.nda.blum.ui.recoverpassword

import android.app.ProgressDialog
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nda.blum.R
import com.nda.blum.databinding.RecuperarPasswordFragmentBinding


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

        val navView: BottomNavigationView = this.activity!!.findViewById(R.id.bttm_nav)
        navView.visibility = View.GONE

        val progressDialog = ProgressDialog.show(this.requireContext(), "", "Cargando...", true)

        bindingRecoverPassword.webView.settings.loadWithOverviewMode = true

        bindingRecoverPassword.webView.settings.javaScriptEnabled = true

        bindingRecoverPassword.webView.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                progressDialog.setCancelable(false)
                progressDialog.show()
                view.loadUrl(url)
                return true
            }
            override fun onPageFinished(view: WebView, url: String) {
                progressDialog.dismiss()
            }
        })

        bindingRecoverPassword.webView.loadUrl("https://retosalvatucasa.com/ws_app_nda/recuperapw.html")

        return bindingRecoverPassword.root
    }
}
