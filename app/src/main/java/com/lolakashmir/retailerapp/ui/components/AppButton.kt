package com.lolakashmir.retailerapp.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * A customizable button component with multiple variants and states.
 *
 * @param text The text to display on the button
 * @param onClick Callback when the button is clicked
 * @param modifier Modifier to be applied to the button
 * @param variant The visual style variant of the button
 * @param isLoading Whether the button is in a loading state
 * @param enabled Controls the enabled state of the button
 * @param contentPadding The spacing values to apply internally between the container and the content
 * @param content Optional custom content to display on the button
 */
@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: ButtonVariant = ButtonVariant.Primary,
    isLoading: Boolean = false,
    enabled: Boolean = true,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable (RowScope.() -> Unit)? = null
) {
    // Animation for loading state
    val buttonElevation by animateDpAsState(if (isLoading) 0.dp else 4.dp)
    val buttonShape = RoundedCornerShape(8.dp)

    // Define color quads for each button variant
    val quad = when (variant) {
        ButtonVariant.Primary -> {
            val container = MaterialTheme.colorScheme.primary
            val content = MaterialTheme.colorScheme.onPrimary
            val disabledContainer = MaterialTheme.colorScheme.surfaceVariant
            val disabledContent = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
            Quad(container, content, disabledContainer, disabledContent)
        }
        ButtonVariant.Secondary -> {
            val container = MaterialTheme.colorScheme.secondaryContainer
            val content = MaterialTheme.colorScheme.onSecondaryContainer
            val disabledContainer = MaterialTheme.colorScheme.surfaceVariant
            val disabledContent = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
            Quad(container, content, disabledContainer, disabledContent)
        }
        ButtonVariant.Outlined -> {
            val container = Color.Transparent
            val content = MaterialTheme.colorScheme.primary
            val disabledContainer = container.copy(alpha = 0f)
            val disabledContent = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
            Quad(container, content, disabledContainer, disabledContent)
        }
    }

    // Animate color changes
    val animatedContainerColor by animateColorAsState(
        if (enabled) quad.container else quad.disabledContainer,
        label = "buttonContainerColor"
    )
    val animatedContentColor by animateColorAsState(
        if (enabled) quad.content else quad.disabledContent,
        label = "buttonContentColor"
    )

    Button(
        onClick = { if (!isLoading) onClick() },
        modifier = modifier,
        enabled = enabled && !isLoading,
        shape = buttonShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = animatedContainerColor,
            contentColor = animatedContentColor,
            disabledContainerColor = quad.disabledContainer,
            disabledContentColor = quad.disabledContent
        ),
        contentPadding = contentPadding,
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = buttonElevation,
            pressedElevation = 2.dp,
            disabledElevation = 0.dp
        ),
        border = if (variant == ButtonVariant.Outlined) {
            BorderStroke(
                width = 2.dp,
                color = if (enabled) quad.content else quad.disabledContent
            )
        } else {
            null
        }
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = quad.content,
                strokeWidth = 2.dp,
                modifier = Modifier.padding(end = 8.dp)
            )
        }

        if (content != null) {
            content()
        } else {
            Text(
                text = if (isLoading) "Loading..." else text,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

/**
 * Represents the visual style variant of the button.
 */
enum class ButtonVariant {
    Primary,
    Secondary,
    Outlined
}

// Helper data class to hold color values
private data class Quad<T>(
    val container: T,
    val content: T,
    val disabledContainer: T,
    val disabledContent: T
)
