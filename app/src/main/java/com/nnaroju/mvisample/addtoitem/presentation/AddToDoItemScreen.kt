package com.nnaroju.mvisample.addtoitem.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.nnaroju.mvisample.addtoitem.presentation.composables.StandardButton
import com.nnaroju.mvisample.utils.collectInLaunchedEffect

@Composable
fun AddToDoItemScreen(
    navigateBack: (String) -> Unit,
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
    addToDoViewModel: AddToDoViewModel = hiltViewModel(),
) {

    val state by addToDoViewModel.state.collectAsStateWithLifecycle()

    addToDoViewModel.effect.collectInLaunchedEffect { effect ->
        when (effect) {
            AddToDoScreenEffect.NavigateToHome -> navigateToHome()
            is AddToDoScreenEffect.NavigateBack -> navigateBack(effect.errorMessage)
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    onClick = { navigateBack("") }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = stringResource(R.string.add_todo_item),
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 19.sp,
                )

            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = state.titleState.title,
                singleLine = true,
                placeholder = { Text(text = stringResource(id = R.string.enter_title)) },
                isError = state.titleState.errorMessage != null,
                trailingIcon = {
                    if (state.titleState.title.isNotEmpty()) {
                        IconButton(onClick = {
                            addToDoViewModel.sendEvent(
                                AddToDoScreenEvent.OnTitleChanged(
                                    ""
                                )
                            )
                        }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = stringResource(R.string.clear_text)
                            )
                        }
                    }
                },
                onValueChange = {
                    addToDoViewModel.sendEvent(AddToDoScreenEvent.OnTitleChanged(it))
                })
            if (state.titleState.errorMessage != null) {
                Text(
                    text = state.titleState.errorMessage!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = state.description,
                placeholder = { Text(text = stringResource(id = R.string.enter_description)) },
                singleLine = true,
                trailingIcon = {
                    if (state.description.isNotEmpty()) {
                        IconButton(onClick = {
                            addToDoViewModel.sendEvent(
                                AddToDoScreenEvent.OnDescriptionChanged(
                                    ""
                                )
                            )
                        }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = stringResource(R.string.clear_text)
                            )
                        }
                    }
                },
                onValueChange = {
                    addToDoViewModel.sendEvent(AddToDoScreenEvent.OnDescriptionChanged(it))
                })
            StandardButton(
                text = stringResource(id = R.string.add_item),
                isLoading = state.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onClick = {
                    addToDoViewModel.sendEvent(AddToDoScreenEvent.AddToDoItem)
                }
            )
            if (state.errorMessage != null) {
                navigateBack(state.errorMessage!!)
            }
        }
    }
}

