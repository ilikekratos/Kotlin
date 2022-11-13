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
import com.example.uinativexml.SQLHelper

@Composable
fun UpdatePage(
    idPair: Pair<Int,Int>,
    sqlHelper: SQLHelper

){
    var showDialog by remember { mutableStateOf(false) }
    var showDialog2 by remember { mutableStateOf(false) }
    var showDialog3 by remember { mutableStateOf(false) }
    var updateOrder:OrderModel

    updateOrder=sqlHelper.getOrder(idPair.second)

    updateOrder.id=idPair.second
    updateOrder.uid=idPair.first


    var products by remember {mutableStateOf(TextFieldValue(updateOrder.products))}
    var phone by remember{ mutableStateOf(TextFieldValue(updateOrder.phone))}
    var deladdress by remember {mutableStateOf(TextFieldValue(updateOrder.deladress))}

    Card() {
        if(showDialog==true){
            AlertDialog(
                onDismissRequest = { showDialog=false },
                title = { Text(text = "Empty Field(s)") },
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
                title = { Text(text = "Letters in phone number") },
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
                title = { Text(text = "Success") },
                confirmButton = {
                    Button(onClick = { showDialog3=false }) {
                        Text(text = "Ok")
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
        Text(text = "Update products", Modifier.fillMaxWidth(0.7f), fontSize = 30.sp)
        TextField(value = products , onValueChange ={
                newText: TextFieldValue ->
            products = newText
        } , Modifier.fillMaxWidth(0.7f))
        Text(text = "Update phone number", Modifier.fillMaxWidth(0.7f), fontSize = 30.sp)
        TextField(value = phone , onValueChange ={
                newText: TextFieldValue ->
            phone = newText
        } , Modifier.fillMaxWidth(0.7f))
        Text(text = "Update delivery adress", Modifier.fillMaxWidth(0.7f), fontSize = 30.sp)
        TextField(value = deladdress , onValueChange ={
                newText: TextFieldValue ->
            deladdress = newText
        } , Modifier.fillMaxWidth(0.7f))
        Button(onClick = {
            if(products.text==""||deladdress.text==""||phone.text=="")
                showDialog=true
            else
                if(phone.text.contains("[a-zA-Z]".toRegex())){
                    showDialog2=true
                }
                else{
                    updateOrder.phone=phone.text
                    updateOrder.deladress=deladdress.text
                    updateOrder.products=products.text
                    sqlHelper.updateOrder(updateOrder)

                    showDialog3=true
                }

        }
            ,
            Modifier
                .padding(0.dp, 10.dp)
                .fillMaxWidth(0.7f)
        ) {
            Text(text = "Update order", fontSize = 30.sp)
        }
    }

}