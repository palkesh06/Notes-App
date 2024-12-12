package com.example.notesapp.presentation.add_edit_notes

import android.annotation.SuppressLint
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notesapp.domain.model.NotesModel
import com.example.notesapp.presentation.add_edit_notes.components.TransparentFieldTextField
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddEditNoteScreen(
    navController: NavController,
    noteColor: Int,
    viewmodel: AddEditNoteViewModel = hiltViewModel()
) {

    val titleState = viewmodel.noteTitle.value
    val contentState = viewmodel.noteContent.value

    val scaffoldState = SnackbarHostState()
    val scope = rememberCoroutineScope()

    val backgroundColorAnimatable = remember {
        Animatable(Color(if (noteColor == -1) viewmodel.noteColor.value else noteColor))
    }

    LaunchedEffect(key1 = true) {
        viewmodel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditNoteUIEvent.ShowSnackbar -> {
                    scaffoldState.showSnackbar(message = event.message)
                }
                AddEditNoteUIEvent.SaveNote -> {
                    navController.navigateUp()
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewmodel.onEvent(AddEditNoteEvent.SaveNote)
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = "save"
                )
            }
        },
        snackbarHost = { scaffoldState }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColorAnimatable.value)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                NotesModel.noteColors.forEach { color ->
                    val colorInt = color.toArgb()
                    Box(modifier = Modifier
                        .size(50.dp)
                        .shadow(15.dp, CircleShape)
                        .clip(CircleShape)
                        .background(Color(colorInt))
                        .border(
                            width = 3.dp,
                            color = if (viewmodel.noteColor.value == colorInt) Color.Black else Color.Transparent,
                            shape = CircleShape
                        )
                        .clickable {
                            scope.launch {
                                backgroundColorAnimatable.animateTo(
                                    targetValue = Color(colorInt),
                                    animationSpec = tween(
                                        durationMillis = 500
                                    )
                                )
                                viewmodel.onEvent(AddEditNoteEvent.ChangeColor(colorInt))
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            TransparentFieldTextField(
                text = titleState.text,
                hint = titleState.hint,
                hintVisible = titleState.hintVisible,
                onValueChange = {
                    viewmodel.onEvent(AddEditNoteEvent.EnteredTitle(it))
                },
                textStyle = MaterialTheme.typography.headlineSmall,
                singleLine = true,
                onFocusChange = {
                    viewmodel.onEvent(AddEditNoteEvent.ChangeTitleFocus(it))
                },
                modifier = Modifier.testTag("title_edit_text")
            )

            Spacer(modifier = Modifier.height(16.dp))

            TransparentFieldTextField(
                text = contentState.text,
                hint = contentState.hint,
                hintVisible = contentState.hintVisible,
                onValueChange = {
                    viewmodel.onEvent(AddEditNoteEvent.EnteredContent(it))
                },
                textStyle = MaterialTheme.typography.bodyLarge,
                singleLine = false,
                onFocusChange = {
                    viewmodel.onEvent(AddEditNoteEvent.ChangeContentFocus(it))
                },
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("content_edit_text")
            )
        }
    }
}