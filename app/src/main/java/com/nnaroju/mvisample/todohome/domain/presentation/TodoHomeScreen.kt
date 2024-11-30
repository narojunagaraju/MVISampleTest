package com.nnaroju.mvisample.todohome.domain.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.automirrored.filled.ManageSearch
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nnaroju.mvisample.R
import com.nnaroju.mvisample.core.domian.model.TodoItem
import com.nnaroju.mvisample.utils.collectInLaunchedEffect

@Composable
fun TodoHomeScreen(
    onNavigateToAddNote: () -> Unit,
    modifier: Modifier = Modifier,
    todHomeViewModel: TodHomeViewModel = hiltViewModel()
) {

    val state by todHomeViewModel.state.collectAsStateWithLifecycle()

    todHomeViewModel.effect.collectInLaunchedEffect { effect ->
        when (effect) {
            TodoHomeScreenEffect.NavigateToAddTodoItem -> onNavigateToAddNote
        }
    }

    Scaffold(modifier = modifier, topBar = {
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
            Icon(
                imageVector = Icons.AutoMirrored.Default.ManageSearch,
                contentDescription = stringResource(R.string.search)
            )
        }
    }, floatingActionButton = {
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
            state.todoList.isEmpty() && state.errorMessage == null -> {
                Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.press_the_button_to_add_a_todo)
                    )
                }
            }

            state.errorMessage?.isNotEmpty() == true -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            modifier = Modifier.size(44.dp),
                            imageVector = Icons.Filled.Error,
                            contentDescription = stringResource(R.string.error)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = state.errorMessage
                                ?: stringResource(R.string.unknown_error_occurred_please_try_again),
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.error
                            )
                        )
                    }
                }
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


@Composable
fun ListNoteItem(
    noteItem: TodoItem, modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.primary)
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .padding(vertical = 16.dp)
        ) {
            Text(
                text = noteItem.title,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.SemiBold,
                fontSize = 19.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = noteItem.description ?: "",
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 15.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}