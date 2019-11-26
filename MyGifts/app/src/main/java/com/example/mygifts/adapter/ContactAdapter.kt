package com.example.mygifts.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mygifts.R
import com.example.mygifts.model.Contact
import com.example.mygifts.model.Gift

class ContactAdapter(
    context: Context?,
    private val contacts: List<Contact>,
    private val clickListener: (position: Int) -> Unit
) : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            inflater.inflate(
                R.layout.item_contact,
                parent,
                false
            ), clickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int = gifts.size

    private fun getItem(position: Int): Gift = gifts[position]

    class ViewHolder(
        itemView: View,
        listener: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val image: ImageView = itemView.findViewById(R.id.preview_image)
        private val name: TextView = itemView.findViewById(R.id.preview_name)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener(position)
                }
            }
        }

        fun bind(gift: Gift) {
            /*image.load(gift.image) {
                transformations(CircleCropTransformation())
            }
            name.text = gift.name*/
        }
    }
}