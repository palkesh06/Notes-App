package com.example.notesapp.presentation.add_edit_notes.components


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Composable
fun TransparentFieldTextField(
    modifier: Modifier = Modifier,
    text: String,
    hint: String,
    hintVisible: Boolean,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle,
    singleLine: Boolean,
    onFocusChange: (FocusState) -> Unit
) {
    Box {
        BasicTextField(value = text,
            onValueChange = onValueChange,
            singleLine = singleLine,
            textStyle = textStyle,
            modifier = modifier
                .fillMaxWidth()
                .onFocusChanged { onFocusChange(it) }
        )

        if (hintVisible) {
            Text(
                text = hint,
                style = textStyle,
                color = Color.DarkGray
            )
        }
    }
}