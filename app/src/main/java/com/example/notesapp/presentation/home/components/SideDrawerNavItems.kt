package com.example.notesapp.presentation.home.components

import com.example.notesapp.presentation.home.components.DrawerItems.Companion.toIcon
import com.example.notesapp.presentation.home.components.DrawerItems.Companion.toSelectionString

object SideDrawerNavItems {
    val navItemsList= DrawerItems.list.map {
        NavigationItems(
            id = it,
            title = it.toSelectionString(),
            iconRes = it.toIcon(),
        )
    }
}