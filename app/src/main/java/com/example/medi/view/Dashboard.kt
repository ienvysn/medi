package com.example.medi.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
//import androidx.compose.material.icons
//import androidx.compose.material.icons.
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.medi.R
import com.example.medi.ui.theme.Background
import com.example.medi.ui.theme.IconActive
import com.example.medi.ui.theme.IconInacive
import kotlin.jvm.java

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
fun DashboardBody() {
    val context = LocalContext.current
    var selectedIndex by remember { mutableStateOf(0) }

    data class NavItem(val title: String, val icon: Int)

    val navList = listOf(
        NavItem(title = "Home", icon = R.drawable.home_icon),
        NavItem(title = "Meds", icon = R.drawable.bottompill),
        NavItem(title = "Appointment", icon = R.drawable.calender_icon),
        NavItem(title = "Profile", icon = R.drawable.profile),

        )
    Scaffold(
//        floatingActionButton = {
//            FloatingActionButton(onClick ={
//                val intent= Intent(context, addMedsScreen::class.java)
//                context.startActivity(intent)
//            }) {
////                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
//            }
//        },
        bottomBar = {
            NavigationBar(
                modifier = Modifier.height(65.dp),
                containerColor = Color.White
            ) {
                navList.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                painterResource(item.icon),
                                contentDescription = item.title,
                                modifier = Modifier.size(25.dp)
                            )
                        },
                        label = {
                            Text(item.title)
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = IconActive,
                            unselectedIconColor = IconInacive,
                            selectedTextColor = IconActive,
                            unselectedTextColor = IconInacive,
                            indicatorColor = Background,
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
fun DashaboardPreview() {
    DashboardBody()
}
