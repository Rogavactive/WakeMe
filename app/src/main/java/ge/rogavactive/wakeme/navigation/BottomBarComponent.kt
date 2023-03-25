package ge.rogavactive.wakeme.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import ge.rogavactive.common.LightGray

@Composable
fun BottomBarComponent(
    bottomBarState: Boolean,
    navBackStackEntry: NavBackStackEntry?,
    items: List<TabsItem> = emptyList(),
    navController: NavHostController
) {
    AnimatedVisibility(
        visible = bottomBarState,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it })
    ) {
        var currentIndex by remember { mutableStateOf(0) }
        var navBarWidth by remember { mutableStateOf(0f) }
        var navBarItemWidth by remember { mutableStateOf(0f) }
        val spaceBetweenItems = (navBarWidth - (items.size.toFloat() * navBarItemWidth)) / (items.size - 1).toFloat()
        val offsetAnim by animateFloatAsState(
            targetValue =
            with(LocalDensity.current) { 16.dp.toPx() } +
                    navBarItemWidth * currentIndex +
                    spaceBetweenItems * currentIndex
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.5.dp)
                .background(LightGray)
        )
        Box(
            modifier = Modifier
                .padding(0.dp, 10.dp)
                .width(
                    with(LocalDensity.current) {
                        navBarItemWidth.toDp()
                    })
                .height(40.dp)
                .offset(
                    with(LocalDensity.current) {
                        offsetAnim.toDp()
                    },
                    0.dp
                )
                .clip(RoundedCornerShape(5.dp))
                .background(LightGray)
        )
        NavigationBar(
            tonalElevation = 3.dp,
            containerColor = Color.Transparent,
            modifier = Modifier
                .height(60.dp)
                .padding(horizontal = 16.dp, vertical = 0.dp)
                .onGloballyPositioned {
                    navBarWidth = it.size.width.toFloat()
                }
        ) {
            val currentDestination = navBackStackEntry?.destination
            items.forEachIndexed { index, screen ->
                AnimatedBottomNavigationItem(
                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                    icon = screen.icon,
                    modifier = Modifier
                        .fillMaxHeight()
                        .onGloballyPositioned {
                            navBarItemWidth = it.size.width.toFloat()
                        },
                    onClick = {
                        currentIndex = index
                        navController.navigate(screen.route) {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}