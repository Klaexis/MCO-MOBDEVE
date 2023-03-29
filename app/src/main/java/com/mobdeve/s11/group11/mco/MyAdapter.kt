package com.mobdeve.s11.group11.mco

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s11.group11.mco.Database.Progress
import com.mobdeve.s11.group11.mco.Database.ProgressDbHelper
import com.mobdeve.s11.group11.mco.databinding.ItemListBinding


class MyAdapter(private val data: ArrayList<Progress>) : RecyclerView.Adapter<MyViewHolder>() {
    private lateinit var progressDbHelper: ProgressDbHelper
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

        holder.setDeleteOnClickListener{ //Delete button in recycler view
            // Inform the user of the deleted element
            Toast.makeText(holder.itemView.context, "Record has been deleted", Toast.LENGTH_SHORT).show()

            // Remove the record from the position
            this.data.removeAt(position)

            // Delete the record in the database
            progressDbHelper = ProgressDbHelper.getInstance(holder.itemView.context)!!
            progressDbHelper.deleteProgress(holder.id, holder.email)

            // Inform the adapter class that the data has changed
            notifyDataSetChanged() // This forces the RecyclerView to update
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}