package com.mu.tote2026.presentation.screen.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mu.tote2026.presentation.components.LogonButton
import com.mu.tote2026.R

/*@Preview(
    name = "Light",
    showBackground = true
)
@Preview(
    name = "Dark",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun PreviewAuth() {
    Tote2026Theme {
        AuthScreen(
            onSignUpClick = { *//*TODO*//* },
            onSignInClick = { *//*TODO*//* }
        )
    }
}*/

@Composable
fun AuthScreen(
    toSignUp: () -> Unit,
    toSignIn: () -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        Column(
            modifier = Modifier.padding(vertical = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LogonButton(
                R.string.sign_up,
                onClick = {
                    toSignUp()
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                modifier = Modifier.size(248.dp),
                painter = painterResource(id = R.drawable.mascot),
                contentDescription = "mascot"
            )
            Spacer(modifier = Modifier.height(16.dp))
            LogonButton(
                R.string.sign_in,
                onClick = {
                    toSignIn()
                }
            )
        }
    }
}