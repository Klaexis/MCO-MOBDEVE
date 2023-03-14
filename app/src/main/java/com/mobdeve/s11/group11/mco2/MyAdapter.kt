package com.mobdeve.s11.group11.mco2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s11.group11.mco2.databinding.ItemListBinding

class MyAdapter(private val data: ArrayList<Progress>) : RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // This is a way to perform View Binding in the RecyclerView.
        val itemListBinding: ItemListBinding = ItemListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)

        return MyViewHolder(itemListBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindData(data[position])

        holder.setDeleteOnClickListener{
            // Inform the user of the deleted element
            Toast.makeText(holder.itemView.context, "Record has been deleted", Toast.LENGTH_SHORT).show()

            // Remove the element from the data (i.e. ArrayList)
            this.data.removeAt(position)

            // Inform the adapter class that the data has changed
            notifyDataSetChanged() // This forces the RecyclerView to update
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}