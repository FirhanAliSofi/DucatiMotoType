package com.firhanalisofi.ducatimototype.screen.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.firhanalisofi.ducatimototype.R
import com.firhanalisofi.ducatimototype.ViewModelFactory
import com.firhanalisofi.ducatimototype.di.Injection
import com.firhanalisofi.ducatimototype.model.OrderType
import com.firhanalisofi.ducatimototype.state.UiState
import com.firhanalisofi.ducatimototype.ui.components.SearchBar
import com.firhanalisofi.ducatimototype.ui.components.TypeItems

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit,
) {
    val query = viewModel.query.value

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllType()
            }
            is UiState.Success -> {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    SearchBar(query, viewModel::search)
                    HomeContent(
                        orderReward = uiState.data,
                        modifier = modifier,
                        navigateToDetail = navigateToDetail,
                    )
                }
            }
            is UiState.Error -> {
                Log.d("Error", "Error UiState")
            }
        }
    }
}

@Composable
fun HomeContent(
    orderReward: List<OrderType>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.testTag("SkinList")
    ) {
        if (orderReward.isEmpty()) {
            item {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.no_type_found),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        } else {
            items(orderReward) { data ->
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                navigateToDetail(data.type.id)
                            }
                            .background(MaterialTheme.colorScheme.surface)
                    ) {
                        TypeItems(
                            photoUrl = data.type.photoUrl,
                            name = data.type.name,
                            requiredPrice = data.type.price,
                        )
                    }
                }
            }
        }
    }
}