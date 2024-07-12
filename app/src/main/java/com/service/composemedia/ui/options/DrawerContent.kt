package com.service.composemedia.ui.options

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import com.service.composemedia.domain.model.UserOptions
import com.service.composemedia.other.MusicControllerUiState
import com.service.composemedia.ui.navigation.Destination
import com.service.composemedia.ui.songscreen.SongEvent
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@Composable
fun DrawerContent(
    onEvent: (SongEvent) -> Unit,
    musicControllerUiState: MusicControllerUiState,
    onNavigateUp: () -> Unit,
    navController: NavHostController
){
    val state = rememberCollapsingToolbarScaffoldState()
    var userOptions = remember {
        mutableListOf<UserOptions>()
    }
    LaunchedEffect(Unit) {
        userOptions.add(UserOptions(id="1", title = "User Options", actionType = "options", imageUrl = ""))
        userOptions.add(UserOptions(id="2", title = "Favorite", actionType = "favorite", imageUrl = ""))
        userOptions.add(UserOptions(id="3", title = "Settings", actionType = "settings", imageUrl = ""))
    }
    CollapsingToolbarScaffold(
        modifier = Modifier
            .fillMaxSize(),
        state = state,
        scrollStrategy = ScrollStrategy.EnterAlways,
        toolbar = {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(5.dp)
            ) {

                IconButton(onClick = { navController.popBackStack() }, modifier = Modifier.align(Alignment.CenterVertically)) {
                    Icon(imageVector = Icons.Filled.Close, contentDescription = "Back")
                }
                Text(
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    text = "Media Options",
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp, 5.dp, 30.dp, 5.dp),
                    textAlign = TextAlign.Center,
                )
            }
        }
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight(), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {
            LazyColumn(modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()) {
                items(userOptions.size){index ->
                    OptionsItem(
                        onClick = {
                            var userIntent = ""
                            when(userOptions.getOrNull(index)?.actionType){
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
                        },
                        userOption = userOptions.getOrNull(index)
                    )
                }
            }
        }
    }

}


@Composable
fun DrawerItem(userOption: UserOptions?, icon: ImageVector, label: String, navController: NavHostController) {
    Column {
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp, 0.dp, 0.dp, 0.dp)
                .clickable {
                    navController.navigate(Destination.adSongScreen)
                }
        ){
            Text(text = userOption?.title?:"", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            IconButton(onClick = { navController.popBackStack() }, modifier = Modifier
                .align(Alignment.CenterVertically)
                .fillMaxWidth()) {
                Icon(imageVector = Icons.Filled.KeyboardArrowRight, contentDescription = "Back")
            }
        }
    }
}

@Composable
fun OptionsItem(
    onClick: () -> Unit,
    userOption: UserOptions?
) {
    ConstraintLayout(
        modifier = Modifier
            .clickable {
                onClick()
            }
            .fillMaxWidth()
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