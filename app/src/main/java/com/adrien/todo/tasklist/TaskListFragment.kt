package com.adrien.todo.tasklist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.adrien.todo.R
import com.adrien.todo.network.Api
import com.adrien.todo.task.TaskActivity
import kotlinx.android.synthetic.main.fragment_task_list.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class TaskListFragment : Fragment() {

    private val taskList = mutableListOf<Task>(
        Task(id = "id_1", title = "Task 1", description = "description 1"),
        Task(id = "id_2", title = "Task 2"),
        Task(id = "id_3", title = "Task 3")
    )

    private val coroutineScope = MainScope()

    val ADD_TASK_REQUEST_CODE = 777
    val adapter = TaskListAdapter(taskList)

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
        recycleerViewExemple.adapter = adapter

        floatingActionButton.setOnClickListener {
            val intent = Intent(activity, TaskActivity::class.java)
            startActivityForResult(intent, ADD_TASK_REQUEST_CODE)
        }

        adapter.onDeleteClickListener = { task ->
            taskList.remove(task)
            adapter.notifyDataSetChanged();
        }
        adapter.onEditClickListener = { task ->
            val intent = Intent(activity, TaskActivity::class.java)
            intent.putExtra("task", task)
            startActivityForResult(intent, ADD_TASK_REQUEST_CODE)
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_TASK_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val reply = data!!.getSerializableExtra("task") as Task
                val index = taskList.indexOfFirst { it.id == reply.id }

                if (index != -1) {
                    taskList[index] = reply
                } else {
                    taskList.add(reply)
                }
                adapter.notifyDataSetChanged()
            }
        }
    }

    public override fun onResume() {
        super.onResume()

        coroutineScope.launch {
            val userData = Api.userService.getInfo().body()!!
            userInfo.text = "${userData.firstName} ${userData.lastName}"
        }
    }

    public override fun onDestroy() {
        super.onDestroy()

        coroutineScope.cancel()
    }
}