//package com.example.todoapp.ui.todo
//
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxHeight
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.itemsIndexed
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Add
//import androidx.compose.material.icons.filled.Menu
//import androidx.compose.material3.DrawerValue
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.FloatingActionButton
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.ModalNavigationDrawer
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.material3.TopAppBar
//import androidx.compose.material3.TopAppBarDefaults
//import androidx.compose.material3.rememberDrawerState
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.runtime.*
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavController
//import com.example.todoapp.ui.components.AppDrawer
//import com.example.todoapp.ui.theme.Purple40
//import com.example.todoapp.ui.theme.bluePrimary
//import com.example.todoapp.ui.theme.white
//import kotlinx.coroutines.launch
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun TodoListScreen(nav: NavController) {
//
//    // Create a mutable state list of Todo items
////    val todos = remember {
////        mutableStateListOf(
////            Todo("1", "Buy Milk", "From store", false),
////            Todo("2", "Workout", "Morning exercise", true)
////        )
////    }
//    val drawerState = rememberDrawerState(DrawerValue.Closed)
//    val scope = rememberCoroutineScope()
//
//
//    ModalNavigationDrawer(
//        drawerState = drawerState,
//        drawerContent = {
//            Box(
//                modifier = Modifier
//                    .fillMaxHeight()
//                    .fillMaxWidth(0.75f)
//                    .clip(RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp))
//
//            ) {
//                AppDrawer(navController = nav)
//            }
//        }
//    ) {
//        Scaffold(
//            topBar = {
//                TopAppBar(
//                    title = { Text("Dashboard", color = white) },
//                    navigationIcon = {
//                        IconButton(
//                            onClick = { scope.launch { drawerState.open() } }
//                        ) {
//                            Icon(Icons.Default.Menu, null, tint = white)
//                        }
//                    },
//                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
//                        containerColor = bluePrimary
//                    )
//                )
//            },
//            floatingActionButton = {
//                FloatingActionButton(
//                    onClick = { nav.navigate(Routes.ADD_EDIT) }
//                ) {
//                    Icon(Icons.Default.Add, contentDescription = "Add Todo")
//                }
//            }
//        ) { padding ->
//
//            // Using itemsIndexed to iterate over the mutable list
//            LazyColumn(modifier = Modifier.padding(padding)) {}
////                itemsIndexed(todos) { index, todo ->
////                    TodoItem(
////                        todo = todo,
////                        onEdit = {
////                            nav.navigate("${Routes.ADD_EDIT}?todoId=${todo.id}")
////                        },
////                        onDelete = {
////                            todos.removeAt(index) // Removing the todo by index
////                        },
////                        onChecked = {
////                            // Toggle the completion status of the todo
////                            todos[index] = todo.copy(isCompleted = !todo.isCompleted)
////                        }
////                    )
////                }
////            }
//        }
//    }
//}
