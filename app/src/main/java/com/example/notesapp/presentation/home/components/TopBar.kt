package com.example.notesapp.presentation.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notesapp.ui.theme.NotesAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    onSearch: ()-> Unit,
    onDrawerClick: ()-> Unit,
    modifier: Modifier = Modifier
) {
TopAppBar(
    title = {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontSize = 25.sp,
            modifier = Modifier.padding(16.dp),
        )
    },
    navigationIcon = {
        IconButton(
            onClick = { onDrawerClick()}
        ) {
            Icon(
                modifier = modifier.size(30.dp),
                imageVector = Icons.Outlined.Menu,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
},
    actions = {
        IconButton(
            onClick = { onSearch()}
        ) {
            Icon(
                modifier = modifier.size(30.dp),
                imageVector = Icons.Outlined.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
)
}

@PreviewLightDark
@Composable
private fun TopAppBarPreview() {
    NotesAppTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {            TopBar(
                title = "My Notes",
                onDrawerClick = {},
                onSearch = { },
          )}
        ) {padding->
            Column(Modifier.padding(padding)) {  }


        }
    }
}