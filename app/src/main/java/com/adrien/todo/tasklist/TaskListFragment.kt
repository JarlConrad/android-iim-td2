package com.adrien.todo.tasklist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.adrien.todo.R
import kotlinx.android.synthetic.main.fragment_task_list.*
import java.util.*

class TaskListFragment : Fragment() {

    private val taskList = mutableListOf<Task>(
        Task(id = "id_1", title = "Task 1", description = "description 1"),
        Task(id = "id_2", title = "Task 2"),
        Task(id = "id_3", title = "Task 3")
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_task_list, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycleerViewExemple.layoutManager = LinearLayoutManager(activity)
        recycleerViewExemple.adapter = TaskListAdapter(taskList)
        var adapter: TaskListAdapter = recycleerViewExemple.adapter as TaskListAdapter

        floatingActionButton.setOnClickListener {
            taskList.add(Task(id = UUID.randomUUID().toString(), title = "Task ${taskList.size + 1}"))
            adapter.notifyDataSetChanged();
        }

        adapter.onDeleteClickListener = { task ->
            taskList.remove(task)
            adapter.notifyDataSetChanged();
        }
    }
}