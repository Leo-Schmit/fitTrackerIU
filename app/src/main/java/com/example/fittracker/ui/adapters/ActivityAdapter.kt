package com.example.fittracker.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fittracker.data.model.Activity
import com.example.fittracker.R

class ActivityAdapter(private var activities: List<Activity>) :
    RecyclerView.Adapter<ActivityAdapter.ActivityViewHolder>() {
    class ActivityViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.tvActivityItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.activity_item, parent, false)
        return ActivityViewHolder(view)
    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        holder.textView.text = "${activities[position].duration} min"
    }

    override fun getItemCount() = activities.size

    fun updateActivities(newActivities: List<Activity>) {
        activities = newActivities
        notifyDataSetChanged()
    }
}