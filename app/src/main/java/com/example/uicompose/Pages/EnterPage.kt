import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uicompose.ViewModels.EnterViewModel
import com.example.uinativexml.SQLHelper

@Composable
fun EnterPage(
    sqlHelper: SQLHelper,
    enterViewModel: EnterViewModel = viewModel(),
    onNavigateToMain: (user:Int) -> Unit

){

    var name by remember { mutableStateOf("") }
    var showDialog by remember {mutableStateOf(false)}
    var showDialog2 by remember {mutableStateOf(false)}

    Card() {
        if(showDialog==true){
            AlertDialog(
                onDismissRequest = { showDialog=false },
                title = { Text(text = "Empty name")},
                confirmButton = {
                    Button(onClick = { showDialog=false }) {
                    }
                }
            )
        }
    }
    Card() {
        if(showDialog2==true){
            AlertDialog(
                onDismissRequest = { showDialog2=false },
                title = { Text(text = "Wrong name")},
                confirmButton = {
                    Button(onClick = { showDialog2=false }) {
                    }
                }
            )
        }
    }

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text ="Enter your name",
            fontSize = 35.sp
        )
        TextField(
            singleLine = true,
            label = { Text("Enter name") },
            value = enterViewModel.usernameInput ,
            onValueChange = { newText: TextFieldValue ->
                enterViewModel.usernameInput = newText
            },
        )

        Button(onClick = {
            if(enterViewModel.usernameInput!= TextFieldValue("")) {
                var userId:Int=sqlHelper.checkUser(enterViewModel.usernameInput.text.toString())
                if(userId!=0)
                    onNavigateToMain(userId)
                else
                showDialog2=true
            }
            else{
                showDialog=true
            }
        })
        {
            Text(text = "GO")
        }
    }

}