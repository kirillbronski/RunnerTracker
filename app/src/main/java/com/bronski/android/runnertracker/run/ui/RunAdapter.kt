package com.bronski.android.runnertracker.run.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bronski.android.runnertracker.core.data.room.RunEntity
import com.bronski.android.runnertracker.databinding.ItemRunBinding

class RunAdapter : RecyclerView.Adapter<RunAdapter.RunViewHolder>() {

    private val runsListDiffer = AsyncListDiffer(this, DIFF_CALLBACK)

    fun submitList(runsList: List<RunEntity>) = runsListDiffer.submitList(runsList)

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RunEntity>() {
            override fun areItemsTheSame(oldItem: RunEntity, newItem: RunEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: RunEntity, newItem: RunEntity): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RunViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRunBinding.inflate(inflater, parent, false)
        return RunViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RunViewHolder, position: Int) {
        val run = runsListDiffer.currentList[position]
        holder.bind(itemRun = run)
    }

    override fun getItemCount(): Int = runsListDiffer.currentList.size

    inner class RunViewHolder(
        val binding: ItemRunBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(itemRun: RunEntity) = with(binding) {

        }

    }
}