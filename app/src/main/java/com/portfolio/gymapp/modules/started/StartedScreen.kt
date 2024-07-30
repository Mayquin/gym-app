package com.portfolio.gymapp.modules.started

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.portfolio.gymapp.R
import com.portfolio.gymapp.ui.components.RoundedButton
import com.portfolio.gymapp.ui.components.TextBase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun StartedScreen() {
    Scaffold { _ ->
        ConstraintLayout (Modifier.fillMaxSize().padding(16.dp)) {
            val midGuideline = createGuidelineFromTop(0.5f)
            val (title, registerButton, loginButton) = createRefs()
            TextBase(textId = R.string.app_name, modifier = Modifier.constrainAs(title){
                top.linkTo(midGuideline, margin = -56.dp)
                width = Dimension.matchParent
            })
            RoundedButton(R.string.register_button, modifier = Modifier.constrainAs(registerButton){
                bottom.linkTo(loginButton.top, margin = 56.dp)
                width = Dimension.matchParent
            }) {
    
            }
            RoundedButton(R.string.login_button, modifier = Modifier.constrainAs(loginButton){
                bottom.linkTo(parent.bottom)
                width = Dimension.matchParent
            }) {
    
            }
        }
    }
}

@Preview
@Composable
fun StartedPreview(){
    StartedScreen()
}
