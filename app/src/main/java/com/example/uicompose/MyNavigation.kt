import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.uinativexml.SQLHelper

@Composable
fun MyNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "enter",
    sqlHelper: SQLHelper,
) {
    var userOrders:ArrayList<OrderModel> = arrayListOf()
    var userId:Int =0
    var idPair:Pair<Int,Int> =Pair (1,1)
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable("enter") {
            EnterPage(sqlHelper=sqlHelper, onNavigateToMain = {
                userId=it
                navController.navigate("main"){popUpTo(0)}
            })
        }
        composable("main") {
            MainPage(sqlHelper=sqlHelper, userId = userId,
                onNavigateToAdd = {
                    navController.navigate("add")
                },
                onNavigateToView = {
                    userOrders=it
                    navController.navigate("view")
                },

            )
        }
        composable("update"){
            println("Yes")
            UpdatePage(idPair=idPair, sqlHelper = sqlHelper )
        }
        composable("add"){
            AddPage(userId, sqlHelper = sqlHelper)
        }
        composable("view"){
                ViewPage(sqlHelper = sqlHelper, userOrders = userOrders , onNavigateToUpdate ={
                    idPair=it
                    navController.navigate("update")
                }
                )
        }
    }
}