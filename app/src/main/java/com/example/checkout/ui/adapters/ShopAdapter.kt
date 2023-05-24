package com.example.checkout.ui.adapters

import android.content.Context
import android.util.Log.e
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.checkout.R
import com.example.checkout.models.ShopModel

class ShopAdapter(val list: ArrayList<ShopModel>,onClick: (Int) -> Unit) :
    RecyclerView.Adapter<ShopAdapter.MyViewHolder>() {
    private lateinit var context: Context
    private  var onClick: (Int) -> Unit
    var isSelected : ArrayList<Boolean>

    init {
        this.onClick = onClick
        isSelected = ArrayList()
    }

    inner class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val image: ImageView = item.findViewById(R.id.backImg)
        val shopName: TextView = item.findViewById(R.id.shop_name)
        val shopPlace : TextView = item.findViewById(R.id.shop_place)
        val const : ConstraintLayout = item.findViewById(R.id.const_layout)
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


        holder.itemView.setOnClickListener {
            onClick(position)
            if(isSelected[position]) holder.const.background = context.resources.getDrawable(R.color.green,null)
            else holder.const.background = context.resources.getDrawable(R.color.white,null)
        }




    }

    fun updateList(newList : ArrayList<ShopModel>){
        e("updating","updating shops")
        val s1= list.lastIndex
        list.clear()
        notifyItemRangeChanged(0,s1)
        if(newList.isEmpty()) return
        list.addAll(newList)
        notifyItemRangeInserted(0,list.lastIndex)
        isSelected.clear()
        for(item in newList) isSelected.add(false)

    }

    fun setOnItemClickListener(click :(Int)-> Unit){
        this.onClick=  click
    }

    fun unselectAll(){

        for( i in 0 .. list.lastIndex ) isSelected[i] = false
        notifyItemRangeChanged(0,list.size)
    }

    fun selectPos(i : Int){
        isSelected[i] = true
        notifyItemChanged(i)
    }


}