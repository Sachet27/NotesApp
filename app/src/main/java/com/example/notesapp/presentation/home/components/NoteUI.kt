package com.example.notesapp.presentation.home.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.example.notesapp.R
import com.example.notesapp.domain.Note
import com.example.notesapp.presentation.NoteActions
import com.example.notesapp.ui.theme.NotesAppTheme
import com.example.notesapp.ui.theme.lighterGreen

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteCard(
    note: Note,
    onDeleteNote: () ->Unit,
    onNoteClick: ()-> Unit,
    onNoteFavorite: ()->Unit,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        ),
        modifier= modifier
            .width(200.dp)
            .sizeIn(maxHeight = 300.dp)
            .combinedClickable(
                onClick = onNoteClick,
                onLongClick = onDeleteNote
            ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary)
    ) {
        Column (
            modifier= Modifier.fillMaxWidth().padding(12.dp)
        ){
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier= Modifier.weight(0.8f),
                    text = note.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
                IconButton(
                    modifier = Modifier.weight(0.2f),
                    onClick = onNoteFavorite
                ){
                    Icon(
                        imageVector = if(note.isFavourited) ImageVector.vectorResource(R.drawable.star_filled) else ImageVector.vectorResource(R.drawable.star_outline),
                        contentDescription = null,
                        tint= lighterGreen
                    )
                }

            }
            Text(
                text = note.description,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis
            )
            if(note.uri!=null){
                Spacer(Modifier.height(8.dp))
                AsyncImage(
                    model = note.uri,
                    contentDescription = "Note Image",
                    contentScale = ContentScale.Fit
                )
            }
            }
        }
    }

@PreviewLightDark
@Composable
private fun NoteCardPreview() {
    NotesAppTheme {
        NoteCard(
            note = Note(
                id = 1,
                title = "Going gym",
                description = "Going gym after tomorrow.Not today tho.\nLets gooooo.",
                uri = null,
                isFavourited = false
            ),
            onNoteClick = {},
            onNoteFavorite = {},
            onDeleteNote = {},
        )
    }
}