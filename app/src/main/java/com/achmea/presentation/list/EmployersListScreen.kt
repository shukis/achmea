package com.achmea.presentation.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.achmea.R
import com.achmea.core.Route
import com.achmea.designsystem.components.SearchTextField
import com.achmea.designsystem.util.UpdateScrollState
import com.achmea.domain.model.Employer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployersListScreen(
    navController: NavController,
    viewModel: EmployersListViewModel,
    paddingValues: PaddingValues
) {
    val query = viewModel.query.collectAsStateWithLifecycle().value.orEmpty()
    val employers = viewModel.employers.collectAsStateWithLifecycle().value
    val showSearchPrompt = viewModel.showSearchPrompt.collectAsStateWithLifecycle().value
    val showLoader = viewModel.showLoader.collectAsStateWithLifecycle().value
    val showNoResults = viewModel.showNoResults.collectAsStateWithLifecycle().value
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
                    placeholder = stringResource(R.string.clear_search),
                    modifier = Modifier.fillMaxWidth(),
                    showLoader = showLoader
                )
            },
            scrollBehavior = behavior
        )
        EmployerListContent(
            employers = employers,
            navController = navController,
            paddingValues = paddingValues,
            lazyListState = lazyListState
        )
        when {
            showSearchPrompt -> InitialSearchPrompt()
            showNoResults -> NoResults()
        }
    }
}

@Composable
fun EmployerListContent(
    employers: List<Employer>,
    navController: NavController,
    paddingValues: PaddingValues,
    lazyListState: LazyListState
) {
    LazyColumn(
        state = lazyListState,
        modifier = Modifier.fillMaxWidth()
    ) {
        items(employers, key = { it.id }) { employer ->
            ListItem(
                modifier = Modifier
                    .clickable {
                        navController.navigate(Route.EmployerDetails(employer.id))
                    },
                headlineContent = { Text(text = employer.name.orEmpty()) },
                trailingContent = {
                    Icon(
                        Icons.AutoMirrored.Default.KeyboardArrowRight,
                        contentDescription = null
                    )
                }
            )
        }
        item { Spacer(modifier = Modifier.height(paddingValues.calculateBottomPadding())) }
    }
}

@Composable
private fun InitialSearchPrompt(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp, vertical = 108.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.employers_initial_search_title),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.employers_initial_search_description),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun NoResults(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp, vertical = 108.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.SearchOff,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.employers_no_results_title),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.employers_no_results_description),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}