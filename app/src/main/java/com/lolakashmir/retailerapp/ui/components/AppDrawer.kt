package com.lolakashmir.retailerapp.ui.components

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDrawer(
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
    drawerContent: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Surface(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(280.dp),
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp)
            ) {
                drawerContent()
            }
        },
        content = content,
        scrimColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
    )
}

@Composable
fun DrawerHeader(
    userName: String,
    userEmail: String,
    userImageUrl: String? = null,
    onProfileClick: (() -> Unit)? = null
) {
    val content = @androidx.compose.runtime.Composable {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = userName.take(2).uppercase(),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = userName,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = userEmail,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }

    if (onProfileClick != null) {
        Box(
            modifier = Modifier.clickable(onClick = onProfileClick)
        ) {
            content()
        }
    } else {
        content()
    }
}

@Composable
fun DrawerItem(
    icon: ImageVector,
    label: String,
    selected: Boolean = false,
    onClick: () -> Unit,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    showArrow: Boolean = true,
    badge: @Composable (() -> Unit)? = null
) {
    val backgroundColor = if (selected) {
        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.12f)
    } else {
        Color.Transparent
    }

    val contentColor = if (selected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(8.dp)),
        color = backgroundColor,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge,
                color = contentColor,
                modifier = Modifier.weight(1f)
            )

            if (badge != null) {
                Spacer(modifier = Modifier.width(8.dp))
                badge()
            }

            if (showArrow) {
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = contentColor.copy(alpha = 0.6f),
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
fun DrawerDivider(
    modifier: Modifier = Modifier
) {
    Divider(
        modifier = modifier.padding(vertical = 8.dp, horizontal = 16.dp),
        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f),
        thickness = 1.dp
    )
}

@Composable
fun DrawerSectionHeader(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title.uppercase(),
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = modifier.padding(start = 28.dp, top = 16.dp, bottom = 4.dp)
    )
}

@Composable
fun DrawerItemList(
    items: List<DrawerItemData>,
    onItemClick: (String) -> Unit,
    selectedItemId: String? = null,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items.forEach { item ->
            item {
                when (item) {
                    is DrawerItemData.Divider -> {
                        DrawerDivider()
                    }
                    is DrawerItemData.Header -> {
                        DrawerSectionHeader(title = item.title)
                    }
                    is DrawerItemData.Item -> {
                        DrawerItem(
                            icon = item.icon,
                            label = item.label,
                            selected = item.id == selectedItemId,
                            onClick = { onItemClick(item.id) },
                            showArrow = item.showArrow,
                            badge = item.badge
                        )
                    }
                }
            }
        }
    }
}

sealed class DrawerItemData {
    data class Item(
        val id: String,
        val icon: ImageVector,
        val label: String,
        val showArrow: Boolean = true,
        val badge: @Composable (() -> Unit)? = null
    ) : DrawerItemData()

    data class Header(val title: String) : DrawerItemData()
    object Divider : DrawerItemData()
}

@OptIn(ExperimentalMaterial3Api::class)
suspend fun DrawerState.openDrawer() {
    if (isClosed) open()
}

@OptIn(ExperimentalMaterial3Api::class)
suspend fun DrawerState.closeDrawer() {
    if (isOpen) close()
}

@OptIn(ExperimentalMaterial3Api::class)
suspend fun DrawerState.toggleDrawer() {
    if (isOpen) close() else open()
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Preview(showBackground = true)
@Composable
fun AppDrawerPreview() {
    val drawerState = rememberDrawerState(DrawerValue.Open)
    val scope = rememberCoroutineScope()

    MaterialTheme {
        AppDrawer(
            drawerState = drawerState,
            drawerContent = {
                Column {
                    DrawerHeader(
                        userName = "John Doe",
                        userEmail = "john.doe@example.com",
                        onProfileClick = {
                            scope.launch { drawerState.close() }
                        }
                    )

                    DrawerItemList(
                        items = listOf(
                            DrawerItemData.Item(
                                id = "home",
                                icon = Icons.Default.Home,
                                label = "Home"
                            ),
                            DrawerItemData.Item(
                                id = "profile",
                                icon = Icons.Default.Person,
                                label = "Profile"
                            ),
                            DrawerItemData.Divider,
                            DrawerItemData.Header("Settings"),
                            DrawerItemData.Item(
                                id = "settings",
                                icon = Icons.Default.Settings,
                                label = "Settings"
                            ),
                            DrawerItemData.Item(
                                id = "help",
                                icon = Icons.Default.Help,
                                label = "Help & Support"
                            )
                        ),
                        onItemClick = { id ->
                            scope.launch { drawerState.close() }
                            // Handle navigation
                        },
                        selectedItemId = "home"
                    )
                }
            }
        ) {
            // Main content
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                Text("Main Content")
            }
        }
    }
}