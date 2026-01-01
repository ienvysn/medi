package com.example.medi.view

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medi.R
import com.example.medi.model.appointmentModel
import com.example.medi.model.medsModel
import com.example.medi.repository.appointmentRepoImpl
import com.example.medi.repository.medsRepoImpl
import com.example.medi.ui.theme.*
import com.example.medi.viewModel.AppointmentViewModel
import com.example.medi.viewModel.MedsViewModel
import java.text.SimpleDateFormat
import java.util.*
import com.example.medi.ui.theme.Card as CardColor

@Composable
fun HomeScreen() {
    val context = LocalContext.current


    val medsViewModel = remember { MedsViewModel(medsRepoImpl()) }
    val appointmentViewModel = remember { AppointmentViewModel(appointmentRepoImpl()) }


    val allMeds by medsViewModel.allmeds.observeAsState(initial = emptyList())
    val allAppointments by appointmentViewModel.appointments.observeAsState(initial = emptyList())


    LaunchedEffect(Unit) {
        medsViewModel.getAllmeds()
        appointmentViewModel.getAllAppointments()
    }


    val todayMeds = remember(allMeds) {
        val calendar = Calendar.getInstance()
        val todayDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) // 1=Sun, 2=Mon...

        allMeds.filter { med ->

            med.frequency == "Daily" || (med.frequency == "Weekly" && med.dayOfWeek == todayDayOfWeek)
        }.sortedBy { it.status == "Taken" }
    }

    val takenCount = todayMeds.count { it.status == "Taken" }
    val totalCount = todayMeds.size


    val upcomingAppointments = remember(allAppointments) {
        val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        val today = Calendar.getInstance().apply {

            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time

        allAppointments.filter {
            try {
                val appDate = sdf.parse(it.date)
                appDate != null && !appDate.before(today)
            } catch (e: Exception) {
                false
            }
        }.sortedBy {

            try { sdf.parse(it.date) } catch(e: Exception) { Date() }
        }.take(3)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Background)
    ) {

        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                colors = CardDefaults.cardColors(containerColor = CardColor),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text("Good Afternoon", style = TextStyle(fontSize = 15.sp), color = Color.White)
                    Spacer(Modifier.height(5.dp))

                }
            }
        }


        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                    .border(1.dp, BorderOutline, shape = RoundedCornerShape(8.dp))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.pills_icon),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(end = 12.dp)
                                .size(20.dp),
                            tint = IconActive
                        )
                        Text(
                            "Today's Medications",
                            style = TextStyle(fontWeight = FontWeight.Medium, fontSize = 18.sp)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            "$takenCount/$totalCount taken",
                            style = TextStyle(fontSize = 15.sp),
                            color = TextColor
                        )
                    }

                    if (todayMeds.isEmpty()) {
                        Text(
                            "No medications for today",
                            modifier = Modifier.padding(16.dp),
                            color = Color.Gray
                        )
                    } else {
                        todayMeds.forEach { med ->
                            MedCardHome(
                                med = med,
                                onStatusChange = { newStatus ->

                                    val updatedMed = med.copy(status = newStatus)
                                    medsViewModel.updateMeds(med.id, updatedMed) { success, _ ->
                                        if (!success) {
                                            Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }


        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                    .border(1.dp, BorderOutline, shape = RoundedCornerShape(8.dp))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.calender_icon),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(end = 12.dp)
                                .size(20.dp),
                            tint = IconActive
                        )
                        Text(
                            "Next Appointment",
                            style = TextStyle(fontWeight = FontWeight.Medium, fontSize = 18.sp)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text("View All", style = TextStyle(fontSize = 15.sp), color = TextColor)
                    }

                    if (upcomingAppointments.isEmpty()) {
                        Text(
                            "No upcoming appointments",
                            modifier = Modifier.padding(16.dp),
                            color = Color.Gray
                        )
                    } else {
                        upcomingAppointments.forEach { appointment ->
                            AppointmentCard(appointment)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MedCardHome(
    med: medsModel,
    onStatusChange: (String) -> Unit
) {
    val isTaken = med.status == "Taken"


    val cardAlpha = if (isTaken) 0.6f else 1f
    val textColor = if (isTaken) Color.Gray else Color.Black
    val textDecoration = if (isTaken) TextDecoration.LineThrough else TextDecoration.None

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = cardAlpha)
        ),
        border = BorderStroke(1.dp, if(isTaken) Color.LightGray else Color.Gray)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = med.name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor,
                        textDecoration = textDecoration
                    )
                    Spacer(Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .background(
                                color = ChipBackground,
                                shape = RoundedCornerShape(50)
                            )
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = med.dosage,
                            fontSize = 12.sp,
                            color = DarkTextColor
                        )
                    }
                }

                Spacer(Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_access_time_24),
                        contentDescription = null,
                        tint = TextColor
                    )
                    Spacer(Modifier.width(6.dp))
                    Text(med.schedule, color = TextColor, fontSize = 16.sp)
                    Spacer(Modifier.width(8.dp))
                    Text(
                        med.notes,
                        fontSize = 14.sp,
                        fontStyle = FontStyle.Italic,
                        color = TextColor,
                        maxLines = 1
                    )
                }
            }


            Row {

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .border(1.dp, if (isTaken) Color.Green else Color.LightGray, CircleShape)
                        .clickable {
                            if (!isTaken) onStatusChange("Taken")
                        }
                        .background(if (isTaken) Color(0xFFE8F5E9) else Color.Transparent, CircleShape)
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.check),
                        contentDescription = "Take",
                        tint = if (isTaken) Color.Green else Color.Gray
                    )
                }

                Spacer(Modifier.width(12.dp))

                if (isTaken) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .border(1.dp, Color.LightGray, CircleShape)
                            .clickable { onStatusChange("Pending") }
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.cross),
                            contentDescription = "Untake",
                            tint = Color.Red
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun AppointmentCard(
    appointment: appointmentModel,
    onEdit: () -> Unit = {},
    onDelete: () -> Unit = {}
) {
    var showMenu by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(18.dp),
        border = BorderStroke(1.dp, BorderOutline),
        colors = CardDefaults.cardColors(
            containerColor = AppoinementBackground
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {


            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Left side: Avatar + Name
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .background(
                                color = AquaIcon,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.profile),
                            contentDescription = null,
                            tint = IconActive,
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = appointment.doctorName,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        if (!appointment.specialty.isNullOrEmpty()) {
                            Text(
                                text = appointment.specialty,
                                fontSize = 14.sp,
                                color = TextColor
                            )
                        }
                    }
                }


                Box {

                    IconButton(onClick = { showMenu = true }) {
                        Icon(
                            painter = painterResource(R.drawable.outline_more_vert_24), // Make sure you have this icon or use Icons.Default.MoreVert
                            contentDescription = "Options",
                            tint = Color.Gray
                        )
                    }

                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false },
                        modifier = Modifier.background(Color.White)
                    ) {
                        DropdownMenuItem(
                            text = { Text("Edit") },
                            onClick = {
                                showMenu = false
                                onEdit()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Delete", color = Color.Red) },
                            onClick = {
                                showMenu = false
                                onDelete()
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(R.drawable.calender_icon),
                    contentDescription = null,
                    tint = IconActive,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = appointment.date,
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(8.dp))


            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(R.drawable.baseline_access_time_24),
                    contentDescription = null,
                    tint = IconActive,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = appointment.time,
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(8.dp))


            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(R.drawable.location),
                    contentDescription = null,
                    tint = IconActive,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = appointment.location ?: "",
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }
        }
    }
}