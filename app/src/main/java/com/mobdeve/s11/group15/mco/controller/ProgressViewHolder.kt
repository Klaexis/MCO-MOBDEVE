package com.mobdeve.s11.group15.mco.controller

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s11.group15.mco.database.Progress
import com.mobdeve.s11.group15.mco.databinding.ItemListBinding

class ProgressViewHolder(private val viewBinding: ItemListBinding): RecyclerView.ViewHolder(viewBinding.root) {
    var email = ""
    var id = 0L
    fun bindData(progress: Progress){
        //Set viewBinding to item_list.xml and its corresponding views
        viewBinding.itemActivity.text = progress.activityMET
        viewBinding.itemDistance.text = progress.distanceTraveled.toString()
        viewBinding.itemTime.text = progress.timeElapsedMinutes.toString() + " : " + progress.timeElapsedSeconds.toString()
        viewBinding.itemCalBurn.text = progress.caloriesBurned.toString()
        viewBinding.itemDate.text = progress.date

        // Store the email and id to the item record upon created
        email = progress.email
        id = progress.id
    }
    fun setDeleteOnClickListener(onClickListener: View.OnClickListener) {
        this.viewBinding.deleteBtn.setOnClickListener(onClickListener)
    }
}