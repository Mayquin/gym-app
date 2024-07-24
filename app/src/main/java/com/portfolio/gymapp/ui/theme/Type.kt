package com.portfolio.gymapp.ui.theme

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp)
)

fun commonTitleTextStyle(fontSize: Int = 22, color: Color = Blue) = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    fontSize = fontSize.sp,
    lineHeight = (fontSize + 4).sp,
    letterSpacing = 0.5.sp,
    color = color)

fun commonTextStyle(fontSize: Int = 16, color: Color = Color.Black) = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    fontSize = fontSize.sp,
    lineHeight = (fontSize + 4).sp,
    letterSpacing = 0.5.sp,
    color = color)

fun commonBlueTextStyle(fontSize: Int = 16) = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    fontSize = fontSize.sp,
    lineHeight = (fontSize + 4).sp,
    letterSpacing = 0.5.sp,
    color = Blue)

fun boldTextStyle(fontSize: Int = 16, color: Color = Color.Black) = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Bold,
    fontSize = fontSize.sp,
    lineHeight = (fontSize + 4).sp,
    letterSpacing = 0.5.sp,
    color = color)

val ButtonTextStyle = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
    lineHeight = 16.sp,
    letterSpacing = 1.sp,
    color = Color.White)

@Composable
fun customButtonColor(
) = ButtonDefaults.buttonColors(
    containerColor = Color.White,
    contentColor = Color.Black,
    disabledContainerColor = Color.LightGray,
    disabledContentColor = Color.White,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun customTextFieldColor(
    disabledTextColor: Color = Color.White,
    backgroundColor: Color = Color.White,
    cursorColor: Color = Color.White,
    errorCursorColor: Color = Color.White,
    focusedLabelColor: Color = Color.White,
    focusedPlaceholderColor: Color = Color.Gray,
    unfocusedPlaceholderColor: Color = Color.Gray) = TextFieldDefaults.textFieldColors(
    disabledTextColor = disabledTextColor,
    //disabledContainerColor = backgroundColor,
    errorCursorColor = errorCursorColor,
    //focusedContainerColor = focusedLabelColor,
    //unfocusedContainerColor = focusedLabelColor,
    //focusedPlaceholderColor = focusedPlaceholderColor,
    //unfocusedPlaceholderColor = unfocusedPlaceholderColor,
    focusedLabelColor = focusedLabelColor,
    cursorColor = cursorColor
/*
disabledTextColor = disabledTextColor,
containerColor = backgroundColor,
cursorColor = cursorColor,
errorCursorColor = errorCursorColor,
focusedLabelColor = focusedLabelColor
 */
)
