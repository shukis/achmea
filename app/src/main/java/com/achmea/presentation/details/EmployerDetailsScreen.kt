package com.achmea.presentation.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.achmea.R
import com.achmea.designsystem.components.InfoRow
import com.achmea.designsystem.components.InfoSection
import com.achmea.designsystem.util.UpdateScrollState

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun EmployerDetailsScreen(
    navController: NavController,
    viewModel: EmployerDetailsViewModel,
    paddingValues: PaddingValues
) {
    val behavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val lazyListState = rememberLazyListState()
    behavior.UpdateScrollState(lazyListState)
    viewModel.employer.collectAsStateWithLifecycle().value?.let { employer ->
        Column(
            modifier = Modifier.padding(
                WindowInsets.safeDrawing
                    .only(WindowInsetsSides.Horizontal)
                    .asPaddingValues()
            )
        ) {
            CenterAlignedTopAppBar(
                title = { },
                scrollBehavior = behavior,
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
            LazyColumn(
                state = lazyListState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    Text(
                        text = employer.name.orEmpty(),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                item {
                    InfoSection(title = stringResource(R.string.employer_details)) {
                        employer.discountPercentage?.let {
                            InfoRow(
                                label = stringResource(R.string.employer_discount),
                                value = stringResource(
                                    R.string.employer_discount_percentage,
                                    it
                                )
                            )
                        }
                        employer.place?.let {
                            InfoRow(
                                label = stringResource(R.string.employer_place),
                                value = it
                            )
                        }
                    }
                }
                item { Spacer(modifier = Modifier.height(paddingValues.calculateBottomPadding())) }
            }
        }
    }
}