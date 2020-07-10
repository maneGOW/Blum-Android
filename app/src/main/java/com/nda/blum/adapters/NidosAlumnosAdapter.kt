package com.nda.blum.adapters

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nda.blum.DAO.BuscarNidoCoachResponse
import com.nda.blum.DAO.BuscarNidoCoachResult
import com.nda.blum.databinding.NidosCoachItemBinding
import com.nda.blum.ui.nidoscoachfragment.NidosCoachFragmentDirections
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.*
import java.util.*

private val ITEM_VIEW_TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_ITEM = 1
private val SELECT_PHOTO = 100

class NidosAlumnosAdapter(
    val context: Context,
    val nidos: BuscarNidoCoachResponse,
    val clickListener: NidosListener,
    val fragment: Fragment
) :
    ListAdapter<DataItem, RecyclerView.ViewHolder>(NidoDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown ViewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val nidos = getItem(position) as DataItem.NidosItem
                holder.bind(clickListener, nidos.nido, fragment)
            }
        }
    }

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun addHeaderAndSubmitList(list: List<BuscarNidoCoachResult>?) {
        adapterScope.launch {
            val items = when (nidos.result) {
                null -> listOf(DataItem.Header)
                else -> list!!.map { DataItem.NidosItem(it) }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.NidosItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    class ViewHolder private constructor(
        val binding: NidosCoachItemBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(clickListener: NidosListener, item: BuscarNidoCoachResult, fragment: Fragment) {
            //binding.nidoData = item

            binding.txtAlumnoName.text = item.Nombre_Nido
            Glide.with(fragment)
                .load(item.fotoNido)
                .apply(RequestOptions.circleCropTransform())
                .into(binding.imgAlumno)
            binding.imgEditChat.visibility = View.VISIBLE
            binding.imgEditChat.setOnClickListener {
                fragment.findNavController().navigate(
                    NidosCoachFragmentDirections.actionNidosCoachFragmentToNidosDataFragment(
                        item.id_Nido,
                        item.Nombre_Nido,
                        item.fotoNido
                    )
                )
            }

            binding.clickListener = clickListener
            binding.root.setOnClickListener {
                fragment.findNavController().navigate(
                    NidosCoachFragmentDirections.actionNidosCoachFragmentToChatWithCoachFragment(
                        "coachNest", item.id_Nido, item.Nombre_Nido, item.fotoNido
                    )
                )
            }

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = NidosCoachItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class NidoDiffCallBack : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }
}

class NidosListener(val clickListener: (movementId: String) -> Unit) {
    fun onClick(nido: BuscarNidoCoachResult) = clickListener(nido.id_Nido)
}

sealed class DataItem {
    data class NidosItem(val nido: BuscarNidoCoachResult) : DataItem() {
        override val id = nido.id_Nido.toLong()
    }

    object Header : DataItem() {
        override val id = Long.MIN_VALUE
    }

    abstract val id: Long
}