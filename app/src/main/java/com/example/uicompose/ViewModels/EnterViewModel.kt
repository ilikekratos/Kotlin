package com.example.uicompose.ViewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel

class EnterViewModel: ViewModel() {
    var usernameInput by mutableStateOf(TextFieldValue(""))
}