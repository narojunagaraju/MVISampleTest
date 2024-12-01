package com.nnaroju.mvisample.todohome.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ManageSearch
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nnaroju.mvisample.R
import com.nnaroju.mvisample.todohome.presentation.composables.ListNoteItem
import com.nnaroju.mvisample.todohome.presentation.composables.ShowErrorDialog
import com.nnaroju.mvisample.utils.collectInLaunchedEffect

@Composable
fun TodoHomeScreen(
    onNavigateToAddNote: () -> Unit,
    onNavigateToSearch: () -> Unit,
    modifier: Modifier = Modifier,
    todHomeViewModel: TodHomeViewModel = hiltViewModel(),
    refreshContent: Boolean = false,
    errorMessage: String = ""
) {

    val state by todHomeViewModel.state.collectAsStateWithLifecycle()

    todHomeViewModel.effect.collectInLaunchedEffect { effect ->
        when (effect) {
            TodoHomeScreenEffect.NavigateToAddTodoItem -> onNavigateToAddNote()
            TodoHomeScreenEffect.NavigateToSearchItem -> onNavigateToSearch()
        }
    }

    if (refreshContent) {
        LaunchedEffect(key1 = Unit) {
            todHomeViewModel.sendEvent(TodoHomeScreenEvent.FetchAllTodoItems)
        }
    }

    if (errorMessage.isNotEmpty()) {
        LaunchedEffect(key1 = Unit) {
            todHomeViewModel.sendEvent(TodoHomeScreenEvent.SetErrorMessage(errorMessage))
        }
    }
    Scaffold(modifier = modifier,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.todo_list_app),
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 19.sp,
                )
                IconButton(
                    onClick = { onNavigateToSearch() }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ManageSearch,
                        contentDescription = stringResource(R.string.search)
                    )
                }

            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAddNote
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add)
                )
            }
        }) { paddingValues ->
        when {
            state.todoList.isEmpty() && state.errorMessage.isNullOrEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.press_the_button_to_add_a_todo)
                    )
                }
            }

            state.errorMessage?.isNotEmpty() == true -> {
                ShowErrorDialog(
                    errorMessage = state.errorMessage ?: "Unknown error occurred..",
                    onSendEvent = { todHomeViewModel.sendEvent(it) }
                )
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = paddingValues.calculateTopPadding()),
                    contentPadding = PaddingValues(vertical = 12.dp)
                ) {
                    items(
                        count = state.todoList.size
                    ) { index ->
                        ListNoteItem(
                            modifier = Modifier.fillMaxSize(), noteItem = state.todoList[index]
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}
