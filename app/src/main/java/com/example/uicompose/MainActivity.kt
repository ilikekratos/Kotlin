package com.example.uicompose

import MyNavigation
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.uicompose.ui.theme.UIComposeTheme
import com.example.uinativexml.SQLHelper

class MainActivity : ComponentActivity() {
    private lateinit var sqlHelper: SQLHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sqlHelper= SQLHelper(this)
        setContent {
            MyNavigation(sqlHelper = sqlHelper)
        }
    }
}


