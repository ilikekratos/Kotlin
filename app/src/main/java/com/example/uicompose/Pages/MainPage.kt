import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uinativexml.SQLHelper

@Composable
fun MainPage(
    sqlHelper: SQLHelper,
    userId:Int,
    onNavigateToView: (userOrders:ArrayList<OrderModel>) -> Unit,
    onNavigateToAdd: (user:Int) -> Unit,

){
    var userOrders:ArrayList<OrderModel>
    userOrders=sqlHelper.getAllOrders(userId)
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Button(
            onClick = {
                      onNavigateToAdd(userId)
            },
            Modifier.width(200.dp)
        )
        {
            Text(text = "Add", fontSize = 20.sp)
        }
        Button(
            onClick = {onNavigateToView(userOrders)},
            Modifier.width(200.dp)
        )
        {
            Text(text = "View", fontSize = 20.sp)
        }
    }

}