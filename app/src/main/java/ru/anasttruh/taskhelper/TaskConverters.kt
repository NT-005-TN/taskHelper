package ru.anasttruh.taskhelper.data

import ru.anasttruh.taskhelper.Task
import ru.anasttruh.taskhelper.TaskEntity

fun Task.toEntity(): TaskEntity = TaskEntity(
    id = this.id,
    title = this.title,
    desc = this.desc,
    fullDesc = this.fullDesc,
    deadline = this.deadline,
    importance = this.importance,
    color = this.color
)

fun TaskEntity.toTask(): Task = Task(
    id = this.id,
    title = this.title,
    desc = this.desc,
    fullDesc = this.fullDesc,
    deadline = this.deadline,
    importance = this.importance,
    color = this.color
)