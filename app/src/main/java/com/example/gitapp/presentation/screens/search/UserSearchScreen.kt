package com.example.gitapp.presentation

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.gitapp.R
import com.example.gitapp.domain.models.User
import com.example.gitapp.presentation.components.MainAppBar
import com.example.gitapp.presentation.components.SearchWidgetState
import com.example.gitapp.presentation.screens.search.UserViewModel
import com.example.gitapp.ui.theme.MyDarkGrayColor
import com.example.gitapp.ui.theme.MyGrayColor
import com.example.gitapp.util.gifLoader
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalComposeUiApi::class)
@Destination(start = true)
@Composable
fun UserSearchScreen(navigator: DestinationsNavigator, viewModel: UserViewModel = hiltViewModel()){

    val state by viewModel.state
    val searchWidgetState by viewModel.searchWidgetState
    val searchString by viewModel.searchString
    val scaffoldState = rememberScaffoldState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current


    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            MainAppBar(
                searchWidgetState = searchWidgetState,
                searchStringState = searchString,
                onTextChange = {
                    viewModel.setSearchString(it)
                },
                onCloseClicked = {
                    viewModel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED)
                },
                onSearchClicked = {
                    viewModel.getUsers(it.trim())
                    keyboardController?.hide()
                },
                onSearchTriggered = {
                    viewModel.updateSearchWidgetState(newValue = SearchWidgetState.OPENED)
                }
            )
        }
    ){
        Box(Modifier.fillMaxSize()) {
            if (state.user != null && !state.isLoading) {

                LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp)) {
                    item {
                        if (state.user != null) {
                            UserProfileHeader(
                                user = state.user,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )

                            Spacer(modifier = Modifier.height(8.dp))
                            Divider(thickness = 0.7.dp, color = MyGrayColor)

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "${state.user?.followers} Repositories",
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.Black
                                    )
                                )
                                IconButton(
                                    onClick = {
                                        navigator.navigate(
                                            UserRepositoriesScreenDestination(
                                                username = viewModel.searchString.value
                                            )
                                        )

                                    },
                                ) {
                                    Icon(
                                        Icons.Default.ChevronRight,
                                        contentDescription = "To Repositories Screen"
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            Divider(thickness = 0.7.dp, color = MyGrayColor)

                        }
                    }

                    item {
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = "Followers",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.Black
                            )
                        )
                    }



                    item {
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = "Following",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.Black
                            )
                        )
                    }


                }

            } else if (state.user == null && !state.isLoading) {
                EmptyStateGifImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(600.dp)
                        .align(Alignment.Center),
                    context = context,
                )
            }
            if (state.isLoading) {
                LoadingGif(
                    context = context,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }


}


@Composable
fun UserProfileHeader(
    user: User?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(
                            data = user?.avatar_url
                                ?: "https://avatars.githubusercontent.com/u/5934628?v=4"
                        )
                        .apply(block = fun ImageRequest.Builder.() {
                            crossfade(true)
                        }).build()
                ),
                modifier = Modifier
                    .clip(CircleShape)
                    .size(100.dp),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(verticalArrangement = Arrangement.Center) {
                Text(
                    text = user?.name ?: user?.login ?: "Null",
                    style = TextStyle(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = user?.login ?: "Null",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Light
                    )
                )
            }
        }

        if (user?.bio != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = 1.dp,
                shape = RoundedCornerShape(
                    8.dp
                ),
                backgroundColor = MyGrayColor,
                border = BorderStroke(
                    0.3.dp,
                    Color.Black
                ),
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    text = user.bio,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.PeopleOutline,
                contentDescription = "Followers Count",
                tint = MyDarkGrayColor,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "${user?.followers} followers")
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .size(5.dp)
                    .clip(CircleShape)
                    .background(Color.Black)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "${user?.following} following")

        }

        if (user?.company != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Icon(
                    imageVector = Icons.Outlined.BusinessCenter, contentDescription = "Company",
                    tint = MyDarkGrayColor,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${user.company}",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
            }
        }

        if (user?.location != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Icon(
                    imageVector = Icons.Outlined.LocationOn, contentDescription = "Location",
                    tint = MyDarkGrayColor,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${user.location}",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
            }
        }

        if (user?.email != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Icon(
                    imageVector = Icons.Outlined.Email, contentDescription = "Email",
                    tint = MyDarkGrayColor,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${user.email}",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
            }
        }

        if (user?.blog != null && user.blog != "") {
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Icon(
                    imageVector = Icons.Outlined.Link, contentDescription = "Blog",
                    tint = MyDarkGrayColor,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${user.blog}",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
            }
        }

        if (user?.twitter_username != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Icon(
                    painter = painterResource(id = R.drawable.ic_twitter),
                    contentDescription = "Twitter",
                    tint = MyDarkGrayColor,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "@${user.twitter_username}",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }
    }
}


@Composable
fun EmptyStateGifImage(
    modifier: Modifier = Modifier,
    context: Context
) {

    Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

        Image(

            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(context).data(data = R.drawable.search).apply(block = {
                    size(Size.ORIGINAL)
                }).build(), imageLoader = context.gifLoader()
            ),
            contentDescription = "Empty State Gif",
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
        )
        Text(
            text = "Search for a user to see their profile",
            style = TextStyle(
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}

@Composable
fun LoadingGif(
    context: Context,
    modifier: Modifier = Modifier
) {
    Image(
        painter = rememberAsyncImagePainter(
            ImageRequest.Builder(context).data(data = R.drawable.octocat).apply(block = {
                size(Size.ORIGINAL)
            }).build(), imageLoader = context.gifLoader()
        ),
        contentDescription = null,
        modifier = modifier
            .fillMaxWidth()
            .height(90.dp)
    )
}