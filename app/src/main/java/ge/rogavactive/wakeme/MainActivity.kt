package ge.rogavactive.wakeme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ge.rogavactive.wakeme.navigation.BottomNavComponent
import ge.rogavactive.wakeme.navigation.TabsItem
import ge.rogavactive.wakeme.ui.theme.WakeMeTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WakeMeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val items = listOf(
                        TabsItem.AlarmScreen,
                        TabsItem.ReminderScreen,
                        TabsItem.AddButtonDialog,
                        TabsItem.CalendarScreen,
                        TabsItem.SettingsScreen
                    )
                    BottomNavComponent(
                        items = items,
                        navController = rememberNavController()
                    )
                }
            }
        }
    }
}