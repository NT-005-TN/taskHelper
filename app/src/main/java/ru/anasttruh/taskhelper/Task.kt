package ru.anasttruh.taskhelper

import java.io.Serializable

class Task(
    val id: Int,
    val title: String,
    val desc: String,
    val fullDesc: String,
    val deadline: String,
    val importance: String,
    val color: String,
): Serializable {

    companion object{
        private var lastId = 0
        fun generateId(): Int {
            return lastId++
        }
    }

}