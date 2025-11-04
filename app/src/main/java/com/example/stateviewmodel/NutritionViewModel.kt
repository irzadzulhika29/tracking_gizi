package com.example.stateviewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update


data class NutritionFormUiState(
    val firstName: String = "",
    val lastName: String = "",
    val foodName: String = "",
    val calories: String = "",
    val protein: String = "",
    val firstNameError: String? = null,
    val lastNameError: String? = null,
    val foodNameError: String? = null,
    val caloriesError: String? = null,
    val proteinError: String? = null,
    val resultSummary: String? = null,
)

class NutritionViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(NutritionFormUiState())
    val uiState: StateFlow<NutritionFormUiState> = _uiState

    fun onFirstNameChange(value: String) {
        _uiState.update {
            it.copy(
                firstName = value,
                firstNameError = null,
                resultSummary = null,
            )
        }
    }

    fun onLastNameChange(value: String) {
        _uiState.update {
            it.copy(
                lastName = value,
                lastNameError = null,
                resultSummary = null,
            )
        }
    }

    fun onFoodNameChange(value: String) {
        _uiState.update {
            it.copy(
                foodName = value,
                foodNameError = null,
                resultSummary = null,
            )
        }
    }

    fun onCaloriesChange(value: String) {
        _uiState.update {
            it.copy(
                calories = value,
                caloriesError = null,
                resultSummary = null,
            )
        }
    }

    fun onProteinChange(value: String) {
        _uiState.update {
            it.copy(
                protein = value,
                proteinError = null,
                resultSummary = null,
            )
        }
    }

    fun onSubmit() {
        val current = _uiState.value
        val firstNameError = validateRequired(current.firstName, "Nama depan wajib diisi")
        val lastNameError = validateRequired(current.lastName, "Nama belakang wajib diisi")
        val foodNameError = validateRequired(current.foodName, "Nama makanan wajib diisi")
        val caloriesError = validatePositiveNumber(current.calories, "Kalori harus berupa angka lebih dari 0")
        val proteinError = validatePositiveNumber(current.protein, "Protein harus berupa angka lebih dari 0")

        if (
            listOf(
                firstNameError,
                lastNameError,
                foodNameError,
                caloriesError,
                proteinError,
            ).any { it != null }
        ) {
            _uiState.update {
                it.copy(
                    firstNameError = firstNameError,
                    lastNameError = lastNameError,
                    foodNameError = foodNameError,
                    caloriesError = caloriesError,
                    proteinError = proteinError,
                    resultSummary = null,
                )
            }
            return
        }

        val fullName = "${current.firstName.trim()} ${current.lastName.trim()}".trim()
        val caloriesValue = current.calories.trim().toFloat()
        val proteinValue = current.protein.trim().toFloat()
        val summary = buildString {
            append("Catatan gizi untuk $fullName:\n")
            append("Makanan: ${current.foodName.trim()}\n")
            append("Kalori: ${"%.1f".format(caloriesValue)} kkal\n")
            append("Protein: ${"%.1f".format(proteinValue)} gram")
        }

        _uiState.update {
            it.copy(
                firstNameError = null,
                lastNameError = null,
                foodNameError = null,
                caloriesError = null,
                proteinError = null,
                resultSummary = summary,
            )
        }
    }

    private fun validateRequired(value: String, errorMessage: String): String? =
        if (value.isBlank()) errorMessage else null

    private fun validatePositiveNumber(value: String, errorMessage: String): String? {
        val number = value.trim().toFloatOrNull()
        return if (number == null || number <= 0f) errorMessage else null
    }
}
