package com.example.checkout.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.checkout.R
import com.example.checkout.models.ShopModel

class ShopAdapter(private val list: ArrayList<ShopModel>, onClick: () -> Unit) :
    RecyclerView.Adapter<ShopAdapter.MyViewHolder>() {
    private lateinit var context: Context
    private lateinit var onClick: () -> Unit

    init {
        this.onClick = onClick
    }

    inner class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val image: ImageView = item.findViewById(R.id.backImg)
        val shopName: TextView = item.findViewById(R.id.shop_name)
        val shopPlace : TextView = item.findViewById(R.id.shop_place)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.shop_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val shop = list[position]

        holder.shopName.text = shop.name
        holder.shopPlace.text  =shop.place
        Glide.with(context).load(shop.imageUrl).into(holder.image)

        holder.itemView.setOnClickListener { onClick() }
    }

    fun updateList(newList : ArrayList<ShopModel>){
        val s1= list.lastIndex
        list.clear()
        notifyItemRangeChanged(0,s1)
        list.addAll(newList)
        notifyItemRangeInserted(0,list.lastIndex)
    }
}