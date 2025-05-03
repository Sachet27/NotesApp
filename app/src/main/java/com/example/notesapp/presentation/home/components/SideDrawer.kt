package com.example.notesapp.presentation.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.notesapp.R


@Composable
fun NavDrawer(
    isDarkTheme: Boolean,
    onThemeToggle: (Boolean) -> Unit,
    enabled: Boolean= false,
    drawerState: DrawerState,
    items: List<NavigationItems>,
    modifier: Modifier= Modifier,
    onItemClick: (NavigationItems)->Unit,
    content: @Composable ()->Unit
    ){
    var selectedItemId by remember{ mutableStateOf(DrawerItems.HOME) }

    ModalNavigationDrawer(
        modifier = modifier,
        drawerState = drawerState,
        gesturesEnabled = enabled,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .width(300.dp)
                    .fillMaxHeight()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.3f)
                        .background(MaterialTheme.colorScheme.surfaceContainerLow),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.logo_transparent),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
                Column(
                    modifier= Modifier.weight(1f)
                ){
                    Spacer(Modifier.height(8.dp))
                    items.forEachIndexed { index, item ->
                        NavigationDrawerItem(
                            label = {
                                Text(
                                    text = item.title,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            },
                            selected = item.id == selectedItemId,
                            onClick = {
                                selectedItemId= item.id
                                onItemClick(item)
                            },
                            icon = {
                                Icon(
                                    imageVector = ImageVector.vectorResource(item.iconRes),
                                    contentDescription = null,
                                    modifier = modifier.size(25.dp)
                                )
                            },
                            badge = {
                                if(item.id== DrawerItems.SETTINGS){
                                    Switch(
                                        checked = isDarkTheme,
                                        onCheckedChange = onThemeToggle
                                    )
                                }
                            }
                        )
                    }
                }
            }
            }
    )
        {
        content()
    }
}

