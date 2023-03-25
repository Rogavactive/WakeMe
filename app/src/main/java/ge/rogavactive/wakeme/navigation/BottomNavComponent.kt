package ge.rogavactive.wakeme.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ge.rogavactive.common.LightGray

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