package com.lolakashmir.retailerapp.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lolakashmir.retailerapp.R
import com.lolakashmir.retailerapp.ui.components.AppButton
import com.lolakashmir.retailerapp.ui.components.ButtonVariant
import com.lolakashmir.retailerapp.ui.theme.RetailerAppTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onSignUpClick: () -> Unit = {}
) {
    // State variables can be added here if needed

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome to Retailer App!",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Sign Up Button
        AppButton(
            text = stringResource(R.string.create_an_account),
            onClick = onSignUpClick,
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Login Button
        AppButton(
            text = stringResource(R.string.log_in),
            onClick = { /* Handle login click */ },
            modifier = Modifier.fillMaxWidth(),
            variant = ButtonVariant.Outlined
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    RetailerAppTheme(darkTheme = false, dynamicColor = false) {
        Surface {
            HomeScreen()
        }
    }
}
