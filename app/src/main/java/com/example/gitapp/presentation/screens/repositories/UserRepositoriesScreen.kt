package com.example.gitapp.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gitapp.domain.models.Repo
import com.example.gitapp.presentation.screens.repositories.UserRepositoriesViewModel
import com.example.gitapp.ui.theme.*
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun UserRepositoriesScreen(username: String,
    viewModel: UserRepositoriesViewModel = hiltViewModel()
){
    viewModel.setUserName(username)
    viewModel.getUserRepositories(viewModel.username.value)

    val state by viewModel.state
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                title = {
                    Text(text = "Repositories", color = Color.Black)
                },
                navigationIcon = {
                    IconButton(onClick = {

                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back to Home",
                            tint = Color.Black
                        )
                    }
                },
            )
        }
    ){
        Box(Modifier.fillMaxSize()) {
            LazyColumn {
                if (state.repos.isNotEmpty()) {
                    items(state.repos) { repo ->
                        RepoItem(repo = repo)
                    }
                }
            }

            if (state.isLoading) {
                LoadingGif(
                    context = context,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            if (state.error != null) {
                Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
            }
        }
    }

}


@Composable
fun RepoItem(repo: Repo) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 5.dp),
        elevation = 0.dp
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            repo.name?.let {
                Text(
                    text = it,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            repo.description?.let {
                Text(
                    text = it,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.Black
                    )
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (repo.language != null) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.height(8.dp))

                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(
                                    when (repo.language) {
                                        "Kotlin" -> {
                                            KotlinColor
                                        }
                                        "Java" -> {
                                            JavaColor
                                        }
                                        "Python" -> {
                                            PythonColor
                                        }
                                        "Javascript" -> {
                                            JavaScriptColor
                                        }
                                        "Dart" -> {
                                            DartColor
                                        }
                                        else -> {
                                            OtherColor
                                        }
                                    }
                                )
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = repo.language.toString(),
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Light,
                                color = Color.Black
                            )
                        )

                    }
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(10.dp))

                    Icon(
                        Icons.Outlined.Star,
                        contentDescription = "Repo Stars",
                        tint = MyDarkGrayColor,
                        modifier = Modifier.size(14.dp),
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = repo.created_at .toString(),
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.Black
                        )
                    )

                }


                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(10.dp))
                    Icon(
                        modifier = Modifier.size(14.dp),
                        painter = painterResource(com.example.gitapp.R.drawable.ic_issues),
                        contentDescription = "Repo Issues",
                        tint = MyDarkGrayColor
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = repo.clone_url.toString(),
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.Black
                        )
                    )

                }

                if (repo.forks_count != null) {
                    Spacer(modifier = Modifier.width(10.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.size(14.dp),
                            painter = painterResource(com.example.gitapp.R.drawable.ic_fork),
                            contentDescription = "Repo Forks",
                            tint = MyDarkGrayColor
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = repo.forks_count.toString(),
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Light,
                                color = Color.Black
                            )
                        )
                    }
                }
            }
        }
        Divider(thickness = 0.8.dp, color = MyGrayColor)
    }
}