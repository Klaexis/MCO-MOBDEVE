package com.mobdeve.s11.group11.mco2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s11.group11.mco2.databinding.ItemListBinding

class MyAdapter(private val data: ArrayList<Progress>) : RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // This is a way to perform View Binding in the RecyclerView.
        val itemListBinding: ItemListBinding = ItemListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)

        val myViewHolder = MyViewHolder(itemListBinding)

//        myViewHolder.[button here].setOnClickListener{
//
//        }

        return myViewHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindData(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }
}