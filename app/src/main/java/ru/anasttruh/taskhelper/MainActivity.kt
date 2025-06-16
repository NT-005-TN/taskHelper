package ru.anasttruh.taskhelper

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ru.anasttruh.taskhelper.AddTaskActivity
import ru.anasttruh.taskhelper.databinding.ActivityMainBinding
import ru.anasttruh.taskhelper.Task
import ru.anasttruh.taskhelper.TaskAdapter
import ru.anasttruh.taskhelper.StatsActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val taskList = mutableListOf<Task>()
    private lateinit var adapter: TaskAdapter

    private val addTaskLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val task = result.data?.getSerializableExtra("task") as? Task
            task?.let {
                taskList.add(it)
                adapter.notifyDataSetChanged()
                Log.d("TaskHelper", "Добавлена задача: ${it.title}")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.taskList.layoutManager = LinearLayoutManager(this)

        adapter = TaskAdapter(taskList) { task ->
            Log.d("TaskHelper", "Click on task: ${task.title}")
        }

        binding.taskList.adapter = adapter

        binding.btnAddTask.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            addTaskLauncher.launch(intent)
        }

        binding.btnStats.setOnClickListener {
            val intent = Intent(this, StatsActivity::class.java)
            startActivity(intent)
        }


    }
}
