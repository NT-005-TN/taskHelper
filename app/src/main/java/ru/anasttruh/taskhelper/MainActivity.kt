package ru.anasttruh.taskhelper

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import ru.anasttruh.taskhelper.data.toEntity
import ru.anasttruh.taskhelper.data.toTask
import ru.anasttruh.taskhelper.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val taskList = mutableListOf<Task>()
    private lateinit var adapter: TaskAdapter

    private lateinit var db: AppDatabase
    private lateinit var dao: TaskDao

    private val addTaskLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val task = result.data?.getSerializableExtra("task") as? Task
            task?.let {
                taskList.add(it)
                lifecycleScope.launch {
                    dao.insertTask(it.toEntity())
                    loadTasks() // чтобы отобразить сохранённую задачу
                }
                adapter.notifyDataSetChanged()
                Log.d("TaskHelper", "Добавлена задача: ${it.title}")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getDatabase(this)
        dao = db.taskDao()

        adapter = TaskAdapter(taskList) { task ->
            Log.d("TaskHelper", "Click on task: ${task.title}")
        }

        binding.taskList.layoutManager = LinearLayoutManager(this)
        binding.taskList.adapter = adapter


        binding.btnAddTask.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            addTaskLauncher.launch(intent)
        }

        binding.btnStats.setOnClickListener {
            val intent = Intent(this, StatsActivity::class.java)
            startActivity(intent)
        }

        loadTasks()
    }

    private fun loadTasks(){
        lifecycleScope.launch {
            val entites = dao.getAllTasks()
            taskList.clear()
            taskList.addAll(entites.map { it.toTask() })
            adapter.notifyDataSetChanged()
        }
    }
}
