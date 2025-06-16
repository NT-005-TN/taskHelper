package ru.anasttruh.taskhelper

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
class TaskEntity (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val desc: String,
    val fullDesc: String,
    val deadline: String,
    val importance: String,
    val color: String
){
}