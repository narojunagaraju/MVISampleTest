package com.nnaroju.mvisample.searchitems.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nnaroju.mvisample.searchitems.presentation.composables.SearchView
import com.nnaroju.mvisample.todohome.presentation.composables.ListNoteItem

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun TodoSearchScreen(
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel = hiltViewModel()
) {

    val state by searchViewModel.state.collectAsStateWithLifecycle()

    Column(modifier = modifier) {
        SearchView(
            query = state.searchQuery,
            onQueryChange = {
                searchViewModel.sendEvent(SearchScreenEvent.SearchItems(it))
            },
            onClearClick = {
                searchViewModel.sendEvent(SearchScreenEvent.SearchItems(""))
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        when {
            state.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            state.todoList.isEmpty() -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Search your favorite items here...")
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 12.dp)
                ) {
                    items(
                        count = state.todoList.size
                    ) { index ->
                        ListNoteItem(
                            modifier = Modifier.fillMaxSize(),
                            noteItem = state.todoList[index]
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }

    }
}