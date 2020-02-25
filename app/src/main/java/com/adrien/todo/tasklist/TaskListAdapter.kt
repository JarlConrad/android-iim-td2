package com.adrien.todo.tasklist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adrien.todo.R
import kotlinx.android.synthetic.main.item_task.view.*

class TaskListAdapter(private val taskList: List<Task>) : RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {

    var onDeleteClickListener: ((Task) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return taskList.count()
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(taskList[position])
    }

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(task: Task) {
            itemView.textView.text = task.title+'\n'+task.description+'\n'

            itemView.deleteButton.setOnClickListener {
                onDeleteClickListener?.invoke(task)
            }
        }
    }
}