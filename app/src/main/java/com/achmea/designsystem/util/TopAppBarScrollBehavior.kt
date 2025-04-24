package com.achmea.designsystem.util

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopAppBarScrollBehavior.UpdateScrollState(lazyListState: LazyListState) {
    val scrollOffset by remember {
        derivedStateOf {
            val offset = lazyListState.firstVisibleItemScrollOffset.toFloat()
            val index = lazyListState.firstVisibleItemIndex * 100f
            index + offset
        }
    }
    LaunchedEffect(scrollOffset) {
        state.contentOffset = -scrollOffset
    }
}