package com.example.notesapp.presentation.notes.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.notesapp.domain.util.NotesOrder
import com.example.notesapp.domain.util.OrderType


@Composable
fun OrderSection(
    noteOrder: NotesOrder,
    onOrderChange: (NotesOrder) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .testTag("order_section_test")
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            DefaultRadioButton(
                text = "Title",
                selected = noteOrder is NotesOrder.Title
            ) {
                onOrderChange(NotesOrder.Title(noteOrder.order))
            }
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Date",
                selected = noteOrder is NotesOrder.Date
            ) {
                onOrderChange(NotesOrder.Date(noteOrder.order))
            }
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Color",
                selected = noteOrder is NotesOrder.Color
            ) {
                onOrderChange(NotesOrder.Color(noteOrder.order))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            DefaultRadioButton(
                text = "Ascending",
                selected = noteOrder.order is OrderType.Ascending
            ) {
                onOrderChange(noteOrder.copy(OrderType.Ascending))
            }
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Descending",
                selected = noteOrder.order is OrderType.Descending
            ) {
                onOrderChange(noteOrder.copy(OrderType.Descending))
            }
        }
    }
}

