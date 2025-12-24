//package com.example.todoapp.data.repository
//
//import com.example.todoapp.data.model.Todo
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.firestore.FirebaseFirestore
//import javax.inject.Inject
//import javax.inject.Singleton
//
//@Singleton
//class TodoRepository @Inject constructor(
//    private val firestore: FirebaseFirestore,
//    private val auth: FirebaseAuth
//) {
//
//    private val todosRef
//        get() = firestore.collection("todos")
//
//    private fun userId(): String =
//        auth.currentUser?.uid ?: throw Exception("User not logged in")
//
//    /* ---------------- ADD ---------------- */
//    fun addTodo(
//        todo: Todo,
//        onResult: (Boolean, String) -> Unit
//    ) {
//        val doc = todosRef.document()
//        val newTodo = todo.copy(
//            id = doc.id,
//            userId = userId()
//        )
//
//        doc.set(newTodo)
//            .addOnSuccessListener {
//                onResult(true, "Todo added successfully")
//            }
//            .addOnFailureListener {
//                onResult(false, it.message ?: "Failed to add todo")
//            }
//    }
//
//    /* ---------------- UPDATE ---------------- */
//    fun updateTodo(
//        todo: Todo,
//        onResult: (Boolean, String) -> Unit
//    ) {
//        todosRef.document(todo.id)
//            .set(todo)
//            .addOnSuccessListener {
//                onResult(true, "Todo updated successfully")
//            }
//            .addOnFailureListener {
//                onResult(false, it.message ?: "Failed to update todo")
//            }
//    }
//
//    /* ---------------- DELETE ---------------- */
//    fun deleteTodo(
//        id: String,
//        onResult: (Boolean, String) -> Unit
//    ) {
//        todosRef.document(id)
//            .delete()
//            .addOnSuccessListener {
//                onResult(true, "Todo deleted")
//            }
//            .addOnFailureListener {
//                onResult(false, it.message ?: "Delete failed")
//            }
//    }
//
//    /* ---------------- GET ALL ---------------- */
//    fun listenTodos(
//        onResult: (List<Todo>) -> Unit
//    ) {
//        todosRef
//            .whereEqualTo("userId", userId())
//            .orderBy("createdAt")
//            .addSnapshotListener { snapshot, _ ->
//                val todos = snapshot?.toObjects(Todo::class.java) ?: emptyList()
//                onResult(todos)
//            }
//    }
//
//    /* ---------------- GET BY ID ---------------- */
//    fun getTodoById(
//        id: String,
//        onResult: (Todo?) -> Unit
//    ) {
//        todosRef.document(id).get()
//            .addOnSuccessListener {
//                onResult(it.toObject(Todo::class.java))
//            }
//            .addOnFailureListener {
//                onResult(null)
//            }
//    }
//}
package com.example.todoapp.data.repository

import com.example.todoapp.data.model.Todo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class TodoRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) {

    private fun todosRef() =
        firestore.collection("todos")
            .document(auth.currentUser!!.uid)
            .collection("items")

    /* ---------------- REALTIME TODOS ---------------- */

    fun getTodos(): Flow<List<Todo>> = callbackFlow {
        val listener = todosRef()
            .orderBy("createdAt")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                val todos = snapshot?.documents?.mapNotNull {
                    it.toObject(Todo::class.java)?.copy(id = it.id)
                } ?: emptyList()

                trySend(todos)
            }

        awaitClose { listener.remove() }
    }

    /* ---------------- ADD ---------------- */

    suspend fun addTodo(todo: Todo) {
        todosRef().add(todo)
    }

    /* ---------------- UPDATE ---------------- */

    suspend fun updateTodo(todo: Todo) {
        todosRef().document(todo.id).set(todo)
    }

    /* ---------------- DELETE ---------------- */

    suspend fun deleteTodo(todoId: String) {
        todosRef().document(todoId).delete()
    }

    /* ---------------- TOGGLE STATUS ---------------- */

    suspend fun toggleTodo(todoId: String, completed: Boolean) {
        todosRef().document(todoId)
            .update("isCompleted", completed)
    }
}
