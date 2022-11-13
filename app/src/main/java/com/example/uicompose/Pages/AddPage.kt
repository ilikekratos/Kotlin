import android.widget.EditText
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uicompose.ViewModels.AddViewModel
import com.example.uicompose.ViewModels.EnterViewModel
import com.example.uinativexml.SQLHelper

@Composable
fun AddPage(
    userId:Int,
    addViewModel: AddViewModel = viewModel(),
    sqlHelper: SQLHelper

    ){
    var showDialog by remember {mutableStateOf(false)}
    var showDialog2 by remember {mutableStateOf(false)}
    var showDialog3 by remember {mutableStateOf(false)}
    var insertOrder:OrderModel=OrderModel(0,userId,"","","")

    Card() {
        if(showDialog==true){
            AlertDialog(
                onDismissRequest = { showDialog=false },
                title = { Text(text = "Empty Field(s)")},
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
                title = { Text(text = "Letters in phone number")},
                confirmButton = {
                    Button(onClick = { showDialog2=false }) {
                    }
                }
            )
        }
    }
    Card() {
        if(showDialog3==true){
            AlertDialog(
                onDismissRequest = { showDialog3=false },
                title = { Text(text = "Success")},
                confirmButton = {
                    Button(onClick = { showDialog3=false }) {
                        Text(text = "Ok")
                    }
                }
            )
        }
    }
    Column(Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Enter products",Modifier.fillMaxWidth(0.7f), fontSize = 30.sp)
        TextField(value = addViewModel.products , onValueChange ={
            newText: TextFieldValue ->
        addViewModel.products = newText
        } ,Modifier.fillMaxWidth(0.7f))
        Text(text = "Enter phone number",Modifier.fillMaxWidth(0.7f), fontSize = 30.sp)
        TextField(value = addViewModel.phone , onValueChange ={
                newText: TextFieldValue ->
            addViewModel.phone = newText
        } ,Modifier.fillMaxWidth(0.7f))
        Text(text = "Enter delivery adress",Modifier.fillMaxWidth(0.7f), fontSize = 30.sp)
        TextField(value = addViewModel.deladdress , onValueChange ={
                newText: TextFieldValue ->
            addViewModel.deladdress = newText
        } ,Modifier.fillMaxWidth(0.7f))
        Button(onClick = {
            if(addViewModel.products.text==""||addViewModel.deladdress.text==""||addViewModel.phone.text=="")
                showDialog=true
            else
            if(addViewModel.phone.text.contains("[a-zA-Z]".toRegex())){
                showDialog2=true
            }
            else{
                insertOrder.phone=addViewModel.phone.text
                insertOrder.deladress=addViewModel.deladdress.text
                insertOrder.products=addViewModel.products.text

                sqlHelper.insertOrder(insertOrder)

                addViewModel.deladdress= TextFieldValue("")
                addViewModel.phone=TextFieldValue("")
                addViewModel.products=TextFieldValue("")
                showDialog3=true
            }
            
        }
        ,
            Modifier
                .padding(0.dp, 10.dp)
                .fillMaxWidth(0.7f)
        ) {
            Text(text = "Add order", fontSize = 30.sp)
        }
    }

}