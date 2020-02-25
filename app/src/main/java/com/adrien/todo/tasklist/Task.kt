package com.adrien.todo.tasklist

import java.io.Serializable

class Task(var id: String, var title: String, var description: String = "Default description") : Serializable {

}