package com.lolakashmir.retailerapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * A reusable AppBar component with common functionality
 *
 * @param title The title to display in the AppBar
 * @param onNavigationClick Callback when the navigation icon is clicked
 * @param showBackButton Whether to show the back button (default: false)
 * @param showMenuIcon Whether to show the menu icon (default: false)
 * @param onMenuClick Callback when the menu icon is clicked
 * @param actions Additional actions to display on the right side of the AppBar
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    title: String,
    onNavigationClick: (() -> Unit)? = null,
    showBackButton: Boolean = false,
    showMenuIcon: Boolean = false,
    onMenuClick: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        },
        navigationIcon = {
            when {
                showBackButton && onNavigationClick != null -> {
                    IconButton(onClick = onNavigationClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
                showMenuIcon && onMenuClick != null -> {
                    IconButton(onClick = onMenuClick) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu"
                        )
                    }
                }
            }
        },
        actions = actions,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        modifier = Modifier
    )
}

/**
 * A preview of the AppBar component
 */
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun AppBarPreview() {
    MaterialTheme {
        Column {
            // Example with title and menu icon
            AppBar(
                title = "Dashboard",
                showMenuIcon = true,
                onMenuClick = { /* Handle menu click */ }
            )

            // Example with back button
            AppBar(
                title = "Details",
                showBackButton = true,
                onNavigationClick = { /* Handle back click */ }
            )

            // Example with actions
            AppBar(
                title = "Profile",
                showBackButton = true,
                onNavigationClick = { /* Handle back click */ },
                actions = {
                    IconButton(onClick = { /* Handle action */ }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More options"
                        )
                    }
                }
            )
        }
    }
}