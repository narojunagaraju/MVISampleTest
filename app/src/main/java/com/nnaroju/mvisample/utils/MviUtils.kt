package com.nnaroju.mvisample.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun <T> Flow<T>.collectInLaunchedEffect(function: suspend (value: T) -> Unit) {
    LaunchedEffect(key1 = this) {
        this@collectInLaunchedEffect.collectLatest(function)
    }
}