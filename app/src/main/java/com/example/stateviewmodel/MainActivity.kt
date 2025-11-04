package com.example.stateviewmodel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stateviewmodel.ui.theme.StateViewmodelTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StateViewmodelTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    NutritionApp()
                }
            }
        }
    }
}

@Composable
fun NutritionApp(viewModel: NutritionViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold { paddingValues ->
        NutritionFormScreen(
            uiState = uiState,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            onFirstNameChange = viewModel::onFirstNameChange,
            onLastNameChange = viewModel::onLastNameChange,
            onFoodNameChange = viewModel::onFoodNameChange,
            onCaloriesChange = viewModel::onCaloriesChange,
            onProteinChange = viewModel::onProteinChange,
            onSubmit = viewModel::onSubmit,
        )
    }
}

@Composable
fun NutritionFormScreen(
    uiState: NutritionFormUiState,
    modifier: Modifier = Modifier,
    onFirstNameChange: (String) -> Unit = {},
    onLastNameChange: (String) -> Unit = {},
    onFoodNameChange: (String) -> Unit = {},
    onCaloriesChange: (String) -> Unit = {},
    onProteinChange: (String) -> Unit = {},
    onSubmit: () -> Unit = {},
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .verticalScroll(scrollState)
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = "Catat Gizi Harian Anda",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
        )
        Text(
            text = "Masukkan data makanan yang dikonsumsi untuk melacak asupan gizi harian Anda.",
            style = MaterialTheme.typography.bodyMedium,
        )

        OutlinedTextField(
            value = uiState.firstName,
            onValueChange = onFirstNameChange,
            label = { Text("Nama depan") },
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.firstNameError != null,
            supportingText = {
                if (uiState.firstNameError != null) {
                    Text(
                        text = uiState.firstNameError,
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            },
        )

        OutlinedTextField(
            value = uiState.lastName,
            onValueChange = onLastNameChange,
            label = { Text("Nama belakang") },
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.lastNameError != null,
            supportingText = {
                if (uiState.lastNameError != null) {
                    Text(
                        text = uiState.lastNameError,
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            },
        )

        OutlinedTextField(
            value = uiState.foodName,
            onValueChange = onFoodNameChange,
            label = { Text("Nama makanan") },
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.foodNameError != null,
            supportingText = {
                if (uiState.foodNameError != null) {
                    Text(
                        text = uiState.foodNameError,
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            },
        )

        OutlinedTextField(
            value = uiState.calories,
            onValueChange = onCaloriesChange,
            label = { Text("Kalori (kkal)") },
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.caloriesError != null,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            supportingText = {
                if (uiState.caloriesError != null) {
                    Text(
                        text = uiState.caloriesError,
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            },
        )

        OutlinedTextField(
            value = uiState.protein,
            onValueChange = onProteinChange,
            label = { Text("Protein (gram)") },
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.proteinError != null,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            supportingText = {
                if (uiState.proteinError != null) {
                    Text(
                        text = uiState.proteinError,
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            },
        )

        Button(onClick = onSubmit) {
            Text(text = "Simpan catatan")
        }

        if (uiState.resultSummary != null) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Ringkasan",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = uiState.resultSummary)
                }
            }
        }
    }
}


