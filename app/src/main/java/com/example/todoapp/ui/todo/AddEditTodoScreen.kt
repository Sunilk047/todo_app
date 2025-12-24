package com.example.todoapp.ui.todo

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.todoapp.data.model.Todo
//import com.example.todoapp.viewmodel.ToDoViewModel
import com.example.todoapp.ui.components.AppButton
import com.example.todoapp.ui.components.AppTextField
import com.example.todoapp.ui.theme.bluePrimary
import com.example.todoapp.ui.theme.white
import com.example.todoapp.viewmodel.TodoViewModel
import java.util.Calendar
import java.util.Date
import java.text.SimpleDateFormat
import java.util.Locale

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun AddEditToDoScreen(
//    navController: NavController,
//    todoId: String? = null,
//    viewModel: TodoViewModel = hiltViewModel()
//) {
//    val context = LocalContext.current
//    val todos by viewModel.todos.collectAsState()
//
//    val isEditMode = todoId != null
//    val existingTodo = todos.firstOrNull { it.id == todoId }
//
//    var title by remember { mutableStateOf(existingTodo?.title ?: "") }
//    var description by remember { mutableStateOf(existingTodo?.description ?: "") }
//    var isCompleted by remember { mutableStateOf(existingTodo?.isCompleted ?: false) }
//
//    val calendar = Calendar.getInstance()
//    var selectedDate by remember {
//        mutableStateOf(existingTodo?.dueDate ?: calendar.timeInMillis)
//    }
//
//    var titleError by remember { mutableStateOf(false) }
//    var statusExpanded by remember { mutableStateOf(false) }
//
//    val uiDateFormat = remember {
//        SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
//    }
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = {
//                    Text(if (isEditMode) "Edit ToDo" else "Add ToDo", color = white)
//                },
//                navigationIcon = {
//                    IconButton(onClick = { navController.popBackStack() }) {
//                        Icon(Icons.Default.ArrowBack, null, tint = white)
//                    }
//                },
//                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
//                    containerColor = bluePrimary
//                )
//            )
//        }
//    ) { padding ->
//
//        Column(
//            modifier = Modifier
//                .padding(padding)
//                .padding(16.dp)
//                .fillMaxSize(),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//
//            Surface(
//                shape = RoundedCornerShape(16.dp),
//                shadowElevation = 6.dp,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//
//                Column(modifier = Modifier.padding(20.dp)) {
//
//                    /* -------- Title -------- */
//                    AppTextField(
//                        value = title,
//                        label = "ToDo Title",
//                        leadingIcon = { Icon(Icons.Default.List, null) },
//                        isError = titleError,
//                        onValueChange = {
//                            title = it
//                            titleError = false
//                        }
//                    )
//
//                    Spacer(Modifier.height(8.dp))
//
//                    /* -------- Description -------- */
//                    AppTextField(
//                        value = description,
//                        label = "Description",
//                        leadingIcon = { Icon(Icons.Default.Description, null) },
//                        onValueChange = { description = it }
//                    )
//
//                    Spacer(Modifier.height(8.dp))
//
//                    /* -------- Due Date -------- */
//                    OutlinedTextField(
//                        value = uiDateFormat.format(Date(selectedDate)),
//                        onValueChange = {},
//                        label = { Text("Due Date") },
//                        readOnly = true,
//                        leadingIcon = { Icon(Icons.Default.DateRange, null) },
//                        trailingIcon = {
//                            IconButton(onClick = {
//                                DatePickerDialog(
//                                    context,
//                                    { _, y, m, d ->
//                                        calendar.set(y, m, d)
//                                        selectedDate = calendar.timeInMillis
//                                    },
//                                    calendar.get(Calendar.YEAR),
//                                    calendar.get(Calendar.MONTH),
//                                    calendar.get(Calendar.DAY_OF_MONTH)
//                                ).show()
//                            }) {
//                                Icon(Icons.Default.Edit, null)
//                            }
//                        },
//                        modifier = Modifier.fillMaxWidth()
//                    )
//
//                    Spacer(Modifier.height(16.dp))
//
//                    /* -------- Status -------- */
//                    ExposedDropdownMenuBox(
//                        expanded = statusExpanded,
//                        onExpandedChange = { statusExpanded = !statusExpanded }
//                    ) {
//                        OutlinedTextField(
//                            value = if (isCompleted) "Completed" else "Pending",
//                            onValueChange = {},
//                            readOnly = true,
//                            label = { Text("Status") },
//                            leadingIcon = { Icon(Icons.Default.Flag, null) },
//                            trailingIcon = {
//                                ExposedDropdownMenuDefaults.TrailingIcon(statusExpanded)
//                            },
//                            modifier = Modifier
//                                .menuAnchor()
//                                .fillMaxWidth()
//                        )
//
//                        ExposedDropdownMenu(
//                            expanded = statusExpanded,
//                            onDismissRequest = { statusExpanded = false }
//                        ) {
//                            DropdownMenuItem(
//                                text = { Text("Pending") },
//                                onClick = {
//                                    isCompleted = false
//                                    statusExpanded = false
//                                }
//                            )
//                            DropdownMenuItem(
//                                text = { Text("Completed") },
//                                onClick = {
//                                    isCompleted = true
//                                    statusExpanded = false
//                                }
//                            )
//                        }
//                    }
//
//                    Spacer(Modifier.height(24.dp))
//
//                    /* -------- Save / Update -------- */
//                    AppButton(
//                        text = if (isEditMode) "Update ToDo" else "Save ToDo",
//                        onClick = {
//                            titleError = title.isBlank()
//                            if (titleError) {
//                                Toast.makeText(context, "Title required", Toast.LENGTH_SHORT).show()
//                                return@AppButton
//                            }
//
//                            if (isEditMode && existingTodo != null) {
//                                viewModel.updateTodo(
//                                    existingTodo.copy(
//                                        title = title,
//                                        description = description,
//                                        dueDate = selectedDate,
//                                        isCompleted = isCompleted
//                                    )
//                                )
//                            } else {
//                                viewModel.addTodo(
//                                    title = title,
//                                    desc = description,
//                                    date = selectedDate
//                                )
//                            }
//
//                            navController.popBackStack()
//                        }
//                    )
//                }
//            }
//        }
//    }
//}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditToDoScreen(
    navController: NavController,
    todoId: String? = null,
    viewModel: TodoViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val todos by viewModel.todos.collectAsState()

    val isEditMode = todoId != null
    val existingTodo = todos.firstOrNull { it.id == todoId }

    /* -------- STATE -------- */

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var isCompleted by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf(System.currentTimeMillis()) }

    var titleError by remember { mutableStateOf(false) }
    var statusExpanded by remember { mutableStateOf(false) }

    val calendar = remember { Calendar.getInstance() }

    val uiDateFormat = remember {
        SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    }

    /* 🔑 PREFILL WHEN EDIT MODE */
    LaunchedEffect(existingTodo) {
        existingTodo?.let {
            title = it.title
            description = it.description
            isCompleted = it.completed
            selectedDate = it.dueDate
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (isEditMode) "Edit ToDo" else "Add ToDo",
                        color = white
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, null, tint = white)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = bluePrimary
                )
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Surface(
                shape = RoundedCornerShape(16.dp),
                shadowElevation = 6.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(20.dp)) {

                    /* -------- Title -------- */
                    AppTextField(
                        value = title,
                        label = "ToDo Title",
                        leadingIcon = { Icon(Icons.Default.List, null) },
                        isError = titleError,
                        onValueChange = {
                            title = it
                            titleError = false
                        }
                    )

                    Spacer(Modifier.height(8.dp))

                    /* -------- Description -------- */
                    AppTextField(
                        value = description,
                        label = "Description",
                        leadingIcon = { Icon(Icons.Default.Description, null) },
                        onValueChange = { description = it }
                    )

                    Spacer(Modifier.height(8.dp))

                    /* -------- Due Date -------- */
                    OutlinedTextField(
                        value = uiDateFormat.format(Date(selectedDate)),
                        onValueChange = {},
                        label = { Text("Due Date") },
                        readOnly = true,
                        leadingIcon = { Icon(Icons.Default.DateRange, null) },
                        trailingIcon = {
                            IconButton(onClick = {
                                DatePickerDialog(
                                    context,
                                    { _, y, m, d ->
                                        calendar.set(y, m, d)
                                        selectedDate = calendar.timeInMillis
                                    },
                                    calendar.get(Calendar.YEAR),
                                    calendar.get(Calendar.MONTH),
                                    calendar.get(Calendar.DAY_OF_MONTH)
                                ).show()
                            }) {
                                Icon(Icons.Default.Edit, null)
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(16.dp))

                    /* -------- Status -------- */
                    ExposedDropdownMenuBox(
                        expanded = statusExpanded,
                        onExpandedChange = { statusExpanded = !statusExpanded }
                    ) {
                        OutlinedTextField(
                            value = if (isCompleted) "Completed" else "Pending",
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Status") },
                            leadingIcon = { Icon(Icons.Default.Flag, null) },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(statusExpanded)
                            },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                        )

                        ExposedDropdownMenu(
                            expanded = statusExpanded,
                            onDismissRequest = { statusExpanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Pending") },
                                onClick = {
                                    isCompleted = false
                                    statusExpanded = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Completed") },
                                onClick = {
                                    isCompleted = true
                                    statusExpanded = false
                                }
                            )
                        }
                    }

                    Spacer(Modifier.height(24.dp))

                    /* -------- Save / Update -------- */
                    AppButton(
                        text = if (isEditMode) "Update ToDo" else "Save ToDo",
                        onClick = {
                            titleError = title.isBlank()
                            if (titleError) {
                                Toast.makeText(
                                    context,
                                    "Title required",
                                    Toast.LENGTH_SHORT
                                ).show()
                                return@AppButton
                            }

                            if (isEditMode && existingTodo != null) {
                                viewModel.updateTodo(
                                    existingTodo.copy(
                                        title = title,
                                        description = description,
                                        dueDate = selectedDate,
                                        completed = isCompleted
                                    )
                                )
                            } else {
                                viewModel.addTodo(
                                    title = title,
                                    desc = description,
                                    date = selectedDate
                                )
                            }

                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}

