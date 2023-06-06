package com.example.checkout.ui.adapters

import android.content.Context
import android.util.Log.e
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.checkout.R
import com.example.checkout.models.ItemModel

class BottomRecAdapter(val map: MutableMap<ItemModel, Int>) :
    RecyclerView.Adapter<BottomRecAdapter.MyViewHolder>() {
    private lateinit var context: Context
    var onRemoveListener: (ItemModel) -> Unit = {}
    var onChangeListener: (ItemModel) -> Unit = {}
    var total: Int = 0


    inner class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val image: ImageView = item.findViewById(R.id.image)
        val shop: TextView = item.findViewById(R.id.place)
        val name: TextView = item.findViewById(R.id.item_name)
        val price: TextView = item.findViewById(R.id.price)
        val add: ImageView = item.findViewById(R.id.btn_add)
        val cancel: ImageView = item.findViewById(R.id.cancel)
        val num: TextView = item.findViewById(R.id.amt)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.bottom_item_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return map.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        total = 0
        val items = map.keys.toList()
        val item = items[position]
        val amt = map.getOrDefault(item, 0)
        total += item.price.toInt() * amt

        holder.apply {
            name.text = item.name
            shop.text = item.shop
            price.text = item.price
            Glide.with(context).load(item.imageUrl).into(image)
            num.text = amt.toString()
        }

        holder.add.setOnClickListener {
            println("item clicked")
            incrementItem(position)
        }
        holder.cancel.setOnClickListener {
            println("remove")
            removeItem(position)
        }
    }

    // to update values in the items list
    fun updateMap(newMap: Map<ItemModel, Int>) {
        val s = itemCount
        map.clear()
        notifyItemRangeRemoved(0, s)
        e("adapter/newMap", "$newMap")
        if (newMap.isEmpty()) return
        map.putAll(newMap)
        computeTotal()
        notifyItemRangeInserted(0, newMap.size)
    }

    private fun computeTotal() {
        total = 0
        if (map.isEmpty()) return
        map.forEach { (item, amt) ->
            total += item.price.toInt() * amt
        }

    }

    private fun incrementItem(pos: Int) {
        map.keys.toList().apply {
            if (pos > lastIndex) return
            val item = this[pos]
            map[item] = map.getOrDefault(item, 0) + 1
            onChangeListener(item)
            notifyItemChanged(pos)
        }

    }

    private fun removeItem(position: Int) {
        map.keys.toList().apply {
            if (position > lastIndex) return
            val item = this[position]
            total -= item.price.toInt() * map[item]!!
            onRemoveListener(item)
            map.remove(item)
            notifyItemRemoved(position)


        }


    }
}