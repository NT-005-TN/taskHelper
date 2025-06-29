package ru.anasttruh.taskhelper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TaskAdapter(
    private val tasks: MutableList<Task>,
    private val onItemClick: (Task) -> Unit
): RecyclerView.Adapter<TaskAdapter.TaskViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.bind(task, onItemClick)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    class TaskViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val titleTextView: TextView = itemView.findViewById(R.id.textTitle)
        private val descTextView: TextView = itemView.findViewById(R.id.textDescription)
        private val deadlineTextView: TextView = itemView.findViewById(R.id.textDeadline)

        fun bind(task: Task, onItemClick: (Task) -> Unit){
            titleTextView.text = task.title
            descTextView.text = task.fullDesc
            if (task.deadline == null || task.deadline == "null") {
                deadlineTextView.text = ""
            } else {
                deadlineTextView.text = secToDate(task.deadline)
            }
            itemView.setOnClickListener {
                onItemClick(task)
            }
        }

        fun secToDate(timestampStr: String): String {
            val timestamp = timestampStr.toLong()
            val date = Date(timestamp)

            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            return "Срок задачи ${sdf.format(date)}"
        }
    }


}