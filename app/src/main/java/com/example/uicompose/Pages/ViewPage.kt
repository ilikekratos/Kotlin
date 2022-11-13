import android.opengl.Visibility
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.defaultDecayAnimationSpec
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uinativexml.SQLHelper

@Composable
fun ViewPage(
    sqlHelper: SQLHelper,
    userOrders:ArrayList<OrderModel>,
    onNavigateToUpdate:(userOrder:Pair<Int,Int>)->Unit
){
    var toDeleteId :Int =-1
    var showDialog by remember {mutableStateOf(false)}
    val deletedOrders = remember { mutableStateListOf<OrderModel>()}


    Card() {
        if(showDialog==true){
            AlertDialog(
                onDismissRequest = { showDialog=false },
                title = { Text(text = "Are u sure?")},
                confirmButton = {
                    Button(onClick = {
                        sqlHelper.deleteOrder(toDeleteId)
                        for(item in userOrders){
                            if(item.id==toDeleteId) {
                                deletedOrders.add(item)

                            }
                        }
                        showDialog=false }){
                        Text(text = "Yes")
                    }
                },
                dismissButton = {
                    Button(onClick = {

                        showDialog=false }){
                        Text(text = "No")
                    }
                }

            )
        }
    }

    LazyColumn{
        items(
            userOrders.toList()
        ){
            order->
            AnimatedVisibility(
                visible = !deletedOrders.contains(order),
                enter= expandVertically(),
                exit= shrinkVertically (animationSpec = tween(1000))
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                        .height(200.dp)

                ){
                    Column(Modifier.fillMaxSize()) {
                        Row(
                            Modifier
                                .fillMaxHeight(0.2f)
                                .fillMaxWidth()){
                            Column(
                                Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(0.5f)
                            ) {
                                Text(text = "Order with id:"+order.id.toString(), fontSize = 20.sp
                                )
                            }
                            Column(
                                Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(1f)) {
                                Text(text = "Phone:"+order.phone, fontSize = 20.sp
                                )
                            }
                        }
                        Row(
                            Modifier
                                .fillMaxHeight(0.7f)
                                .fillMaxWidth()){
                            Column(
                                Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(0.5f)) {
                                Text(text = "Products:"+order.products, fontSize = 20.sp
                                )
                            }
                            Column(
                                Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(1f)) {
                                Text(text = "Adress:"+order.deladress, fontSize = 20.sp
                                )
                            }
                        }
                        Row(
                            Modifier
                                .fillMaxHeight(1f)
                                .fillMaxWidth()) {
                            Column(
                                Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(0.5f),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center) {
                                Button(onClick = {

                                    onNavigateToUpdate( Pair(order.uid,order.id)) }) {
                                    Text(text = "Update", fontSize = 20.sp)

                                }
                            }
                            Column(
                                Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(1f),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center

                            ) {
                                Button(onClick = {
                                    toDeleteId=order.id
                                    showDialog=true
                                }) {
                                    Text(text = "Delete", fontSize = 20.sp)
                                }
                            }

                        }
                    }
                }
            }

        }
    }
}