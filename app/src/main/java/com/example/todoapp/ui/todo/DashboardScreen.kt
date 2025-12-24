package com.example.todoapp.ui.todo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.todoapp.ui.components.AppDrawer
import com.example.todoapp.ui.components.DashboardCard
import com.example.todoapp.viewmodel.AuthViewModel
import com.example.todoapp.viewmodel.TodoViewModel
import com.example.todoapp.ui.theme.bluePrimary
import com.example.todoapp.ui.theme.white
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navController: NavController,
    todoViewModel: TodoViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel()
) {

    /* ---------------- STATE ---------------- */

    val todos by todoViewModel.todos.collectAsState()

    val total = todos.size
    val completed = todos.count { it.completed }
    val pending = todos.count { !it.completed }

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    /* ---------------- DRAWER ---------------- */

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.75f)
                    .clip(
                        RoundedCornerShape(
                            topEnd = 24.dp,
                            bottomEnd = 24.dp
                        )
                    )
            ) {
                AppDrawer(navController = navController)
            }
        }
    ) {

        /* ---------------- SCAFFOLD ---------------- */

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Dashboard", color = white) },
                    navigationIcon = {
                        IconButton(
                            onClick = { scope.launch { drawerState.open() } }
                        ) {
                            Icon(Icons.Default.Menu, contentDescription = null, tint = white)
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = bluePrimary
                    )
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    containerColor = bluePrimary,
                    onClick = { navController.navigate(Routes.ADD_EDIT) }
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Todo")
                }
            }
        ) { padding ->

            /* ---------------- CONTENT ---------------- */

            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize()
            ) {

                /* -------- Greeting -------- */

                Text(
                    text = "Hello, ${authViewModel.userName}",
                    style = MaterialTheme.typography.headlineSmall
                )

                Text(
                    text = "Here’s an overview of your tasks",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(24.dp))

                /* -------- Stats -------- */

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    DashboardCard(
                        title = "Total",
                        count = total,
                        icon = Icons.Default.List,
                        modifier = Modifier.weight(1f)
                    )
                    DashboardCard(
                        title = "Completed",
                        count = completed,
                        icon = Icons.Default.CheckCircle,
                        modifier = Modifier.weight(1f)
                    )
                    DashboardCard(
                        title = "Pending",
                        count = pending,
                        icon = Icons.Default.Schedule,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                /* -------- Todo List / Empty State -------- */
                Text(
                    text = "ToDo List",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(12.dp))
                if (todos.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.List,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(64.dp)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "No Todo Found",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(todos, key = { it.id }) { todo ->
                            TodoItem(
                                todo = todo,
                                onEdit = {
                                    navController.navigate("${Routes.ADD_EDIT}?todoId=${todo.id}")
                                },
                                onDelete = {
                                    todoViewModel.deleteTodo(todo.id)
                                },
                                onToggleStatus = {
                                    todoViewModel.toggle(todo)
                                }
                            )

                        }
                    }
                }
            }
        }
    }
}
