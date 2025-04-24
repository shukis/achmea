package com.achmea.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.achmea.R

@Composable
fun SearchTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String,
    showLoader: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        placeholder = { Text(placeholder) },
        textStyle = MaterialTheme.typography.bodyLarge,
        singleLine = true,
        shape = RoundedCornerShape(32.dp),
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = null)
        },
        trailingIcon = {
            if (showLoader) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
            } else if (value.isNotEmpty()) {
                IconButton(onClick = { onValueChange("") }) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = stringResource(R.string.clear_search)
                    )
                }
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.surfaceVariant,
            unfocusedBorderColor = MaterialTheme.colorScheme.surfaceVariant,
            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            cursorColor = MaterialTheme.colorScheme.primary
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewSearchTextField() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        SearchTextField(value = "some value", onValueChange = {}, placeholder = "Search")
        SearchTextField(value = "", onValueChange = {}, placeholder = "Search")
        SearchTextField(value = "", onValueChange = {}, placeholder = "Search", showLoader = true)
    }
}