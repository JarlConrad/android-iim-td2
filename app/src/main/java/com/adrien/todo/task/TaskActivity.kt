package com.adrien.todo.task

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.adrien.todo.R
import com.adrien.todo.tasklist.Task
import kotlinx.android.synthetic.main.activity_task.*
import java.util.*

class TaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)

        val task = intent.getSerializableExtra("task") as? Task
        createTitle.setText(task?.title)
        createDescription.setText(task?.description)

        addCustomItem.setOnClickListener{
            var newTask = Task(
                id = task?.id ?: UUID.randomUUID().toString(),
                title = createTitle.text.toString(),
                description = createDescription.text.toString())
            intent.putExtra("task" , newTask)
            setResult(Activity.RESULT_OK,intent)
            finish()
        }
    }
}
