package com.example.notesapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.notesapp.presentation.add_edit_notes.AddEditNoteScreen
import com.example.notesapp.presentation.notes.NotesScreen
import com.example.notesapp.presentation.ui.theme.NotesAppTheme
import com.example.notesapp.presentation.util.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesAppTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    HomeScreen(navController)
                }
            }
        }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    NavHost(
        navController = navController as NavHostController,
        startDestination = Screen.NotesScreen.route
    ) {
        composable(route = Screen.NotesScreen.route) {
            NotesScreen(navController = navController)
        }
        composable(
            route = Screen.AddEditNoteScreen.route + "?noteId={noteId}&noteColor={noteColor}",
            arguments = listOf(
                navArgument(name = "noteId") {
                    type = NavType.IntType
                    defaultValue = -1
                },
                navArgument(name = "noteColor") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )) {
            val color = it.arguments?.getInt("noteColor") ?: -1
            AddEditNoteScreen(navController = navController, noteColor = color)
        }

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NotesAppTheme {
        HomeScreen(navController = rememberNavController())
    }
}