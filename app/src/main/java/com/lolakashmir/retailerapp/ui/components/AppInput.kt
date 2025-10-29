package com.lolakashmir.retailerapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

/**
 * A customizable input field component with multiple variants and states.
 *
 * @param value The input text to be shown in the text field
 * @param onValueChange The callback that is triggered when the input service updates the text
 * @param label The optional label to be displayed inside the text field container
 * @param placeholder The optional placeholder text to be displayed when the text field is empty
 * @param modifier Modifier to be applied to the text field
 * @param variant The visual style variant of the input field
 * @param isError Indicates if the input is in an error state
 * @param errorMessage The error message to be displayed below the input field when isError is true
 * @param leadingIcon The optional leading icon to be displayed at the start of the text field
 * @param trailingIcon The optional trailing icon to be displayed at the end of the text field
 * @param keyboardType The keyboard type to be used for this text field
 * @param imeAction The IME (Input Method Editor) action to be used for this text field
 * @param keyboardActions The keyboard actions to be called when the input service emits an IME action
 * @param singleLine When true, this text field becomes a single horizontally scrolling text field
 * @param maxLines The maximum height in terms of maximum number of visible lines
 * @param isPasswordField When true, the input will be treated as a password field
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppInput(
    value: String,
    onValueChange: (String) -> Unit,
    label: String? = null,
    placeholder: String? = null,
    modifier: Modifier = Modifier,
    variant: InputVariant = InputVariant.Outlined,
    isError: Boolean = false,
    errorMessage: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    keyboardActions: KeyboardActions = KeyboardActions(),
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    isPasswordField: Boolean = false
) {
    var passwordVisible by remember { mutableStateOf(false) }
    
    val backgroundColor = when (variant) {
        InputVariant.Filled -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        InputVariant.Outlined -> Color.Transparent
    }
    
    val textColor = MaterialTheme.colorScheme.onSurface
    val placeholderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
    val disabledColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
    val errorColor = MaterialTheme.colorScheme.error
    val focusedColor = MaterialTheme.colorScheme.primary
    val unfocusedColor = MaterialTheme.colorScheme.outline
    
    val containerModifier = when (variant) {
        InputVariant.Filled -> Modifier
            .background(backgroundColor, RoundedCornerShape(8.dp))
            .then(if (isError) Modifier.border(1.dp, errorColor, RoundedCornerShape(8.dp)) else Modifier)
        InputVariant.Outlined -> Modifier
            .border(
                width = 1.dp,
                color = when {
                    isError -> errorColor
                    else -> unfocusedColor
                },
                shape = RoundedCornerShape(8.dp)
            )
    }
    
    Column(
        modifier = modifier
    ) {
        Box(
            modifier = containerModifier
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp),
                label = label?.let { 
                    { Text(text = it, style = MaterialTheme.typography.bodyMedium) } 
                },
                placeholder = placeholder?.let { 
                    { Text(text = it, style = MaterialTheme.typography.bodyMedium.copy(color = placeholderColor)) } 
                },
                singleLine = singleLine,
                maxLines = maxLines,
                isError = isError,
                leadingIcon = leadingIcon,
                trailingIcon = {
                    if (isPasswordField) {
                        val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                        val description = if (passwordVisible) "Hide password" else "Show password"
                        
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = image, description)
                        }
                    } else {
                        trailingIcon?.invoke()
                    }
                },
                visualTransformation = if (isPasswordField && !passwordVisible) {
                    PasswordVisualTransformation()
                } else {
                    VisualTransformation.None
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = keyboardType,
                    imeAction = imeAction
                ),
                keyboardActions = keyboardActions,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = textColor,
                    unfocusedTextColor = textColor,
                    disabledTextColor = disabledColor,
                    errorTextColor = errorColor,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent,
                    cursorColor = focusedColor,
                    errorCursorColor = errorColor,
                    focusedLabelColor = focusedColor,
                    unfocusedLabelColor = unfocusedColor,
                    errorLabelColor = errorColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                    focusedLeadingIconColor = focusedColor,
                    unfocusedLeadingIconColor = unfocusedColor,
                    errorLeadingIconColor = errorColor,
                    focusedTrailingIconColor = focusedColor,
                    unfocusedTrailingIconColor = unfocusedColor,
                    errorTrailingIconColor = errorColor,
                    focusedPlaceholderColor = placeholderColor,
                    unfocusedPlaceholderColor = placeholderColor,
                    disabledPlaceholderColor = placeholderColor,
                    errorPlaceholderColor = errorColor.copy(alpha = 0.6f)
                )
            )
        }
        
        if (isError && !errorMessage.isNullOrEmpty()) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = errorMessage,
                color = errorColor,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 12.dp)
            )
        }
    }
}

/**
 * Represents the visual style variant of the input field.
 */
enum class InputVariant {
    /**
     * Filled input field with a background color
     */
    Filled,
    
    /**
     * Outlined input field with a border
     */
    Outlined
}
