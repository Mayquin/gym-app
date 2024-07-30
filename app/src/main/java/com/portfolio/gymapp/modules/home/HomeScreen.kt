package com.portfolio.gymapp.modules.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.portfolio.gymapp.R
import com.portfolio.gymapp.ui.components.ImageBase
import com.portfolio.gymapp.ui.components.TextBase
import com.portfolio.gymapp.ui.theme.boldTextStyle
import com.portfolio.gymapp.ui.theme.commonTextStyle

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(){
    Scaffold (bottomBar = {
        HomeNavigationBar()
    }){ _ ->
        ConstraintLayout(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            val (placeholderImage, placeholderDescription) = createRefs()
            val guideline_h30 = createGuidelineFromTop(fraction = 0.3f)
            ImageBase(imageId = R.drawable.ic_weigth_lifting, modifier = Modifier.constrainAs(placeholderImage){
                top.linkTo(guideline_h30)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.value(128.dp)
                height = Dimension.value(128.dp)
            })
            TextBase(textId = R.string.placeholderHomeDescription, textStyle = boldTextStyle(), modifier = Modifier.constrainAs(placeholderDescription){
                top.linkTo(placeholderImage.bottom, margin = 16.dp)
                width = Dimension.matchParent
            })
        }
    }
}

@Composable
fun HomeNavigationBar(){
    BottomAppBar {
        Row (modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround){
            IconButton(onClick = {
                // Navigate to home
            }) {
                Icon(modifier = Modifier.fillMaxSize(), painter = painterResource(id = R.drawable.ic_home), contentDescription = "homeNavigationIcon")
            }
            IconButton(onClick = {
                // Navigate to calendar
            }) {
                Icon(modifier = Modifier.fillMaxSize(), painter = painterResource(id = R.drawable.ic_calendar), contentDescription = "homeNavigationIcon")
            }
            IconButton(onClick = {
                // Navigate to calendar
            }) {
                Icon(modifier = Modifier.fillMaxSize(), painter = painterResource(id = R.drawable.ic_notifications), contentDescription = "homeNavigationIcon")
            }
        }
    }
}

@Preview
@Composable
fun PreviewHome(){
    HomeScreen()
}

@Preview
@Composable
fun PreviewHomeNavigationBar(){
    HomeNavigationBar()
}