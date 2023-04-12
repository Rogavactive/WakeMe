package ge.rogavactive.wakeme.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun BottomNavComponent(
    items: List<TabsItem> = emptyList(),
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val bottomBarState = rememberSaveable { mutableStateOf(true) }
    bottomBarState.value = items
        .map { it.route }
        .contains(navBackStackEntry?.destination?.route)

    Scaffold(
//        floatingActionButtonPosition = FabPosition.Center,
//        floatingActionButton = {
//            FloatingActionButton(onClick = { /*TODO*/ }, shape = CircleShape) {
//                Icon(
//                    imageVector = ImageVector.vectorResource(id = TabsItem.AddButtonDialog.icon),
//                    contentDescription = null,
//                    modifier = Modifier.size(24.dp),
//                    tint = PrimaryBlack
//                )
//            }
//        },
        bottomBar = {
            BottomBarComponent(
                bottomBarState = bottomBarState.value,
                navBackStackEntry = navBackStackEntry,
                navController = navController,
                items = items
            )
        }
    ) { innerPadding ->
        WakeMeNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }

}