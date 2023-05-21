package com.example.checkout.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.checkout.R
import com.example.checkout.models.ItemModel
import javax.inject.Inject

class ItemsAdapter @Inject constructor(private val list: ArrayList<ItemModel>, onClick: () -> Unit) :
    RecyclerView.Adapter<ItemsAdapter.MyViewHolder>() {
    private lateinit var context: Context
    private lateinit var onClick: () -> Unit

    init {
        this.onClick = onClick
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<ImageView>(R.id.image)
        val name = itemView.findViewById<TextView>(R.id.item_name)
        val disc = itemView.findViewById<TextView>(R.id.details)
        val price = itemView.findViewById<TextView>(R.id.price)
        val add = itemView.findViewById<AppCompatButton>(R.id.btn_add)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list[position]

        holder.name.text = item.name
        holder.disc.text = item.disc
        holder.price.text = item.price
        Glide.with(context).load(item.imageUrl).into(holder.image)
        holder.add.setOnClickListener {
            onClick()
        }


    }

}