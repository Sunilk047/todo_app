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

    private fun todosRef(uid: String) =
        firestore.collection("todos")
            .document(uid)
            .collection("items")
    /* ---------------- REALTIME TODOS ---------------- */

    fun getTodos(): Flow<List<Todo>> = callbackFlow {

        val uid = auth.currentUser?.uid
        if (uid == null) {
            trySend(emptyList())
            close()
            return@callbackFlow
        }

        val listener = todosRef(uid)
            .orderBy("createdAt")
            .addSnapshotListener { snapshot, error ->

                if (error != null) {
                    // ✅ IGNORE PERMISSION DENIED (logout case)
                    if (error.code == com.google.firebase.firestore.FirebaseFirestoreException.Code.PERMISSION_DENIED) {
                        trySend(emptyList())
                        return@addSnapshotListener
                    }
                    return@addSnapshotListener
                }

                val todos = snapshot?.documents
                    ?.mapNotNull { it.toObject(Todo::class.java)?.copy(id = it.id) }
                    ?: emptyList()

                trySend(todos)
            }

        awaitClose { listener.remove() }
    }

    /* ---------------- ADD ---------------- */

    suspend fun addTodo(todo: Todo) {
        auth.currentUser?.uid?.let { uid ->
            todosRef(uid).add(todo)
        }
    }

    /* ---------------- UPDATE ---------------- */

    suspend fun updateTodo(todo: Todo) {
        auth.currentUser?.uid?.let { uid ->
            todosRef(uid).document(todo.id).set(todo)
        }
    }

    /* ---------------- DELETE ---------------- */

    //    suspend fun deleteTodo(todoId: String) {
//        todosRef().document(todoId).delete()
//    }
    suspend fun deleteTodo(todoId: String) {
        auth.currentUser?.uid?.let { uid ->
            todosRef(uid).document(todoId).delete()
        }
    }
}
