package com.example.todoapp

import android.os.Bundle
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import com.example.todoapp.ui.navigation.AppNavGraph
import com.example.todoapp.ui.theme.ToDoAppTheme
import androidx.fragment.app.FragmentActivity

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ToDoAppTheme {
                AppNavGraph()
            }
        }
    }
}

