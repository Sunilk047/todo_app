//package com.example.todoapp.viewmodel
//
//import androidx.compose.runtime.*
//import androidx.lifecycle.ViewModel
//import com.example.todoapp.data.model.Todo
//import com.example.todoapp.data.repository.TodoRepository
//import dagger.hilt.android.lifecycle.HiltViewModel
//import javax.inject.Inject
//
//@HiltViewModel
//class TodoViewModel @Inject constructor(
//    private val repository: TodoRepository
//) : ViewModel() {
//
//    var todos by mutableStateOf<List<Todo>>(emptyList())
//        private set
//
//    var isLoading by mutableStateOf(false)
//        private set
//
//    init {
//        observeTodos()
//    }
//
//    private fun observeTodos() {
//        repository.listenTodos {
//            todos = it
//        }
//    }
//
//    fun getTodoById(
//        id: String,
//        onResult: (Todo?) -> Unit
//    ) {
//        repository.getTodoById(id, onResult)
//    }
//
//    fun addTodo(
//        title: String,
//        description: String,
//        status: String,
//        dueDate: Long,
//        onResult: (Boolean, String) -> Unit
//    ) {
//        isLoading = true
//        repository.addTodo(
//            Todo(
//                title = title,
//                description = description,
//                status = status,
//                dueDate = dueDate
//            )
//        ) { success, msg ->
//            isLoading = false
//            onResult(success, msg)
//        }
//    }
//
//    fun updateTodo(
//        todo: Todo,
//        onResult: (Boolean, String) -> Unit
//    ) {
//        isLoading = true
//        repository.updateTodo(todo) { success, msg ->
//            isLoading = false
//            onResult(success, msg)
//        }
//    }
//
//    fun deleteTodo(id: String) {
//        repository.deleteTodo(id) { _, _ -> }
//    }
//}

package com.example.todoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.model.Todo
import com.example.todoapp.data.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {

    val todos: StateFlow<List<Todo>> =
        repository.getTodos()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addTodo(title: String, desc: String, date: Long) {
        viewModelScope.launch {
            repository.addTodo(
                Todo(
                    title = title,
                    description = desc,
                    dueDate = date
                )
            )
        }
    }

    fun updateTodo(todo: Todo) {
        viewModelScope.launch {
            repository.updateTodo(todo)
        }
    }

    fun deleteTodo(todoId: String) {
        viewModelScope.launch {
            repository.deleteTodo(todoId)
        }
    }

    fun toggle(todo: Todo) {
        viewModelScope.launch {
            repository.toggleTodo(todo.id, !todo.completed)
        }
    }
}

