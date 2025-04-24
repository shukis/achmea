package com.achmea.presentation.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.achmea.core.Route
import com.achmea.designsystem.components.SearchTextField
import com.achmea.designsystem.util.UpdateScrollState
import com.achmea.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployersListScreen(
    navController: NavController,
    viewModel: EmployersListViewModel,
    paddingValues: PaddingValues
) {
    val query = viewModel.query.collectAsStateWithLifecycle().value.orEmpty()
    val employers = viewModel.employers.collectAsStateWithLifecycle().value
    val behavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val lazyListState = rememberLazyListState()
    behavior.UpdateScrollState(lazyListState)
    Column(
        modifier = Modifier.padding(
            WindowInsets.safeDrawing
                .only(WindowInsetsSides.Horizontal)
                .asPaddingValues()
        )
    ) {
        CenterAlignedTopAppBar(
            title = {
                SearchTextField(
                    value = query,
                    onValueChange = {
                        viewModel.onSearch(it)
                    },
                    placeholder = stringResource(R.string.employer_search),
                    modifier = Modifier
                        .fillMaxWidth()
                )
            },
            scrollBehavior = behavior
        )
        LazyColumn(state = lazyListState, modifier = Modifier.fillMaxWidth()) {
            items(employers, key = { it.id }) { employer ->
                ListItem(modifier = Modifier.clickable(onClick = {
                    navController.navigate(Route.EmployerDetails(employer.id))
                }),
                    headlineContent = { Text(text = employer.name.orEmpty()) },
                    trailingContent = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.AutoMirrored.Default.KeyboardArrowRight,
                                contentDescription = null
                            )
                        }
                    })
            }
            item { Spacer(modifier = Modifier.height(paddingValues.calculateBottomPadding())) }
        }
    }
}