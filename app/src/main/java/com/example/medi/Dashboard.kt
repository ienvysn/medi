package com.example.medi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.medi.ui.theme.Background
import com.example.medi.ui.theme.IconActive
import com.example.medi.ui.theme.IconInacive
import com.example.medi.ui.theme.MediTheme
import com.example.medi.ui.theme.Muted
import com.example.medi.ui.theme.Primary
import com.example.medi.ui.theme.Secondary

class Dashboard : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DashboardBody()
            }
        }
    }

@Composable
fun DashboardBody(){
    val context = LocalContext.current
    var selectedIndex by remember { mutableStateOf(0) }

    data class NavItem(val title: String, val icon: Int, )
    val NavList= listOf(
        NavItem(title = "Home", icon = R.drawable.home_icon),
        NavItem(title = "Meds", icon = R.drawable.bottompill),
        NavItem(title = "Appointment", icon = R.drawable.calender_icon),
        NavItem(title = "Profile", icon = R.drawable.profile),

        )
    Scaffold(
    bottomBar = {
        NavigationBar {
            NavList.forEachIndexed { index,item->
                NavigationBarItem(
                    icon = {
                        Icon(
                            painterResource(item.icon),
                            null,
                            modifier = Modifier.size(25.dp)
                        )
                    },
                    label = {
                        Text(item.title)
                    },
                    colors = NavigationBarItemColors(
                        selectedIconColor = IconActive,
                        unselectedIconColor = IconInacive,
                        selectedTextColor = IconActive,
                        unselectedTextColor = IconInacive,
                        selectedIndicatorColor = Background,
                        disabledIconColor = IconInacive,
                        disabledTextColor = IconInacive,

                        ),
                    selected = selectedIndex == index,
                    onClick = {
                        selectedIndex = index
                    }
                )
            }
        }
    }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (selectedIndex) {
                0 -> HomeScreen()
                1 -> MedsScreen()
                2 -> AppointmentScreen()
                3 -> ProfileScreen()
                else -> HomeScreen()
            }
        }
    }

}
@Composable
@Preview
fun DashaboardPreview(){
    DashboardBody()
}

