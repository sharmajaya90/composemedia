package com.service.composemedia.ui.options

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import com.service.composemedia.domain.model.UserOptions
import com.service.composemedia.ui.navigation.Destination

@Composable
fun FullScreenDialog(onDismiss: () -> Unit,
                     navController: NavHostController) {
    val userOptions = remember {
        mutableListOf<UserOptions>()
    }
    LaunchedEffect(Unit) {
        userOptions.add(UserOptions(id="1", title = "User Options", actionType = "options", imageUrl = ""))
        userOptions.add(UserOptions(id="2", title = "Favorite", actionType = "favorite", imageUrl = ""))
        userOptions.add(UserOptions(id="3", title = "Settings", actionType = "settings", imageUrl = ""))
    }
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f)
                .background(Color.White) // Semi-transparent background
            ,

        ) {
            IconButton(onClick = { onDismiss.apply {
                false
            } }, modifier = Modifier.align(Alignment.TopStart)) {
                Icon(imageVector = Icons.Filled.Close, contentDescription = "Close")
            }

            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                items(userOptions){
                    OptionsItems(onClick = {
                        var userIntent = ""
                        when(it.actionType){
                            "options"->{
                                userIntent = Destination.adSongScreen
                            }
                            "settings"->{
                                userIntent = Destination.settingsScreen
                            }
                            "favorite"->{
                                userIntent = Destination.favoriteScreen
                            }
                        }
                        navController.navigate(userIntent)
                    },userOption = it)
                }
            }
        }
    }
}

@Composable
fun OptionButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(horizontal = 16.dp)
    ) {
        Text(text = text)
    }
}


@Composable
fun OptionsItems(
    onClick: () -> Unit,
    userOption: UserOptions?
) {
    ConstraintLayout(
        modifier = Modifier
            .clickable {
                onClick
            }
            .fillMaxWidth().background(Color.White)
    ) {
        val (
            songTitle, image
        ) = createRefs()

        Icon(
            imageVector = Icons.Filled.KeyboardArrowRight,
            contentDescription = null,
            modifier = Modifier
                .size(30.dp)
                .clip(MaterialTheme.shapes.medium)
                .constrainAs(image) {
                    end.linkTo(parent.end, 16.dp)
                    top.linkTo(parent.top, 16.dp)
                    bottom.linkTo(parent.bottom, 16.dp)
                }
        )

        Text(
            text = userOption?.title?:"",
            maxLines = 1,
            style = MaterialTheme.typography.subtitle1,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.constrainAs(songTitle) {
                linkTo(
                    start = parent.start,
                    end = image.start,
                    startMargin = 24.dp,
                    endMargin = 16.dp,
                    bias = 0f
                )
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start, 16.dp)
                width = Dimension.preferredWrapContent
            }
        )
    }
}