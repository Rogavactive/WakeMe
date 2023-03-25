package ge.rogavactive.wakeme.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ge.rogavactive.alarm.ui.alarmdetails.AlarmDetailsScreenComposable
import ge.rogavactive.alarm.ui.alarmdetails.AlarmDetailsScreenInfo
import ge.rogavactive.alarm.ui.alarmlist.AlarmScreenComposable
import ge.rogavactive.common.R

typealias NavComposableFun = @Composable (NavController) -> Unit

sealed class TabsItem(@StringRes val title: Int, val route: String, @DrawableRes val icon: Int, val screen: NavComposableFun) {
    object AlarmScreen : TabsItem(R.string.tab_alarm, "Alarm", R.drawable.ic_alarm_clock , { navController ->
        AlarmScreenComposable( onOpenDetailsClick = { id ->
            navController.navigate("${AlarmDetailsScreenInfo.route}/$id")
        })
    })
    object ReminderScreen : TabsItem(R.string.tab_reminders,"Reminder",  R.drawable.ic_reminder_bell , { PlaceHolderText("ReminderScreen") })
    object AddButtonDialog : TabsItem(R.string.tab_button_add,"AddButton",  R.drawable.ic_plus, { PlaceHolderText("AddButtonDialog") })
    object CalendarScreen : TabsItem(R.string.tab_calendar,"Calendar",  R.drawable.ic_calendar, { PlaceHolderText("CalendarScreen") })
    object SettingsScreen : TabsItem(R.string.tab_settings,"Settings",  R.drawable.ic_settings, { PlaceHolderText("SettingsScreen") })
}

@Composable
fun WakeMeNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(navController = navController, startDestination = TabsItem.AlarmScreen.route, modifier = modifier) {
        composable(TabsItem.AlarmScreen.route) { TabsItem.AlarmScreen.screen(navController) }
        composable(TabsItem.ReminderScreen.route) { TabsItem.ReminderScreen.screen(navController) }
        composable(TabsItem.AddButtonDialog.route) { TabsItem.AddButtonDialog.screen(navController) }
        composable(TabsItem.CalendarScreen.route) { TabsItem.CalendarScreen.screen(navController) }
        composable(TabsItem.SettingsScreen.route) { TabsItem.SettingsScreen.screen(navController) }
        composable(
            route = "${AlarmDetailsScreenInfo.route}/{${AlarmDetailsScreenInfo.ARG_ID}}",
            arguments = listOf(navArgument(AlarmDetailsScreenInfo.ARG_ID) {
                type = NavType.IntType
                defaultValue = -1
            })
        ) { entry ->  AlarmDetailsScreenComposable(id = entry.arguments?.getInt(AlarmDetailsScreenInfo.ARG_ID)) }
    }
}

//TODO: delete after adding actual components
@Composable
fun PlaceHolderText(name: String) {
    Text(text = "Hello $name!")
}
