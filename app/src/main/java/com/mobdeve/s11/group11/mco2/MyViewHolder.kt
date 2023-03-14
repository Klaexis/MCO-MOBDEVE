package com.mobdeve.s11.group11.mco2

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s11.group11.mco2.databinding.ItemListBinding

class MyViewHolder(private val viewBinding: ItemListBinding): RecyclerView.ViewHolder(viewBinding.root) {
    fun bindData(progress: Progress){
        viewBinding.itemActivity.text = progress.activityMET
        viewBinding.itemDistance.text = progress.distanceTraveled
        viewBinding.itemTime.text = progress.timeElapsed
        viewBinding.itemCalBurn.text = progress.caloriesBurned
    }
    fun setDeleteOnClickListener(onClickListener: View.OnClickListener) {
        this.viewBinding.deleteBtn.setOnClickListener(onClickListener)
    }
}