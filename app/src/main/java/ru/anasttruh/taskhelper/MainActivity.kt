package ru.anasttruh.taskhelper

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import ru.anasttruh.taskhelper.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val taskList = mutableListOf<Task>()
    private lateinit var adapter: TaskAdapter

    companion object {
        private const val ADD_TASK_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root) // <-- исправлено

        binding.taskList.layoutManager = LinearLayoutManager(this) // <-- добавлено

        adapter = TaskAdapter(taskList) { task ->
            Log.d("TaskHelper", "Click on task: ${task.title}")
        }

        binding.taskList.adapter = adapter

        binding.btnAddTask.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivityForResult(intent, ADD_TASK_REQUEST_CODE)
        }

        binding.btnStats.setOnClickListener {
            val intent = Intent(this, StatsActivity::class.java)
            startActivity(intent)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == ADD_TASK_REQUEST_CODE && resultCode == RESULT_OK && data != null){
            val task = data.getSerializableExtra("task") as? Task
            task?.let{
                taskList.add(it)
                adapter.notifyDataSetChanged()
                Log.d("TaskHelper", "Добавлена задача: ${it.title}")
            }
        }
    }
}