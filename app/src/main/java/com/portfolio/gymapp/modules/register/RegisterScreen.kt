package com.portfolio.gymapp.modules.register

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.portfolio.gymapp.R
import com.portfolio.gymapp.ui.components.ConfirmPasswordTextField
import com.portfolio.gymapp.ui.components.PasswordTextField
import com.portfolio.gymapp.ui.components.RoundedButton
import com.portfolio.gymapp.ui.components.TextBase
import com.portfolio.gymapp.ui.components.TextFieldBase

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RegisterScreen(){
    val passwordText by remember { mutableStateOf("") }
    val confirmPasswordText by remember { mutableStateOf("") }
    Scaffold { _ ->
        ConstraintLayout (modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)){
            val (username, email, password, confirmPassword, validateButton) = createRefs()
            Column (modifier = Modifier.constrainAs(username){
                top.linkTo(parent.top)
            }){
                TextBase(textId = R.string.username)
                TextFieldBase(initialValue = "", hintTextId = R.string.username_hint) {

                }
            }
            Column(modifier = Modifier.constrainAs(email){
                top.linkTo(username.bottom, margin = 10.dp)
            }) {
                TextBase(textId = R.string.email)
                Spacer(modifier = Modifier.height(8.dp))
                TextFieldBase(initialValue = "", hintTextId = R.string.email_hint) {
    
                }
            }
            Column (modifier = Modifier.constrainAs(password){
                top.linkTo(email.bottom, margin = 10.dp)
            }){
                TextBase(textId = R.string.password)
                Spacer(modifier = Modifier.height(8.dp))
                PasswordTextField(text = passwordText, labelText = stringResource(id = R.string.password_hint)) {

                }
            }
            ConfirmPasswordTextField(modifier = Modifier.constrainAs(confirmPassword){
                top.linkTo(password.bottom)
            }, text = confirmPasswordText, confirmText = passwordText) {

            }

            RoundedButton(modifier = Modifier.constrainAs(validateButton){
                bottom.linkTo(parent.bottom)
                width = Dimension.matchParent
            }, textId = R.string.validate_button) {
                
            }
        }
    }
}

@Preview
@Composable
fun RegisterPreview(){
    RegisterScreen()
}