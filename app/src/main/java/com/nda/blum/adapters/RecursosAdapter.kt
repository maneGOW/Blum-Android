package com.nda.blum.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nda.blum.DAO.BuscarNidoCoachResult
import com.nda.blum.DAO.RecursosResponse
import com.nda.blum.DAO.RecursosResult
import com.nda.blum.R
import com.nda.blum.databinding.RecursoItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


private val ITEM_VIEW_TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_ITEM = 1

class RecursosAdapter(
    val context: Context,
    val recursos: RecursosResponse,
    val fragment: Fragment
) :
    ListAdapter<RecursoDataItem, RecyclerView.ViewHolder>(RecursosDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown ViewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val recursos = getItem(position) as RecursoDataItem.RecursosItem
                holder.bind(recursos.recurso, fragment)

            }
        }
    }

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun addHeaderAndSubmitList(list: List<RecursosResult>?) {
        adapterScope.launch {
            val items = when (recursos.result) {
                null -> listOf(RecursoDataItem.Header)
                else -> list!!.map { RecursoDataItem.RecursosItem(it) }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is RecursoDataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is RecursoDataItem.RecursosItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    class ViewHolder private constructor(
        val binding: RecursoItemBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RecursosResult, fragment: Fragment) {
            val fileExtension = item.nombreArchivo.substringAfterLast(".")
            if (fileExtension == "PDF" || fileExtension == "pdf") {
                binding.txtRecursoNombre.text = item.nombreArchivo
                Glide.with(fragment)
                    .load(R.drawable.pdf_coach)
                    .into(binding.imgRecurso)
                binding.root.setOnClickListener {
                    val browserIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://retosalvatucasa.com/ws_app_nda/Documentos/${item.nombreArchivo}")
                    )
                    fragment.startActivity(browserIntent)

                }
            } else if (fileExtension == "jpg" || fileExtension == "JPG" || fileExtension == "JPEG" || fileExtension == "jpeg") {
                binding.txtRecursoNombre.text = item.nombreArchivo
                Glide.with(fragment)
                    .load(R.drawable.podcast_coach)
                    .into(binding.imgRecurso)
                binding.root.setOnClickListener {
                    val browserIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://retosalvatucasa.com/ws_app_nda/Documentos/${item.nombreArchivo}")
                    )
                    fragment.startActivity(browserIntent)
                }
            } else if (fileExtension == "MP3" || fileExtension == "mp3") {
                binding.txtRecursoNombre.text = item.nombreArchivo
                Glide.with(fragment)
                    .load(R.drawable.audio_coach)
                    .into(binding.imgRecurso)
                binding.root.setOnClickListener {
                    val browserIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://retosalvatucasa.com/ws_app_nda/Documentos/${item.nombreArchivo}")
                    )
                    fragment.startActivity(browserIntent)
                }
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecursoItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class RecursosDiffCallback : DiffUtil.ItemCallback<RecursoDataItem>() {
    override fun areItemsTheSame(oldItem: RecursoDataItem, newItem: RecursoDataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RecursoDataItem, newItem: RecursoDataItem): Boolean {
        return oldItem == newItem
    }
}

class RecursosListener(val clickListener: (recursosId: String) -> Unit) {
    fun onClick(nido: BuscarNidoCoachResult) = clickListener(nido.id_Nido)
}

sealed class RecursoDataItem {
    data class RecursosItem(val recurso: RecursosResult) : RecursoDataItem() {
        override val id = 1L
    }

    object Header : RecursoDataItem() {
        override val id = Long.MIN_VALUE
    }

    abstract val id: Long
}