package com.example.uicompose.ViewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel

class AddViewModel: ViewModel() {
    var products by mutableStateOf(TextFieldValue(""))
    var phone by mutableStateOf(TextFieldValue(""))
    var deladdress by mutableStateOf(TextFieldValue(""))
}