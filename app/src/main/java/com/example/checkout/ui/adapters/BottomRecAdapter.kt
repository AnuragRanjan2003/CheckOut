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

class BottomRecAdapter(val list: ArrayList<ItemModel>, val amount: ArrayList<Int>) :
    RecyclerView.Adapter<BottomRecAdapter.MyViewHolder>() {
    private lateinit var context: Context
    var onRemoveListener: (ItemModel) -> Unit = {}
    var onChangeListener: () -> Unit = {}
    var total: Int = 0

    init {
        for (it in list) amount.add(1)
    }

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
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        total=0
        val item = list[position]
        val amt = amount[position]
        total += item.price.toInt() * amt

        holder.apply {
            name.text = item.name
            shop.text = item.shop
            price.text = item.price
            Glide.with(context).load(item.imageUrl).into(image)
            num.text = amt.toString()
        }

        holder.add.setOnClickListener { incrementItem(position) }
        holder.cancel.setOnClickListener { removeItem(position) }
    }

    fun updateList(newList: ArrayList<ItemModel>) {
        val s1 = list.size
        list.clear()
        notifyItemRangeRemoved(0, s1)
        e("newList/Adapter", "$newList")
        if (newList.isEmpty()) return
        list.addAll(newList)
        amount.clear()
        for (it in list) amount.add(1)

        notifyItemRangeInserted(0, list.size)
        e("adapter", "updated : $list")
        e("adapter", "updated : $amount")
        total = 0
        for (i in 0..list.lastIndex) {
            total += list[i].price.toInt() * amount[i]
        }
        e("adapter", "tot : $total")


    }

    private fun incrementItem(position: Int) {
        amount[position]++
        notifyItemChanged(position)
        onChangeListener()
    }

    private fun removeItem(position: Int) {
        total -= list[position].price.toInt() * amount[position]
        onRemoveListener(list[position])
        list.removeAt(position)
        amount.removeAt(position)

        notifyItemRemoved(position)


    }
}