package ru.anasttruh.taskhelper

import android.app.Activity
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ru.anasttruh.taskhelper.databinding.ActivityAddTaskBinding

class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.addTask)

        val importanceAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.importance_data,
            android.R.layout.simple_spinner_item
        ).also{
            adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        }

        binding.importanceSpinner.adapter = importanceAdapter


        val colorAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.color_data,
            android.R.layout.simple_spinner_item
        ).also{
            adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        }

        binding.colorSpinner.adapter = colorAdapter

        var importance = binding.importanceSpinner.selectedItem as String
        var color = binding.colorSpinner.selectedItem as String

        var importancePosition = binding.importanceSpinner.selectedItemPosition
        var colorPosition = binding.colorSpinner.selectedItemPosition

        var selectedDate: Long? = null
        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance().apply {
                set(year, month, dayOfMonth)
            }
            selectedDate = calendar.timeInMillis
            println(selectedDate)
        }

        binding.btnCreateTask.setOnClickListener {
            val title = binding.enterTitleText.text.toString().trim()
            val desc = binding.enterDescText.text.toString().trim()

            when {
                title.isEmpty() -> {
                    Toast.makeText(this, "Введите название", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                desc.isEmpty() -> {
                    Toast.makeText(this, "Введите описание", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                colorPosition == 0 -> {
                    color = "null"
                }
                importancePosition == 0 -> {
                    importance = "null"
                }
            }

            val task = Task(
                id = Task.generateId(),
                title = title,
                desc = "null",
                fullDesc = desc,
                deadline = selectedDate.toString(),
                importance = importance,
                color = color,
                )

            val resultIntent = Intent(this, MainActivity::class.java)
            resultIntent.putExtra("task", task)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

    }
}