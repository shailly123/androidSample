package com.example.takehome.ui.screens

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn

import androidx.compose.foundation.lazy.items

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.takehome.data.remote.model.GithubUser
import com.example.takehome.data.remote.model.GithubUserRepos
import com.example.takehome.ui.theme.TakeHomeTheme
import com.example.takehome.ui.viewmodels.GithubUserViewModel
import kotlinx.coroutines.delay


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DashboardContent(navController: NavHostController, githubUserViewModel: GithubUserViewModel) {

    val uiState by githubUserViewModel.uiState.collectAsState()

    TakeHomeTheme() {
        Surface(modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)) {

            Column(modifier = Modifier.padding(5.dp)) {
                CustomSearchBar(onSearchClicked = { it -> githubUserViewModel.fetchGithubUser(it) })
                LoadingContent(uiState.isLoading)
                ProfileDetailsSection(uiState.githubUser)
                RepositoriesListSection(uiState.userRepos) { itemId ->
                    navController.navigate(
                        "details/$itemId"
                    )
                }
                ErrorContent(uiState.error)
            }
        }
    }
}

@Composable
fun LoadingContent(loading: Boolean) {
    if(loading) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {
            Image(imageVector = Icons.Default.AccountCircle, contentDescription = "")
            Text(text = "Loading Details..")
        }
    }
}

@Composable
fun ErrorContent(errorMessage: String?) {
    if(errorMessage.isNullOrEmpty().not()) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
                 horizontalAlignment = Alignment.CenterHorizontally,
                 verticalArrangement = Arrangement.Center) {
            Image(imageVector = Icons.Default.Warning, contentDescription = "")
            Text("No user found $errorMessage")
        }
    }
}
@Composable
fun RepositoriesListSection(userRepos: List<GithubUserRepos>, onCardClicked:(Long) -> Unit) {
    LazyColumn {
        items(userRepos){
            RepositoryCard(it,onCardClicked)
        }
    }
}
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RepositoryCard(userRepo: GithubUserRepos,onCardClicked:(Long) -> Unit) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = MaterialTheme.shapes.medium,
        elevation = 5.dp,
        backgroundColor = MaterialTheme.colors.surface,
        onClick = { onCardClicked(userRepo.id)}
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(8.dp)) {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(text = userRepo.name, fontWeight = FontWeight.Bold )
                Row(verticalAlignment = Alignment.CenterVertically,) {
                    Text(text = userRepo.forks.toString(),Modifier.padding(2.dp))
                    if(userRepo.forks > 6000){
                        Icon(
                            imageVector = Icons.Default.Star,
                            tint = Color.Red,
                            contentDescription = "Star Badge",
                            modifier = Modifier
                                .size(24.dp)
                                .padding(2.dp),

                        )
                    }
                }
            }
            Text(text = userRepo.description.orEmpty(),modifier = Modifier.padding(4.dp))
        }

    }
}

@Composable
fun CustomSearchBar( onSearchClicked: (String) -> Unit) {
    UsernameTextField(onSearchClicked)
}

@Composable
fun UsernameTextField( onSearchClicked: (String) -> Unit) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(2.dp)) {
        var text by remember { mutableStateOf("") }
        val keyboardController = LocalSoftwareKeyboardController.current
        var isFocused by remember { mutableStateOf(false) }
        val focusManager = LocalFocusManager.current

        TextField(
            value = text,
            modifier = Modifier
                .padding(horizontal = 2.dp, vertical = 4.dp)
                .weight(2f)
                .onFocusEvent { isFocused = it.isFocused },
            onValueChange = {
                text = it
            },
            singleLine = true,
            label = { Text(text = "Enter a github user id") },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    // Hide the keyboard when "Done" is clicked
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }
            ),
            textStyle = TextStyle(fontSize = 15.sp),
        )
        Button(onClick = { onSearchClicked(text) }, modifier = Modifier
            .wrapContentWidth()
            .weight(1f) ) {
            Text(
                text = "SEARCH",
                Modifier
                    .align(Alignment.CenterVertically)
            )
        }
    }
    }



@Composable
fun ProfileDetailsSection(user : GithubUser?){
    Column(
        Modifier
            .fillMaxWidth()
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        var imageVisible by remember { mutableStateOf(false) }
        LaunchedEffect(Unit) {
            delay(1000) // Delay for 1 second (1000 milliseconds)
            imageVisible = true
        }
        val imageAlpha: Float by animateFloatAsState(
            targetValue = if (imageVisible) 1f else 0f,
            animationSpec = tween(
                durationMillis = 1000,
                easing = LinearEasing
            )
        )
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(user?.avatarUrl)
                .crossfade(true)
                .build(),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(CircleShape)
                .size(120.dp, 120.dp),
            alpha = imageAlpha,
        )

        Text(text = user?.userName.orEmpty())
    }
}

